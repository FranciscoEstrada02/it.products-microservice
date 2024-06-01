package it.products_microservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Set<TypeDTO> types;
}
