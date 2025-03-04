package com.digitalhouse.proyectofinal;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.entity.CategoryEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.repository.CarRepository;
import com.digitalhouse.proyectofinal.repository.CategoryRepository;
import com.digitalhouse.proyectofinal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class ProyectofinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectofinalApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, CategoryRepository categoryRepository, CarRepository carRepository) {
        return args -> {

            userRepository.save(
                    new UserEntity(
                            null,
                            "jose",
                            "administrator@gmail.com",
                            new BCryptPasswordEncoder().encode("pass123"),
                            "admin",
                            true)

            );

            userRepository.save(
                    new UserEntity(
                            null,
                            "maria",
                            "customer1@gmail.com",
                            new BCryptPasswordEncoder().encode("123456789"),
                            "customer",
                            true)

            );

            userRepository.save(
                    new UserEntity(
                            null,
                            "alfonso",
                            "customer2@gmail.com",
                            new BCryptPasswordEncoder().encode("789456123"),
                            "customer",
                            false)

            );

            categoryRepository.save(new CategoryEntity(null, "Crossover", "Description"));
            categoryRepository.save(new CategoryEntity(null, "Sedan", "Description"));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000001",
                            "NISSAN",
                            "X Trail Advance",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1000),
                                    "[\n" +
                                    "    \"http://localhost:8181/images/nissan/1.jpg\",\n" +
                                    "    \"http://localhost:8181/images/nissan/2.jpg\",\n" +
                                    "    \"http://localhost:8181/images/nissan/3.jpg\",\n" +
                                    "    \"http://localhost:8181/images/nissan/4.jpg\",\n" +
                                    "    \"http://localhost:8181/images/nissan/5.jpg\",\n" +
                                    "    \"http://localhost:8181/images/nissan/6.jpg\"\n" +
                                    "  ]\n",
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000002",
                            "NISSAN",
                            "Versa",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(450),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));
        };
    }

}
