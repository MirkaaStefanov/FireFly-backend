package com.example.FireFly_backend.config;

import com.example.FireFly_backend.models.dto.FinalProductDTO;
import com.example.FireFly_backend.models.dto.FirstProductDTO;
import com.example.FireFly_backend.models.dto.MidProductDTO;
import com.example.FireFly_backend.models.entity.FinalProduct;
import com.example.FireFly_backend.models.entity.FirstProduct;
import com.example.FireFly_backend.models.entity.MidProduct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class ModelMapperConfig {

    @Bean("productModelMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);

        // Safe converter for byte[] to Base64 String
        Converter<byte[], String> toBase64 = ctx ->
                ctx.getSource() != null ? Base64.getEncoder().encodeToString(ctx.getSource()) : null;

        // FirstProduct mappings
        modelMapper.typeMap(FirstProduct.class, FirstProductDTO.class)
                .addMappings(mapper -> {
                    mapper.using(toBase64).map(FirstProduct::getImage, FirstProductDTO::setImage);
                    mapper.skip(FirstProductDTO::setMultipartFile);
                });

        modelMapper.typeMap(FirstProductDTO.class, FirstProduct.class)
                .addMappings(mapper -> {
                    mapper.skip(FirstProduct::setImage);
                });

        // Similar pattern for other product types
        configureFinalProductMappings(modelMapper, toBase64);
        configureMidProductMappings(modelMapper, toBase64);

        return modelMapper;
    }

    private void configureFinalProductMappings(ModelMapper modelMapper, Converter<byte[], String> toBase64) {
        modelMapper.typeMap(FinalProduct.class, FinalProductDTO.class)
                .addMappings(mapper -> {
                    mapper.using(toBase64).map(FinalProduct::getImage, FinalProductDTO::setImage);
                    mapper.skip(FinalProductDTO::setMultipartFile);
                });

        modelMapper.typeMap(FinalProductDTO.class, FinalProduct.class)
                .addMappings(mapper -> {
                    mapper.skip(FinalProduct::setImage);
                });
    }

    private void configureMidProductMappings(ModelMapper modelMapper, Converter<byte[], String> toBase64) {
        modelMapper.typeMap(MidProduct.class, MidProductDTO.class)
                .addMappings(mapper -> {
                    mapper.using(toBase64).map(MidProduct::getImage, MidProductDTO::setImage);
                    mapper.skip(MidProductDTO::setMultipartFile);
                });

        modelMapper.typeMap(MidProductDTO.class, MidProduct.class)
                .addMappings(mapper -> {
                    mapper.skip(MidProduct::setImage);
                });
    }
}