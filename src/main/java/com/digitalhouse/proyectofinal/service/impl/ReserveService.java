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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${url.backend}")
    private String urlBackend;

    public List<ReserveEntity> findAll() {
        return reserveRepository.findAll();
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

            if (!carEntity.get().getStatus()) {
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
                .map(carEntity -> carEntity.getName() + " " + carEntity.getBrand() + " " + carEntity.getModel())
                .collect(Collectors.joining(", "));

        String message = String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Reservation Confirmation</title>
                <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
            </head>
            <body style="font-family: 'Poppins', sans-serif; background-color: #F5F5F5; display: block; padding: 20px 0; text-align: center;">
                <div style="width: 700px; background-color: white; margin: 0 auto; border-radius: 10px; box-shadow: 0 10px 30px -5px rgba(48, 90, 227, 0.371);">
                    
                    <!-- Header -->
                    <div style="background-color: #1736c0; padding: 30px; text-align: center; border-radius: 10px 10px 0 0;">
                        <h1 style="font-size: 30px; font-weight: 700; color: white;">Reservation Confirmed</h1>
                        <p style="color: white; opacity: 0.9; margin-top: 10px;">Your booking details are below</p>
                    </div>
                    
                    <!-- Contenido -->
                    <div style="padding: 40px;">
                        <div style="margin-bottom: 30px;">
                            <p style="color: #4a5568; margin-bottom: 10px;">Hi %s,</p>
                            <p style="color: #718096;">Thank you for your reservation. Here are your details:</p>
                        </div>
                        
                        <!-- Detalles de la Tabla -->
                        <div style="margin-bottom: 40px; overflow: hidden; border-radius: 5px; border: 1px solid #e2e8f0;">
                            <table style="border-collapse: collapse; text-align: center; width: -webkit-fill-available;">
                                <tbody style="background-color: white; border-top: 1px solid #e2e8f0;">
                                    <tr>
                                        <td style="padding: 15px; font-weight: 500; color: #1a202c; background-color: #f7fafc;">Dates</td>
                                        <td style="padding: 15px; color: #6b7280;">%s</td>
                                    </tr>
                                    <tr>
                                        <td style="padding: 15px; font-weight: 500; color: #1a202c; background-color: #f7fafc;">Cars</td>
                                        <td style="padding: 15px; color: #6b7280;">%s</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                        <!-- Action Button -->
                        <div style="text-align: center; margin-bottom: 40px;">
                            <a href="%s" style="background-color: #1736c0; color: white; font-weight: 500; padding: 15px 30px; border-radius: 10px; text-decoration: none; display: inline-block;">
                                Manage Your Reservation
                            </a>
                        </div>
                        
                        <!-- Footer -->
                        <div style="border-top: 1px solid #e2e8f0; padding-top: 30px; text-align: center;">
                            <p style="color: #4a5568; margin-bottom: 10px;">Best regards,</p>
                            <p style="font-weight: 500; color: #2d3748; margin-bottom: 20px;">The Team</p>
                            <p style="font-size: 14px; color: #6b7280;">If you did not make this reservation, please contact us immediately.</p>
                        </div>
                    </div>
                </div>
            </body>
            </html>
            
            """, userEntity.getName(), reserveEntity.getStartDate() + " - " + reserveEntity.getEndDate(), listCars, urlBackend + "/login");
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

    public ReserveEntity findByIdAndUserId(Long serverId, Long idUser) {
        return reserveRepository.findByIdAndUserId(serverId, idUser).orElseThrow(() -> new ResourceNotFoundException("Reserve not found"));
    }
}
