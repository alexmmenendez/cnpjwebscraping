package br.com.cnpjwebscraping.domain;

import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONSULTA")
public class Consulta {

    @Id
    @SequenceGenerator(name = "empresaSequence", sequenceName = "EMPRESA_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "empresaSequence")
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATA_CRIACAO")
    private Date dataAbertura;

    @Column(name = "DATA_FINALIZACAO")
    private Date dataFinalizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ConsultaStatus status;

    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_CONSULTA_EMPRESA"))
    @ManyToOne(optional=false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Empresa empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public ConsultaStatus getStatus() {
        return status;
    }

    public void setStatus(ConsultaStatus status) {
        this.status = status;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
