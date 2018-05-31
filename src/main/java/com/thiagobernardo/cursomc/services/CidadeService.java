package com.thiagobernardo.cursomc.services;

import com.thiagobernardo.cursomc.domain.Cidade;
import com.thiagobernardo.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repository;
	
	public List<Cidade> findCidades(Integer estadoId) {
		return repository.findCidades(estadoId);
	}

}
