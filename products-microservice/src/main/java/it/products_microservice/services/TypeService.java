package it.products_microservice.services;

import it.products_microservice.model.Type;

import java.util.List;
import java.util.Optional;

public interface TypeService {

    List<Type> findAll();
    Optional<Type> findById(Long id);
    Type save(Type type);
    void deleteById(Long id);
}
