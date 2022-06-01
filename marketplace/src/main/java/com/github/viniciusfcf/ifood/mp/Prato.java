package com.github.viniciusfcf.ifood.mp;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

public class Prato {

	public Long id;
	
	public String nome;
	
	public String descricao;
	
	public Restaurante restaurante;
	
	public BigDecimal preco;

	public static Multi<PratoDTO> findAll(PgPool pgPool) {
		Uni<RowSet<Row>> preparedQuery = (Uni<RowSet<Row>>) pgPool.preparedQuery("select * from prato");
		return preparedQuery.onItem().transformToMulti(rowSet -> Multi.createFrom().items(() -> {
			return StreamSupport.stream(rowSet.spliterator(), false);		
		})).onItem().transform(PratoDTO::from);
	}
	
}
 