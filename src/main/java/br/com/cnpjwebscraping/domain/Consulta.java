package br.com.cnpjwebscraping.domain;

import br.com.cnpjwebscraping.hardcoded.ConsultaStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "CONSULTA")
public class Consulta {

    @Id
    @SequenceGenerator(name = "consultaSequence", sequenceName = "CONSULTA_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "consultaSequence")
    @Column(name = "ID")
    private Long id;

    @Column(name = "TICKET")
    private UUID ticket = UUID.randomUUID();

    @Column(name = "DATA_CRIACAO")
    private Date dataAbertura;

    @Column(name = "DATA_FINALIZACAO")
    private Date dataFinalizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private ConsultaStatus status;

    @JoinColumn(name = "EMPRESA_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_CONSULTA_EMPRESA"))
    @OneToOne(fetch = FetchType.EAGER)
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

    public UUID getTicket() {
        return ticket;
    }

    public void setTicket(UUID ticket) {
        this.ticket = ticket;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
