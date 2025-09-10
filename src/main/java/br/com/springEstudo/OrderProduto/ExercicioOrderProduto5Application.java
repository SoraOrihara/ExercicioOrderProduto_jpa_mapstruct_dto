package br.com.springEstudo.OrderProduto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExercicioOrderProduto5Application {

	
	public static void main(String[] args) {
		SpringApplication.run(ExercicioOrderProduto5Application.class, args);
	}

}
