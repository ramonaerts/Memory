package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"restcontrollers"})
public class MemoryRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoryRestApplication.class, args);
	}

}
