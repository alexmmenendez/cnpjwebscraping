package br.com.cnpjwebscraping.domain;


import br.com.cnpjwebscraping.hardcoded.CNPJDados;
import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "EMPRESA")
public class Empresa {

    @Id
    @SequenceGenerator(name = "empresaSequence", sequenceName = "EMPRESA_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "empresaSequence")
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ConsultaStatus status;

    @Column(name = "CONSULTA_DATA_CRIACAO")
    private Date consultaDataCriacao;

    @Column(name = "CONSULTA_DATA_FINALIZACAO")
    private Date consultaDataFinalizacao;

    @Column(name = "CNPJ", unique = true)
    private String cnpj;

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "DATA_ABERTURA")
    private Date dataAbertura;

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

    @Column(name = "INSCRICAO_ESTADUAL")
    private String inscricaoEstadual;

    @JoinColumn(name = "CIDADE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EMPRESA_CIDADE"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Cidade cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
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

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public ConsultaStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultaStatus status) {
        this.status = status;
    }

    public Date getConsultaDataCriacao() {
        return consultaDataCriacao;
    }

    public void setConsultaDataCriacao(Date consultaDataCriacao) {
        this.consultaDataCriacao = consultaDataCriacao;
    }

    public Date getConsultaDataFinalizacao() {
        return consultaDataFinalizacao;
    }

    public void setConsultaDataFinalizacao(Date consultaDataFinalizacao) {
        this.consultaDataFinalizacao = consultaDataFinalizacao;
    }
}
