package com.thiagobernardo.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.thiagobernardo.cursomc.domain.Cliente;
import com.thiagobernardo.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
