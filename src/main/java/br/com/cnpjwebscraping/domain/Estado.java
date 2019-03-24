package br.com.cnpjwebscraping.domain;

import javax.persistence.*;

@Entity
@Table(name = "ESTADO")
public class Estado {
	
	@Id
	@SequenceGenerator(name="estadoSequence", sequenceName="ESTADO_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="estadoSequence")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "UF")
	private String uf;
	
	@Column(name = "URL")
	private String url;
	
	public Estado() {
		
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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
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
		builder.append("Estado [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", uf=");
		builder.append(uf);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}
	
}