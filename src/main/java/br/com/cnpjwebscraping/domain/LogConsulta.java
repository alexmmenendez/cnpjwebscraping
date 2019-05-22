package br.com.cnpjwebscraping.domain;

import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOG_CONSULTA")
public class LogConsulta {

    @Id
    @SequenceGenerator(name="logConsultaSequence", sequenceName="LOG_CONSULTA_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="logConsultaSequence")
    @Column(name = "ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_LOG")
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ConsultaStatus status;

    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_LOG_CONSULTA_EMPRESA"))
    @ManyToOne(optional=false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Empresa empresa;

    public LogConsulta() {

    }

    public LogConsulta(Date data, ConsultaStatus status, Empresa empresa) {
        this.data = data;
        this.status = status;
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
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
