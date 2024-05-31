package it.products_microservice.services.impl;

import it.products_microservice.model.Type;
import it.products_microservice.repositories.TypeRepository;
import it.products_microservice.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Optional<Type> findById(Long id) {
        return typeRepository.findById(id);
    }

    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public void deleteById(Long id) {
        typeRepository.deleteById(id);
    }
}
