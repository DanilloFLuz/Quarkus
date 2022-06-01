package com.github.viniciusfcf.ifood.cadastro.dto;

import java.math.BigDecimal;

import javax.validation.ConstraintValidatorContext;

import com.github.viniciusfcf.ifood.cadastro.infra.DTO;
import com.github.viniciusfcf.ifood.cadastro.infra.ValidDTO;

@ValidDTO
public class AdicionarPratoDTO implements DTO{
	
	public String nome;
	
	public String descricao;
	
	public RestauranteDTO restaurante;
	
	public BigDecimal preco;
	
	@Override
	public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
		// TODO Auto-generated method stub
		return DTO.super.isValid(constraintValidatorContext);
	}
}
