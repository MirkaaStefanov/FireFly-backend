package com.example.FireFly_backend.config;


import com.example.FireFly_backend.exceptions.user.UserNotFoundException;
import com.example.FireFly_backend.models.baseEntity.BaseEntity;
import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.dto.MidProductDTO;
import com.example.FireFly_backend.models.dto.common.BaseDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import com.example.FireFly_backend.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * Configuration class for defining beans related to application setup, such as ModelMapper, ObjectMapper,
 * UserDetailsService, AuthenticationProvider, AuthenticationManager, PasswordEncoder, and RestTemplate.
 */
@Configuration
@RequiredArgsConstructor
@EnableAspectJAutoProxy
@EnableAsync
public class ApplicationConfig {
    private final UserRepository repository;

    @Bean
    @Primary
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Global configuration
        configureGlobalSettings(modelMapper);

        // Product-specific mappings
        configureProductMappings(modelMapper);

        return modelMapper;
    }

    private void configureGlobalSettings(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setPropertyCondition(context -> {
                    if (context.getParent() != null &&
                            context.getParent().getDestination() instanceof BaseEntity &&
                            context.getParent().getSource() instanceof BaseDTO) {

                        String destinationProperty = context.getMapping().getLastDestinationProperty().getName();
                        return !("id".equals(destinationProperty) ||
                                "createdAt".equals(destinationProperty) ||
                                "updatedAt".equals(destinationProperty) ||
                                "deletedAt".equals(destinationProperty));
                    }
                    return true;
                })
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true);
    }

    private void configureProductMappings(ModelMapper modelMapper) {
        Converter<byte[], String> toBase64 = ctx ->
                ctx.getSource() != null ? Base64.getEncoder().encodeToString(ctx.getSource()) : null;

        // FirstProduct mappings
        modelMapper.createTypeMap(FirstProduct.class, FirstProductDTO.class)
                .addMappings(mapper -> {
                    mapper.using(toBase64).map(FirstProduct::getImage, FirstProductDTO::setImage);
                    mapper.skip(FirstProductDTO::setMultipartFile);
                });

        modelMapper.createTypeMap(FirstProductDTO.class, FirstProduct.class)
                .addMappings(mapper -> mapper.skip(FirstProduct::setImage));

        // FinalProduct mappings
        modelMapper.createTypeMap(FinalProduct.class, FinalProductDTO.class)
                .addMappings(mapper -> {
                    mapper.using(toBase64).map(FinalProduct::getImage, FinalProductDTO::setImage);
                    mapper.skip(FinalProductDTO::setMultipartFile);
                });

        modelMapper.createTypeMap(FinalProductDTO.class, FinalProduct.class)
                .addMappings(mapper -> mapper.skip(FinalProduct::setImage));

        // MidProduct mappings
        modelMapper.createTypeMap(MidProduct.class, MidProductDTO.class)
                .addMappings(mapper -> {
                    mapper.using(toBase64).map(MidProduct::getImage, MidProductDTO::setImage);
                    mapper.skip(MidProductDTO::setMultipartFile);
                });

        modelMapper.createTypeMap(MidProductDTO.class, MidProduct.class)
                .addMappings(mapper -> mapper.skip(MidProduct::setImage));
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    @Bean
    public Validator validator() {
        Validator validator;

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        return validator;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
