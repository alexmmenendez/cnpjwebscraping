package br.com.cnpjwebscraping.solver.deathbycaptchav2.client;

import br.com.cnpjwebscraping.solver.deathbycaptchav2.base64.Base64;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.AccessDeniedException;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.InvalidCaptchaException;
import br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.ServiceOverloadException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Random;


/**
 * Death by Captcha socket API client.
 *
 */
public class SocketClient extends Client
{
    final static public String HOST = "api.dbcapi.me";
    final static public int FIRST_PORT = 8123;
    final static public int LAST_PORT = 8130;

    final static public String TERMINATOR = "\r\n";


    protected SocketChannel channel = null;
    protected Object callLock = new Object();


    protected String sendAndReceive(byte[] payload)
        throws IOException
    {
        ByteBuffer sbuf = ByteBuffer.wrap(payload);
        ByteBuffer rbuf = ByteBuffer.allocateDirect(256);
        CharsetDecoder rbufDecoder = Charset.forName("UTF-8").newDecoder();
        StringBuilder response = new StringBuilder();

        int ops = SelectionKey.OP_WRITE | SelectionKey.OP_READ;
        if (this.channel.isConnectionPending()) {
            ops = ops | SelectionKey.OP_CONNECT;
        }

        Selector selector = Selector.open();
        try {
            this.channel.register(selector, ops);
            int intvl_idx = 0;
            int intvl = 0;
            int[] results = {0, 0};
            
            while (true) {
                results = Client.getPollInterval(intvl_idx);
                intvl = results[0];
                intvl_idx = results[1];

                if (0 < selector.select(intvl * 1000)) {
                    Iterator keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = (SelectionKey)keys.next();
                        SocketChannel ch = (SocketChannel)key.channel();
                        if (key.isConnectable()) {
                            // Just connected
                            ch.finishConnect();
                        }
                        if (key.isReadable() && !sbuf.hasRemaining()) {
                            // Receiving the response
                            while (0 < ch.read(rbuf)) {
                                rbuf.flip();
                                response.append(rbufDecoder.decode(rbuf).toString());
                            }
                            if (2 <= response.length() && response.substring(response.length() - 2, response.length()).equals(SocketClient.TERMINATOR)) {
                                response.setLength(response.length() - 2);
                                return response.toString();
                            } else if (0 == response.length()) {
                                throw new IOException("Connection lost");
                            }
                        }
                        if (key.isWritable() && sbuf.hasRemaining()) {
                            // Sending the request
                            while (0 < ch.write(sbuf) && sbuf.hasRemaining()) {
                                //
                            }
                        }
                        keys.remove();
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException("API communication failed: " + e.toString());
        } finally {
            selector.close();
        }
    }


    /**
     * @see br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client#close
     */
    public void close()
    {
        if (null != this.channel) {
            this.log("CLOSE");

            if (this.channel.isConnected() || this.channel.isConnectionPending()) {
                try {
                    this.channel.socket().shutdownOutput();
                    this.channel.socket().shutdownInput();
                } catch (Exception e) {
                    //
                } finally {
                    try {
                        this.channel.close();
                    } catch (Exception e) {
                        //
                    }
                }
            }

            try {
                this.channel.socket().close();
            } catch (Exception e) {
                //
            }

            this.channel = null;
        }
    }

    /**
     * @see br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client#connect
     */
    public boolean connect()
        throws IOException
    {
        if (null == this.channel) {
            this.log("OPEN");

            InetAddress host = null;
            try {
                host = InetAddress.getByName(SocketClient.HOST);
            } catch (Exception e) {
                //System.out.println(e)
                throw new IOException("API host not found");
            }

            SocketChannel channel = SocketChannel.open();
            channel.configureBlocking(false);
            try {
                channel.connect(new InetSocketAddress(
                    host,
                    SocketClient.FIRST_PORT + new Random().nextInt(
                        SocketClient.LAST_PORT - SocketClient.FIRST_PORT + 1
                    )
                ));
            } catch (IOException e) {
                throw new IOException("API connection failed");
            }

            this.channel = channel;
        }

        return null != this.channel;
    }


    protected JSONObject call(String cmd, JSONObject args)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        try {
            args.put("cmd", cmd).put("version", Client.API_VERSION);
        } catch (JSONException e) {
            //System.out.println(e);
            return new JSONObject();
        }

        int attempts = 2;
        byte[] payload = (args.toString() + SocketClient.TERMINATOR).getBytes();
        JSONObject response = null;
        while (0 < attempts && null == response) {
            attempts--;
            if (null == this.channel && !cmd.equals("login")) {
                this.call("login", this.getCredentials());
            }
            synchronized (this.callLock) {
                if (this.connect()) {

                    try {
                        response = new JSONObject(this.sendAndReceive(payload));
                    } catch (Exception e) {
                        //System.out.println("SocketClient.call(): " + e.toString());
                        this.close();
                    }
                }
            }
        }
        if (null == response) {
            throw new IOException("API connection lost or timed out");
        }

        String error = response.optString("error", null);
        if (null != error) {
            synchronized (this.callLock) {
                this.close();
            }
            if (error.equals("not-logged-in")) {
                throw new AccessDeniedException("Access denied, check your credentials");
            } else if (error.equals("banned")) {
                throw new AccessDeniedException("Access denied, account is suspended");
            } else if (error.equals("insufficient-funds")) {
                throw new AccessDeniedException("Access denied, balance is too low");
            } else if (error.equals("invalid-captcha")) {
                throw new InvalidCaptchaException("CAPTCHA was rejected by the service, check if it's a valid image");
            } else if (error.equals("service-overload")) {
                throw new ServiceOverloadException("CAPTCHA was rejected due to service overload, try again later");
            } else {
                throw new IOException("API server error occured: " + error);
            }
        } else {
            return response;
        }
    }

    {
   }

    protected JSONObject call(String cmd)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        return this.call(cmd, new JSONObject());
    }


