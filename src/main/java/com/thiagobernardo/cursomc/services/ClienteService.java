package com.thiagobernardo.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiagobernardo.cursomc.domain.Cidade;
import com.thiagobernardo.cursomc.domain.Cliente;
import com.thiagobernardo.cursomc.domain.Endereco;
import com.thiagobernardo.cursomc.domain.enums.Perfil;
import com.thiagobernardo.cursomc.domain.enums.TipoCliente;
import com.thiagobernardo.cursomc.dto.ClienteDTO;
import com.thiagobernardo.cursomc.dto.ClienteNewDTO;
import com.thiagobernardo.cursomc.repositories.ClienteRepository;
import com.thiagobernardo.cursomc.repositories.EnderecoRepository;
import com.thiagobernardo.cursomc.security.UserSS;
import com.thiagobernardo.cursomc.services.exceptions.AuthorizationException;
import com.thiagobernardo.cursomc.services.exceptions.DataIntegrityException;
import com.thiagobernardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3Service;
	
	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato ! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, 
				Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), encoder.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getCep(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		URI uri = s3Service.uploadFile(multipartFile);
		Cliente cli = find(user.getId());
		cli.setImageUrl(uri.toString());
		repository.save(cli);
		
		return uri;
	}
}
