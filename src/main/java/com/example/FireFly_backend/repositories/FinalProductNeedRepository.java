package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FinalProductNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinalProductNeedRepository extends JpaRepository<FinalProductNeed, Long> {

    List<FinalProductNeed> findAllByFinalProductAndDeletedFalse(FinalProduct finalProduct);

    Optional<FinalProductNeed> findByIdAndDeletedFalse(Long id);

    List<FinalProductNeed> findAllByDeletedFalse();
}
