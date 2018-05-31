package com.thiagobernardo.cursomc.dto;

import java.io.Serializable;

import com.thiagobernardo.cursomc.domain.Estado;

public class EstadoDTO implements Serializable {
	
	private static final long serialVersionUID = -817245270535648153L;
	
	private Integer id;
	private String nome;
	
	public EstadoDTO() {
	}

	public EstadoDTO(Estado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
