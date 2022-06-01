package com.github.viniciusfcf.ifood.cadastro.dto;


import com.github.viniciusfcf.ifood.cadastro.Prato;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface PratoMapper {
	
	public Prato toPrato(AtualizarPratoDTO dto, @MappingTarget Prato prato);
	
	public Prato toPrato(AdicionarPratoDTO dto);
	
	public PratoDTO toPratoDTO(Prato p);
	
}
