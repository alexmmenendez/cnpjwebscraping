package br.com.cnpjwebscraping.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "HISTORICO_EMPRESA")
public class HistoricoEmpresa {

    @Id
    @SequenceGenerator(name = "historicoEmpresaSequence", sequenceName = "HISTORICO_EMPRESA_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "historicoEmpresaSequence")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATA_HISTORICO")
    private Date dataHistorico;

    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_HISTORICO_EMPRESA_EMPRESA"))
    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    private Empresa empresa;

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;

    @Column(name = "DATA_SITUACAO_CADASTRAL")
    private Date dataSituacaoCadastral;

    @Column(name = "DATA_SITUACAO_ESPECIAL")
    private Date dataSituacaoEspecial;

    @Column(name = "SITUACAO_CADASTRAL")
    private String situacaoCadastral;

    @Column(name = "PORTE")
    private String porte;

    @Column(name = "PARENTESCO")
    private String parentesco;

    @Column(name = "ATIVIDADE_ECONOMICA_PRINCIPAL")
    private String atividadeEconomicaPrincipal;

    @Column(name = "ATIVIDADE_ECONOMICA_SECUNDARIA")
    private String atividadeEconomicaSecundaria;

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

    @JoinColumn(name = "CIDADE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_HISTORICO_EMPRESA_CIDADE"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Cidade cidade;

    @Column(name = "ENDERECO_ELETRONICO")
    private String enderecoEletronico;

    @Column(name = "EFR")
    private String efr;

    @Column(name = "MOTIVO_SITUACAO_CADASTRAL")
    private String motivoSituacaoCadastral;

    @Column(name = "SITUACAO_ESPECIAL")
    private String situacaoEspecial;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "historico")
    private Consulta consulta;

    public HistoricoEmpresa() {

    }

    public HistoricoEmpresa(Empresa empresa) {
        this.dataHistorico = new Date();
        this.empresa = empresa;
        this.atividadeEconomicaPrincipal = empresa.getAtividadeEconomicaPrincipal();
        this.atividadeEconomicaSecundaria = empresa.getAtividadeEconomicaSecundaria();
        this.bairro = empresa.getBairro();
        this.cep = empresa.getCep();
        this.cidade = empresa.getCidade();
        this.complementoLogradouro = empresa.getComplementoLogradouro();
        this.dataSituacaoCadastral = empresa.getDataSituacaoCadastral();
        this.dataSituacaoEspecial = empresa.getDataSituacaoEspecial();
        this.efr = empresa.getEfr();
        this.enderecoEletronico = empresa.getEnderecoEletronico();
        this.logradouro = empresa.getLogradouro();
        this.motivoSituacaoCadastral = empresa.getMotivoSituacaoCadastral();
        this.naturezaJuridica = empresa.getNaturezaJuridica();
        this.nomeFantasia = empresa.getNomeFantasia();
        this.numeroLogradouro = empresa.getNumeroLogradouro();
        this.parentesco = empresa.getParentesco();
        this.porte = empresa.getPorte();
        this.razaoSocial = empresa.getRazaoSocial();
        this.situacaoCadastral = empresa.getSituacaoCadastral();
        this.situacaoEspecial = empresa.getSituacaoEspecial();
    }

    public void setDados(Empresa empresa) {
        this.dataHistorico = new Date();
        this.empresa = empresa;
        this.atividadeEconomicaPrincipal = empresa.getAtividadeEconomicaPrincipal();
        this.atividadeEconomicaSecundaria = empresa.getAtividadeEconomicaSecundaria();
        this.bairro = empresa.getBairro();
        this.cep = empresa.getCep();
        this.cidade = empresa.getCidade();
        this.complementoLogradouro = empresa.getComplementoLogradouro();
        this.dataSituacaoCadastral = empresa.getDataSituacaoCadastral();
        this.dataSituacaoEspecial = empresa.getDataSituacaoEspecial();
        this.efr = empresa.getEfr();
        this.enderecoEletronico = empresa.getEnderecoEletronico();
        this.logradouro = empresa.getLogradouro();
        this.motivoSituacaoCadastral = empresa.getMotivoSituacaoCadastral();
        this.naturezaJuridica = empresa.getNaturezaJuridica();
        this.nomeFantasia = empresa.getNomeFantasia();
        this.numeroLogradouro = empresa.getNumeroLogradouro();
        this.parentesco = empresa.getParentesco();
        this.porte = empresa.getPorte();
        this.razaoSocial = empresa.getRazaoSocial();
        this.situacaoCadastral = empresa.getSituacaoCadastral();
        this.situacaoEspecial = empresa.getSituacaoEspecial();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Date getDataSituacaoCadastral() {
        return dataSituacaoCadastral;
    }

    public void setDataSituacaoCadastral(Date dataSituacaoCadastral) {
        this.dataSituacaoCadastral = dataSituacaoCadastral;
    }

    public Date getDataSituacaoEspecial() {
        return dataSituacaoEspecial;
    }

    public void setDataSituacaoEspecial(Date dataSituacaoEspecial) {
        this.dataSituacaoEspecial = dataSituacaoEspecial;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getAtividadeEconomicaPrincipal() {
        return atividadeEconomicaPrincipal;
    }

    public void setAtividadeEconomicaPrincipal(String atividadeEconomicaPrincipal) {
        this.atividadeEconomicaPrincipal = atividadeEconomicaPrincipal;
    }

    public String getAtividadeEconomicaSecundaria() {
        return atividadeEconomicaSecundaria;
    }

    public void setAtividadeEconomicaSecundaria(String atividadeEconomicaSecundaria) {
        this.atividadeEconomicaSecundaria = atividadeEconomicaSecundaria;
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

    public String getEnderecoEletronico() {
        return enderecoEletronico;
    }

    public void setEnderecoEletronico(String enderecoEletronico) {
        this.enderecoEletronico = enderecoEletronico;
    }

    public String getEfr() {
        return efr;
    }

    public void setEfr(String efr) {
        this.efr = efr;
    }

    public String getMotivoSituacaoCadastral() {
        return motivoSituacaoCadastral;
    }

    public void setMotivoSituacaoCadastral(String motivoSituacaoCadastral) {
        this.motivoSituacaoCadastral = motivoSituacaoCadastral;
    }

    public String getSituacaoEspecial() {
        return situacaoEspecial;
    }

    public void setSituacaoEspecial(String situacaoEspecial) {
        this.situacaoEspecial = situacaoEspecial;
    }


    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
