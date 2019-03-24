package br.com.cnpjwebscraping.util;

import javax.net.ssl.*;

public class TrustUtil {

	public static void setTrustAllCerts() throws Exception {
		setTrustAllCerts("SSL");
	}

	public static void setTrustAllCerts(String tipoSSL) throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
		}};

		try {
			SSLContext sc = SSLContext.getInstance(tipoSSL);
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			});
		} catch (Exception e) {
			throw new Exception("certificate fail");
		}
	}
	
}
