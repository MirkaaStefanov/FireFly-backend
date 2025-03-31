package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.MidProductNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidProductNeedRepository extends JpaRepository<MidProductNeed, Long> {
}
