package br.com.cnpjwebscraping.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMPRESA_SCRAPING")
public class EmpresaScraping {

    @Id
    @SequenceGenerator(name = "empresaScrapingSequence", sequenceName = "EMPRESA_SCRAPING_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "empresaScrapingSequence")
    @Column(name = "ID")
    private Long id;

    @Column(name = "HTML", columnDefinition="TEXT")
    private String html;

    @Column(name = "DATA_HISTORICO", columnDefinition = "TEXT")
    private Date dataHistorico;

    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EMPRESA_SCRAPING_EMPRESA"))
    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    private Empresa empresa;

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "SITUACAO_CADASTRAL")
    private String situacaoCadastral;

    @Column(name = "PARENTESCO")
    private String parentesco;

    @Column(name = "NATUREZA_JURIDICA")
    private String naturezaJuridica;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "NUMERO_LOGRADOURO")
    private String numeroLogradouro;

    @Column(name = "COMPLEMENTO_LOGRADOURO")
    private String complementoLogradouro;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CIDADE")
    private String cidade;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "scraping")
    private Consulta consulta;

    public EmpresaScraping() {

    }

    public EmpresaScraping(Empresa empresa) {
        this.setDataHistorico(new Date());
        this.setEmpresa(empresa);
        this.setBairro(empresa.getBairro());
        this.setCep(empresa.getCep());
        this.setComplementoLogradouro(empresa.getComplementoLogradouro());
        this.setLogradouro(empresa.getLogradouro());
        this.setNaturezaJuridica(empresa.getNaturezaJuridica());
        this.setNumeroLogradouro(empresa.getNumeroLogradouro());
        this.setParentesco(empresa.getParentesco());
        this.setRazaoSocial(empresa.getRazaoSocial());
        this.setSituacaoCadastral(empresa.getSituacaoCadastral());
    }

    public void setDados(Empresa empresa) {
        this.setDataHistorico(new Date());
        this.setEmpresa(empresa);
        this.setBairro(empresa.getBairro());
        this.setCep(empresa.getCep());
        this.setComplementoLogradouro(empresa.getComplementoLogradouro());
        this.setLogradouro(empresa.getLogradouro());
        this.setNaturezaJuridica(empresa.getNaturezaJuridica());
        this.setNumeroLogradouro(empresa.getNumeroLogradouro());
        this.setParentesco(empresa.getParentesco());
        this.setRazaoSocial(empresa.getRazaoSocial());
        this.setSituacaoCadastral(empresa.getSituacaoCadastral());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumeroLogradouro() {
        return numeroLogradouro;
    }

    public void setNumeroLogradouro(String numeroLogradouro) {
        this.numeroLogradouro = numeroLogradouro;
    }

    public String getComplementoLogradouro() {
        return complementoLogradouro;
    }

    public void setComplementoLogradouro(String complementoLogradouro) {
        this.complementoLogradouro = complementoLogradouro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
