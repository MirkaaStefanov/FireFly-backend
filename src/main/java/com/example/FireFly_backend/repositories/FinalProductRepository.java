package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinalProductRepository extends JpaRepository<FinalProduct, Long> {
    Optional<FinalProduct> findByIdAndDeletedFalse(Long id);
    List<FinalProduct> findAllByDeletedFalse();
}
