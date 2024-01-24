package org.example;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class AdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdmApplication.class, args);
	}

}
