package com.thiagobernardo.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobernardo.cursomc.domain.Cliente;
import com.thiagobernardo.cursomc.repositories.ClienteRepository;
import com.thiagobernardo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato ! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
