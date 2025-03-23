package com.digitalhouse.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank
    private String name;

    @Setter
    @NotBlank
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique = true)
    private String email;

    @Setter
    @NotBlank
    @Length(min = 8)
    private String password;

    @Setter
    @NotBlank
    private String type;

    @Setter
    @NotNull
    private Boolean active;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    @Setter
    private Set<CarEntity> favorites = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<ReserveEntity> reserves;

}
