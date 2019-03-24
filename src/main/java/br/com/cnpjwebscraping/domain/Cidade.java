package br.com.cnpjwebscraping.domain;

import javax.persistence.*;

@Entity
@Table(name = "CIDADE")
public class Cidade {
	
	@Id
	@SequenceGenerator(name="cidadeSequence", sequenceName="CIDADE_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="cidadeSequence")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@JoinColumn(name = "ESTADO_ID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_CIDADE_ESTADO"))
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Estado estado;
	
	@Column(name = "url")
	private String url;
	
	public Cidade() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cidade [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}
	
}