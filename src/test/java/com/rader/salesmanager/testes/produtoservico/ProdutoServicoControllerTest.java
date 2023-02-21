package com.rader.salesmanager.testes.produtoservico;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rader.salesmanager.api.controller.ProdutoServicoController;
import com.rader.salesmanager.api.model.input.ProdutoServicoInput;
import com.rader.salesmanager.domain.filter.ProdutoFilter;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.service.CadastroProdutoServicoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProdutoServicoController.class)
public class ProdutoServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CadastroProdutoServicoService produtoServicoService;

    @Test
    public void deveRetornarBadRequestAoCadastrarProdutoServicoComDadosInvalidos() throws Exception {
        ProdutoServicoInput input = new ProdutoServicoInput();
        input.setDescricao("");

        String json = new ObjectMapper().writeValueAsString(input);

        mockMvc.perform((RequestBuilder) post("/produtos-servicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(json)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarListaDeProdutosServicos() throws Exception {
        ProdutoServico produto1 = new ProdutoServico();
        produto1.setId(UUID.randomUUID());
        produto1.setDescricao("Produto 1");
        produto1.setPreco(BigDecimal.TEN);
        produto1.setProduto(true);

        ProdutoServico produto2 = new ProdutoServico();
        produto2.setId(UUID.randomUUID());
        produto2.setDescricao("Produto 2");
        produto2.setPreco(BigDecimal.valueOf(20));
        produto2.setProduto(true);

        List<ProdutoServico> produtos = Arrays.asList(produto1, produto2);

        given(produtoServicoService.buscarTodos((Specification<ProdutoServico>) any(
                ProdutoFilter.class), (Pageable) any(Pageable.class)))
                .filteredOn((Predicate<? super ProdutoServico>) new PageImpl<>(produtos));

        mockMvc.perform(get("/produtos-servicos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$._embedded.produtosServicos", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$._embedded.produtosServicos[0].descricao", is(produto1.getDescricao())))
                .andExpect((ResultMatcher) jsonPath("$._embedded.produtosServicos[0].preco", is(produto1.getPreco().doubleValue())))
                .andExpect((ResultMatcher) jsonPath("$._embedded.produtosServicos[0].produto", is(produto1.getProduto())))
                .andExpect((ResultMatcher) jsonPath("$._embedded.produtosServicos[1].descricao", is(produto2.getDescricao())))
                .andExpect((ResultMatcher) jsonPath("$._embedded.produtosServicos[1].preco", is(produto2.getPreco().doubleValue())))
                .andExpect((ResultMatcher) jsonPath("$._embedded.produtosServicos[1].produto", is(produto2.getProduto())));
    }

    @Test
    public void deveRetornarUmProdutoServico() throws Exception {
        ProdutoServico produto = new ProdutoServico();
        produto.setId(UUID.randomUUID());
        produto.setDescricao("Produto 1");
        produto.setPreco(BigDecimal.TEN);
        produto.setProduto(true);


        ResultActions resultActions = mockMvc.perform(get("/produtos-servicos/{id}", produto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}