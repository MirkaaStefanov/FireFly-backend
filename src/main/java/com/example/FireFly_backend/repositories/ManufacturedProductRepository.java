package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.ManufacturedProduct;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturedProductRepository extends JpaRepository<ManufacturedProduct, Long> {
    Optional<ManufacturedProduct> findByIdAndDeletedFalse(Long id);

    List<ManufacturedProduct> findAllByDeletedFalse();

}
