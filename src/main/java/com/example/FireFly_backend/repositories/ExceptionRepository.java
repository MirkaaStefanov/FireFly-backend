package com.example.FireFly_backend.repositories;


import com.example.FireFly_backend.enums.ExceptionSeverity;
import com.example.FireFly_backend.models.entity.Exception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExceptionRepository extends JpaRepository<Exception, UUID> {
    List<Exception> findAllBySeverityIs(ExceptionSeverity exceptionSeverity);
}
