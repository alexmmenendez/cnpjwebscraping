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

    @JoinColumn(name = "CONSULTA_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_LOG_CONSULTA_CONSULTA"))
    @ManyToOne(optional=false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Consulta consulta;
}
