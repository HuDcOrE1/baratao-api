package br.com.br.baratao.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.br.baratao.modelo.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {
	
	@Bean
	public Docket barataoApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.br.baratao")) // Por onde vai começar a ler as classes para documentar
				.paths(PathSelectors.ant("/**")) // end-points para documentar
				.build()
				.ignoredParameterTypes(Usuario.class) // ignora usuário dos endpoints para não mostrar senha 
				.globalOperationParameters(Arrays.asList( // criando um endpoint para receber o webtoken no delete
						new ParameterBuilder()
						.name("Authorization") 
						.description("Header para token JWT")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()));
	}

}
