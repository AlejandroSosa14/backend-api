package com.digitalhouse.proyectofinal;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ProyectofinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectofinalApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(CarRepository carRepository) {
		return args -> {
			carRepository.save(
					new CarEntity(
							null, "NISSAN",
							"X Trail Advance",
							2025,
							true,
							"gasolina",
							"automatico",
							new BigDecimal(1000),
							"{\n" +
									"  \"urls\": [\n" +
									"    \"http://localhost:8181/images/nissan/1.jpg\",\n" +
									"    \"http://localhost:8181/images/nissan/2.jpg\",\n" +
									"    \"http://localhost:8181/images/nissan/3.jpg\",\n" +
									"    \"http://localhost:8181/images/nissan/4.jpg\",\n" +
									"    \"http://localhost:8181/images/nissan/5.jpg\",\n" +
									"    \"http://localhost:8181/images/nissan/6.jpg\"\n" +
									"  ]\n" +
									"}"));

			carRepository.save(
					new CarEntity(
							"NISSAN",
							"Versa",
							2020,
							false,
							"gasolina",
							"estandar",
							new BigDecimal(450)));
		};
	}


}
