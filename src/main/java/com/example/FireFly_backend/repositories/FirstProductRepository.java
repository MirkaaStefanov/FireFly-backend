package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FirstProductRepository extends JpaRepository<FirstProduct, Long> {
    Optional<FirstProduct> findByIdAndDeletedFalse(Long id);

    List<FirstProduct> findAllByDeletedFalse();
}
