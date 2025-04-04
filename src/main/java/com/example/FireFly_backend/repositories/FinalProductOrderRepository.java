package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FinalProductOrder;
import com.example.FireFly_backend.models.entity.MidProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinalProductOrderRepository extends JpaRepository<FinalProductOrder, Long> {

    Optional<FinalProductOrder> findByIdAndDeletedFalse(Long id);

    Optional<FinalProductOrder> findByFinalProduct(FinalProduct finalProduct);

    List<FinalProductOrder> findAllByDeletedFalse();


}
