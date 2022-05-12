package br.com.zup.edu.biblioteca.integration;

import br.com.zup.edu.biblioteca.controller.LivroRequest;
import br.com.zup.edu.biblioteca.core.BaseIntegrationTest;
import br.com.zup.edu.biblioteca.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CadastrarNovoLivroControllerTest extends BaseIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @BeforeEach
    void setup() {
        livroRepository.deleteAll();
    }

    @Test
    void deveCadastrarUmLivroComSucesso() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                "Java Efetivo",
                "As melhores práticas para a plataforma Java",
                "9780132345286",
                LocalDate.of(2001, 02, 01)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        mockMvc.perform(requestHttp)
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http://localhost/livros/*"));

        assertThat(livroRepository.findAll())
                .isNotEmpty()
                .hasSize(1);

    }

    @Test
    void naoDeveCadastrarUmLivroComTituloInvalido() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                "Lemon drops chocolate croissant macaroon brownie. Marshmallow jelly sugar plum bear claw bonbon." +
                        " Pie candy canes lemon drops lemon drops chocolate bar chupa chups oat cake biscuit muffin." +
                        " Sweet jujubes bear claw marzipan topping. Sweet roll shortbread tiramisu pudding ice cream cookie jelly-o tootsie roll.",
                "Lemon drops chocolate croissant macaroon brownie",
                "9780132345286",
                LocalDate.of(2001, 02, 01)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        String payloadResponse = mockMvc.perform(requestHttp)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(payloadResponse.contains("O campo titulo o comprimento deve ser entre 0 e 200")).isTrue();
        assertThat(livroRepository.findAll()).isEmpty();

    }

    @Test
    void naoDeveCadastrarUmLivroSemInformarTitulo() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                null,
                "Lemon drops chocolate croissant macaroon brownie",
                "9780132345286",
                LocalDate.of(2001, 02, 01)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        String payloadResponse = mockMvc.perform(requestHttp)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(payloadResponse.contains("O campo titulo não deve estar em branco")).isTrue();
        assertThat(livroRepository.findAll()).isEmpty();

    }

    @Test
    void naoDeveCadastrarUmLivroComDescricaoInvalida() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                "Java Efetivo",
                "Lemon drops chocolate croissant macaroon brownie. Marshmallow jelly sugar plum bear claw bonbon. " +
                        "Pie candy canes lemon drops lemon drops chocolate bar chupa chups oat cake biscuit muffin." +
                        " Sweet jujubes bear claw marzipan topping. Sweet roll shortbread tiramisu pudding ice cream cookie jelly-o tootsie roll." +
                        " Bonbon jelly beans candy jelly-o ice cream tiramisu marzipan. Cotton candy tootsie roll soufflé cookie chupa chups bear claw powder pie." +
                        " Chocolate cake liquorice gingerbread macaroon gingerbread cheesecake sesame snaps toffee gummi bears." +
                        " Pastry jelly beans jelly-o jujubes gummies topping cookie. Chupa chups marshmallow pudding chupa chups icing dragée fruitcake fruitcake." +
                        " Apple pie cotton candy pie bonbon lollipop." +
                        " Oat cake jelly beans brownie cheesecake sweet carrot cake jujubes." +
                        "Biscuit donut jelly beans oat cake cotton candy chocolate. Marzipan wafer liquorice wafer brownie. " +
                        "Dragée fruitcake donut gingerbread lollipop chocolate lemon drops topping. " +
                        "Biscuit tiramisu cookie soufflé dragée apple pie macaroon sesame snaps gingerbread. " +
                        "Dessert sweet roll liquorice pastry croissant macaroon pastry tootsie roll. " +
                        "Cake caramels sweet roll lemon drops sesame snaps. Cotton candy liquorice tart oat cake biscuit sesame snaps." +
                        " Dessert soufflé candy canes cheesecake shortbread icing gummi bears. Lemon drops gummi bears danish cake croissant." +
                        " Pie sesame snaps cotton candy caramels gingerbread. Bear claw gummies liquorice gummies powder." +
                        "Lemon drops chocolate croissant macaroon brownie. Marshmallow jelly sugar plum bear claw bonbon." +
                        " Pie candy canes lemon drops lemon drops chocolate bar chupa chups oat cake biscuit muffin." +
                        "Sweet jujubes bear claw marzipan topping. Sweet roll shortbread tiramisu pudding ice cream cookie jelly-o tootsie roll." +
                        "Bonbon jelly beans candy jelly-o ice cream tiramisu marzipan. Cotton candy tootsie roll soufflé cookie chupa chups bear claw powder pie." +
                        "Chocolate cake liquorice gingerbread macaroon gingerbread cheesecake sesame snaps toffee gummi bears." +
                        "Pastry jelly beans jelly-o jujubes gummies topping cookie. Chupa chups marshmallow pudding chupa chups icing dragée fruitcake fruitcake." +
                        "Pie sesame snaps cotton candy caramels gingerbread. Bear claw gummies liquorice gummies powder ",
                "9780132345286",
                LocalDate.of(2001, 02, 01)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        String payloadResponse = mockMvc.perform(requestHttp)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(payloadResponse.contains("O campo descricao o comprimento deve ser entre 0 e 2000")).isTrue();
        assertThat(livroRepository.findAll()).isEmpty();

    }

    @Test
    void naoDeveCadastrarUmLivroComIsbnInvalido() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                "Java Efetivo",
                "As melhores práticas para a plataforma Java",
                "teste",
                LocalDate.of(2001, 02, 01)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        String payloadResponse = mockMvc.perform(requestHttp)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(payloadResponse.contains("O campo isbn ISBN inválido")).isTrue();
        assertThat(livroRepository.findAll()).isEmpty();

    }

    @Test
    void naoDeveCadastrarUmLivroComIsbnEmBranco() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                "Java Efetivo",
                "As melhores práticas para a plataforma Java",
                " ",
                LocalDate.of(2001, 02, 01)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        String payloadResponse = mockMvc.perform(requestHttp)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(payloadResponse.contains("O campo isbn não deve estar em branco")).isTrue();
        assertThat(payloadResponse.contains("O campo isbn ISBN inválido")).isTrue();
        assertThat(livroRepository.findAll()).isEmpty();

    }

    @Test
    void naoDeveCadastrarUmLivroComDataNoFuturo() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                "Java Efetivo",
                "As melhores práticas para a plataforma Java",
                "9780132345286",
                LocalDate.now().plusDays(1)
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        String payloadResponse = mockMvc.perform(requestHttp)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(payloadResponse.contains("O campo dataPublicacao deve ser uma data no passado ou no presente")).isTrue();
        assertThat(livroRepository.findAll()).isEmpty();

    }

    @Test
    void naoDeveCadastrarUmLivroSemDataDePublicacao() throws Exception {
        LivroRequest livroRequest = new LivroRequest(
                "Java Efetivo",
                "As melhores práticas para a plataforma Java",
                "9780132345286",
               null
        );

        String payload = mapper.writeValueAsString(livroRequest);

        MockHttpServletRequestBuilder requestHttp = MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt-br")
                .content(payload);

        String payloadResponse = mockMvc.perform(requestHttp)
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(payloadResponse.contains("O campo dataPublicacao não deve ser nulo")).isTrue();
        assertThat(livroRepository.findAll()).isEmpty();

    }

}