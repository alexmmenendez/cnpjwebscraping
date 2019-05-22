package br.com.cnpjwebscraping.domain;

import br.com.cnpjwebscraping.hardcoded.ParametroConfiguracao;

import javax.persistence.*;

@Entity
@Table(name = "CONFIGURACAO", uniqueConstraints = @UniqueConstraint(columnNames = {"PARAMETRO"}, name = "PARAMETRO_UNIQUE"))
public class Configuracao {

    @Id
    @SequenceGenerator(name = "configuracaoSequence", sequenceName = "CONFIGURACAO_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "configuracaoSequence")
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "PARAMETRO")
    private ParametroConfiguracao parametro;

    @Column(name = "VALOR")
    private String valor;

    public Configuracao() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParametroConfiguracao getParametro() {
        return parametro;
    }

    public void setParametro(ParametroConfiguracao parametro) {
        this.parametro = parametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Configuracao other = (Configuracao) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Configuracao [id=");
        builder.append(id);
        builder.append(", parametro=");
        builder.append(parametro);
        builder.append(", valor=");
        builder.append(valor);
        builder.append("]");
        return builder.toString();
    }

}