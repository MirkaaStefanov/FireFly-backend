package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;

import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.models.entity.MidProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MidProductOrderRepository extends JpaRepository<MidProductOrder, Long> {

    Optional<MidProductOrder> findByIdAndDeletedFalse(Long id);

    List<MidProductOrder> findAllByDeletedFalse();

    Optional<MidProductOrder> findByMidProduct(MidProduct midProduct);
}
