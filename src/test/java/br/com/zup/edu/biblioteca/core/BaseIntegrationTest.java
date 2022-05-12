package br.com.zup.edu.biblioteca.core;

import br.com.zup.edu.biblioteca.BibliotecaApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BibliotecaApplication.class)
@AutoConfigureMockMvc()
public class BaseIntegrationTest {

}