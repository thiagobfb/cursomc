package com.thiagobernardo.cursomc.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.thiagobernardo.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		pagto.setDataVencimento(
				Date.from(LocalDate.now().plusWeeks(1).atStartOfDay(
						ZoneId.systemDefault()).toInstant()));
	}

}
