package com.digitalhouse.proyectofinal.service.impl;

import com.digitalhouse.proyectofinal.entity.CarEntity;
import com.digitalhouse.proyectofinal.entity.ReserveEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;
import com.digitalhouse.proyectofinal.exception.BadRequestException;
import com.digitalhouse.proyectofinal.exception.EmailSendingException;
import com.digitalhouse.proyectofinal.exception.ResourceNotFoundException;
import com.digitalhouse.proyectofinal.repository.CarRepository;
import com.digitalhouse.proyectofinal.repository.ReserveRepository;
import com.digitalhouse.proyectofinal.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final UserRepository userRepository;
    private final ReserveRepository reserveRepository;
    private final CarRepository carRepository;
    private final EmailService emailService;

    public List<ReserveEntity> findAll() {
        return reserveRepository.findAll();
    }

    public ReserveEntity findById(Long id) {
        return reserveRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserve not found"));
    }

    public List<ReserveEntity> findByUserId(UserEntity userEntity) {
        return reserveRepository.findByUser(userEntity);
    }

    public ReserveEntity save(ReserveEntity reserveEntity) {

        if (reserveEntity.getStartDate().isBefore(java.time.LocalDate.now())) {
            throw new BadRequestException("Start date must be after today");
        }

        if (reserveEntity.getStartDate() == null || reserveEntity.getEndDate() == null) {
            throw new BadRequestException("Start date and end date are required");
        }

        if (reserveEntity.getEndDate().isBefore(reserveEntity.getStartDate())) {
            throw new BadRequestException("End date must be after start date");
        }

        if (reserveEntity.getStartDate().isBefore(java.time.LocalDate.now())) {
            throw new BadRequestException("Start date must be after today");
        }

        Set<CarEntity> cars = new HashSet<>();

        reserveEntity.getCars().forEach(car -> {

            Optional<CarEntity> carEntity = carRepository.findById(car.getId());

            if (carEntity.isEmpty()) {
                throw new ResourceNotFoundException(car.getName() + " car not found");
            }

            if (!carEntity.get().getStatus()){
                throw new BadRequestException(carEntity.get().getName() + " car is already reserved");
            }

            carEntity.get().setStatus(Boolean.FALSE);

            cars.add(carEntity.get());

        });

        UserEntity userEntity = userRepository.findByName(reserveEntity.getUser().getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!userEntity.getActive()) {
            throw new ResourceNotFoundException("User is not active");
        }

        UserEntity userBuilder = UserEntity.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .build();

        reserveEntity.setUser(userBuilder);
        reserveEntity.setCars(cars);

        // Mensaje personalizado --> Mejorar
        String subject = "Reservation details";
        String listCars = cars.stream()
                .map(carEntity -> carEntity.getName() + " "+ carEntity.getBrand() + " " + carEntity.getModel())
                .collect(Collectors.joining(", "));

        String message = """
                Hi %s,
  
                Here are your details:
                - Dates: %s
                - Cars: %s

                Link for login: http://localhost:8181/login

                Best regards,
                The Team
                """.formatted(userEntity.getName(), reserveEntity.getStartDate() + " - " + reserveEntity.getEndDate(), listCars);
        try {
            emailService.sendEmail(userEntity.getEmail(), subject, message);
        } catch (MessagingException me) {
            throw new EmailSendingException("Failed to send reservation email. Please check email service.");
        }

        return reserveRepository.save(reserveEntity);
    }

    public List<ReserveEntity> findByUserId(Long id) {
        return reserveRepository.findByUserId(id);
    }
}
