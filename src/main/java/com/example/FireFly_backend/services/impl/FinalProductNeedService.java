package com.example.FireFly_backend.services.impl;

import com.example.FireFly_backend.repositories.FinalProductNeedRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FinalProductNeedService {

    private final FinalProductNeedRepository finalProductNeedRepository;
    private final ModelMapper modelMapper;



}
