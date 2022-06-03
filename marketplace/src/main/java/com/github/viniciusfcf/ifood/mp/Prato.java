package com.github.viniciusfcf.ifood.mp;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

import com.github.viniciusfcf.ifood.mp.dto.PratoDTO;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

public class Prato {

	public Long id;
	
	public String nome;
	
	public String descricao;
	
	public Restaurante restaurante;
	
	public BigDecimal preco;

	public static Multi<PratoDTO> findAll(PgPool prato) {
		Uni<RowSet<Row>> preparedQuery = (Uni<RowSet<Row>>) prato.preparedQuery("select * from prato ").execute();// Colocar o Execute no final se não da erro 500
		return UniToMulti(preparedQuery);
	}
	
	public static Multi<PratoDTO> findAll(PgPool client,Long idRestaurante) {
		Uni<RowSet<Row>> preparedQuery = 
				(Uni<RowSet<Row>>) client.preparedQuery("select * from prato "
						+ "where prato.restaurante_id = $1 ORDER BY nome ASC")
				.execute(Tuple.of(idRestaurante)); //Tuple colocar depois do execute
		return UniToMulti(preparedQuery);
	}

	private static Multi<PratoDTO> UniToMulti(Uni<RowSet<Row>> queryResult) {
		return queryResult.onItem()
				.transformToMulti(set -> Multi.createFrom().items(() -> {
					return StreamSupport.stream(set.spliterator(), false);
				}))
				.onItem().transform(PratoDTO::from); // Colocar o transform no lugar do apply
	}

	public static Uni<PratoDTO> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT * FROM prato WHERE id = $1").execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> iterator.hasNext() ? PratoDTO.from(iterator.next()) : null);
    }
	
}
 