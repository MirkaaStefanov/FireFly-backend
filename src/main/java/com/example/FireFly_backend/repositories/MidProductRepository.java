package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MidProductRepository extends JpaRepository<MidProduct, Long> {
    Optional<MidProduct> findByIdAndDeletedFalse(Long id);
    List<MidProduct> findAllByDeletedFalse();
}
