package com.example.FireFly_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalProductNeed extends JpaRepository<FinalProductNeed, Long> {
}
