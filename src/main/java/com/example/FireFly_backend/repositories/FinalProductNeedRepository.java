package com.example.FireFly_backend.repositories;

import com.example.FireFly_backend.models.entity.FinalProductNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalProductNeedRepository extends JpaRepository<FinalProductNeed, Long> {
}
