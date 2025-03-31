package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.ManufacturedProduct;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturedProductRepository extends JpaRepository<ManufacturedProduct, Long> {

}
