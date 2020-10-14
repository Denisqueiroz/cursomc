package com.queiroz.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.queiroz.cursomc.domain.Cliente;
import com.queiroz.cursomc.domain.enums.TipoCliente;
import com.queiroz.cursomc.dto.ClienteNewDTO;
import com.queiroz.cursomc.repositories.ClienteRepository;
import com.queiroz.cursomc.resources.exection.FieldMessage;
import com.queiroz.cursomc.services.validation.utiLs.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    
	@Autowired
	private ClienteRepository repo ;
	

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

       Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null) {
        	list.add(new FieldMessage("email", "Email já existente"));
        }
		 

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
