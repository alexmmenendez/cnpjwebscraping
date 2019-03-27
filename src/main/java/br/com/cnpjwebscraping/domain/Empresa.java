package br.com.cnpjwebscraping.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMPRESA")
public class Empresa {

    @Id
    @SequenceGenerator(name = "empresaSequence", sequenceName = "EMPRESA_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "empresaSequence")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CNPJ", unique = true)
    private String cnpj;

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;

    @Column(name = "DATA_ABERTURA")
    private Date dataAbertura;

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

    @JoinColumn(name = "CIDADE_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EMPRESA_CIDADE"))
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
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

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", dataAbertura=" + dataAbertura +
                ", dataSituacaoCadastral=" + dataSituacaoCadastral +
                ", dataSituacaoEspecial=" + dataSituacaoEspecial +
                ", situacaoCadastral='" + situacaoCadastral + '\'' +
                ", porte='" + porte + '\'' +
                ", parentesco='" + parentesco + '\'' +
                ", atividadeEconomicaPrincipal='" + atividadeEconomicaPrincipal + '\'' +
                ", atividadeEconomicaSecundaria='" + atividadeEconomicaSecundaria + '\'' +
                ", naturezaJuridica='" + naturezaJuridica + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numeroLogradouro='" + numeroLogradouro + '\'' +
                ", complementoLogradouro='" + complementoLogradouro + '\'' +
                ", cep='" + cep + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade=" + cidade +
                ", enderecoEletronico='" + enderecoEletronico + '\'' +
                ", efr='" + efr + '\'' +
                ", motivoSituacaoCadastral='" + motivoSituacaoCadastral + '\'' +
                ", situacaoEspecial='" + situacaoEspecial + '\'' +
                '}';
    }
}
