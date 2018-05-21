package com.thiagobernardo.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagobernardo.cursomc.domain.Pedido;
import com.thiagobernardo.cursomc.repositories.PedidoRepository;
import com.thiagobernardo.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato ! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
