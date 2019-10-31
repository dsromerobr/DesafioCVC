package br.com.cvc.DesafioCVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DesafioCvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioCvcApplication.class, args);

	}

}
