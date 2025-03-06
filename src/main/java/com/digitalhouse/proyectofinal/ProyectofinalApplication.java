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
                            new BCryptPasswordEncoder().encode("pass12334565"),
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
                            new BigDecimal(1000),null,
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

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000003",
                            "NISSAN",
                            "X Trail Verza",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1320),null,
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000004",
                            "NISSAN",
                            "Versa",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(450),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));
            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000005",
                            "NISZA",
                            "X Trail New",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1000),null,
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000006",
                            "AUDI",
                            "Venom",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(1820),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000007",
                            "HYUNDAI",
                            "Hyundai Tucson",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1820),null,
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000008",
                            "Honda",
                            "Honda Civic",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(1852),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));

            //////
            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000009",
                            "Ford",
                            "Ford Mustang",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1000),null,
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000010",
                            "NISSAN",
                            "Versa",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(450),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000011",
                            "Volvo",
                            "Volvo XC60",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1320),null,
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000012",
                            "Tesla",
                            "Tesla Model S",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(450),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));
            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000013",
                            "BMW",
                            "BMW X5",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1000),null,
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000014",
                            "Porsche",
                            "Porsche 911",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(1820),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000015",
                            "LEXUS",
                            "Lexus RX",
                            2025,
                            true,
                            "gasolina",
                            "automatico",
                            new BigDecimal(1820),null,
                            new CategoryEntity(1L, "Crossover", "Description")));

            carRepository.save(
                    new CarEntity(
                            null,
                            "00000000000000016",
                            "KIA",
                            "Kia Sportage",
                            2020,
                            false,
                            "gasolina",
                            "estandar",
                            new BigDecimal(1852),
                            null,
                            new CategoryEntity(2L, "Sedan", "Description")));

        };

    }

}
