package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.enums.MaterialType;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.FirstProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FirstProductOrderRepository extends JpaRepository<FirstProductOrder, Long> {

    Optional<FirstProductOrder> findByIdAndDeletedFalse(Long id);

    List<FirstProductOrder> findAllByDeletedFalse();

    Optional<FirstProductOrder> findByFirstProductAndDeletedFalse(FirstProduct firstProduct);

    List<FirstProductOrder> findAllByDeletedFalseAndFirstProductMaterialType(MaterialType materialType);

}
