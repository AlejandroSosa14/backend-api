package com.digitalhouse.proyectofinal.repository;

import com.digitalhouse.proyectofinal.entity.ReserveEntity;
import com.digitalhouse.proyectofinal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity, Long>{

    List<ReserveEntity> findByUser(UserEntity userEntity);

    List<ReserveEntity> findByUserId(Long userId);

    Optional<ReserveEntity> findByIdAndUserId(Long reserveId, Long userId);

}
