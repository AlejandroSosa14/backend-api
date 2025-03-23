package com.digitalhouse.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "reserve_car",
            joinColumns = @JoinColumn(name = "reserve_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private Set<CarEntity> cars;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
