package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.models.entity.MidProductNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MidProductNeedRepository extends JpaRepository<MidProductNeed, Long> {

    List<MidProductNeed> findAllByMidProductAndDeletedFalse(MidProduct midProduct);
    Optional<MidProductNeed> findByIdAndDeletedFalse(Long id);
    List<MidProductNeed> findAllByDeletedFalse();

}