    /**
     * @see br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client#Client(String, String)
     * @param username username
     * @param password password
     */
    public SocketClient(String username, String password)
    {
        super(username, password);
    }

    public void finalize()
    {
        this.close();
    }


    /**
     * @see br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client#getUser
     */
    public User getUser()
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        return new User(this.call("user"));
    }

    /**
     * @see br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client#upload
     */
    public Captcha upload(byte[] img, String challenge, int type, byte[] banner, String banner_text, String grid)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        JSONObject args = new JSONObject();
        try {
            args.put("captcha",
                Base64.encodeBytes(img)).put(
                "swid", Client.SOFTWARE_VENDOR_ID).put(
                "challenge", challenge).put(
                "grid", grid).put(
                "type", Integer.toString(type)).put(
                "banner_text", banner_text);
            if (banner!=null) {
                args.put("banner",
                    Base64.encodeBytes(banner));
                }
        } catch (JSONException e) {
            //System.out.println(e);
        }
        Captcha c = new Captcha(this.call("upload", args));
        return c.isUploaded() ? c : null;
    }

    public Captcha upload(byte[] img, String challenge, int type, byte[] banner, String banner_text)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        return this.upload(img,challenge,type,banner,banner_text, "");
    }

    public Captcha upload(byte[] img, int type, byte[] banner, String banner_text)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        return this.upload(img,type,banner,banner_text);
    }

    public Captcha upload(byte[] img)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        return this.upload(img,"",0,null,"");
    }

    public Captcha upload(int type, JSONObject json)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        
        JSONObject args = new JSONObject();
        try {
            args.put("swid", Client.SOFTWARE_VENDOR_ID).put(
                "type", Integer.toString(type)).put(
                "token_params", json);
            
        } catch (JSONException e) {
            //System.out.println(e);
        }
        Captcha c = new Captcha(this.call("upload", args));
        return c.isUploaded() ? c : null;
    }


    /**
     * @see br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client#getCaptcha
     */
    public Captcha getCaptcha(int id)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        JSONObject args = new JSONObject();
        try {
            args.put("captcha", id);
        } catch (JSONException e) {
            //System.out.println(e);
        }
        return new Captcha(this.call("captcha", args));
    }

    /**
     * @see br.com.cnpjwebscraping.solver.deathbycaptchav2.client.Client#report
     */
    public boolean report(int id)
        throws IOException, br.com.cnpjwebscraping.solver.deathbycaptchav2.exception.Exception
    {
        JSONObject args = new JSONObject();
        try {
            args.put("captcha", id);
        } catch (JSONException e) {
            //System.out.println(e);
        }
        return !(new Captcha(this.call("report", args))).isCorrect();
    }
}
