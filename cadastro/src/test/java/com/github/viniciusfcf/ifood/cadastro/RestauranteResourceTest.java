package com.github.viniciusfcf.ifood.cadastro;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response.Status;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.viniciusfcf.ifood.cadastro.dto.AtualizarRestauranteDTO;
import com.github.viniciusfcf.ifood.cadastro.util.TokenUtils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

@DBRider
@DBUnit(caseInsensitiveStrategy =  Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
public class RestauranteResourceTest {
	
	private String token;
	
	@BeforeEach
	public void gereToken() throws Exception {
		token = TokenUtils.generateTokenString("/JWTProprietarioClaims.json", null);
	}
	
	@Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testBuscarRestaurantes() {
        String resultado = given()
                .when().get("/restaurantes")
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().asString();
        Approvals.verifyJson(resultado);
    }
	
	private RequestSpecification given() {
		return RestAssured.given()
				.contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
	}
	
	@Test
	@DataSet("restaurantes-cenario-1.yml")
	public void testAlterarRestaurante() {
		AtualizarRestauranteDTO dto = new AtualizarRestauranteDTO();
		dto.nomeFantasia = "novoNome";
		Long parameterValue = 123L;
		given()
				.with().pathParam("id", parameterValue)
				.body(dto)
				.when().put("/restaurantes/{id}")
				.then()
				.statusCode(Status.NO_CONTENT.getStatusCode())
				.extract().asString();
		
		Restaurante findById = Restaurante.findById(parameterValue);
		
		Assert.assertEquals(dto.nomeFantasia, findById.nome);
	}
	
//	@Test
//	@DataSet("restaurantes-cenario-1.yml")
//	public void testAdicionarRestaurantes() {
//		Restaurante dto = new Restaurante();
//		dto.nome = "Teste novo nome";
//		dto.proprietario = "eu";
//		Long parameterValue = 123L;
//		given()
//			.body(dto)
//			.when().put("/restaurantes")
//			.then()
//			.statusCode(Status.NO_CONTENT.getStatusCode())
//			.extract().asString();
//
//		Restaurante findById = Restaurante.findById(parameterValue);
//		
//		Assert.assertEquals(dto.nome, findById.nome);
//	}
	
	
}