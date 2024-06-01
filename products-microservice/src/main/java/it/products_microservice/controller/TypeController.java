package it.products_microservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.products_microservice.dto.TypeDTO;
import it.products_microservice.model.Type;
import it.products_microservice.services.TypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos")
@Tag(name = "Type Resource")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Obtener todos los tipos")
    @GetMapping
    public List<TypeDTO> getAllTipos() {
        return typeService.findAll().stream()
                .map(type -> modelMapper.map(type, TypeDTO.class))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener tipo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getTipoById(
            @PathVariable Long id) {
        return typeService.findById(id)
                .map(type -> ResponseEntity.ok(modelMapper.map(type, TypeDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo tipo")
    @PostMapping
    public TypeDTO createType(@RequestBody TypeDTO typeDTO) {
        Type type = modelMapper.map(typeDTO, Type.class);
        Type createdType = typeService.save(type);
        return modelMapper.map(createdType, TypeDTO.class);
    }

    @Operation(summary = "Actualizar tipo existente")
    @PutMapping("/{id}")
    public ResponseEntity<TypeDTO> updateType(
            @PathVariable Long id,
            @RequestBody TypeDTO typeDTO) {
        return typeService.findById(id)
                .map(type -> {
                    type.setName(typeDTO.getName());
                    Type updatedType = typeService.save(type);
                    return ResponseEntity.ok(modelMapper.map(updatedType, TypeDTO.class));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un tipo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteType(
            @PathVariable Long id) {
        return typeService.findById(id)
                .map(type -> {
                    typeService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
