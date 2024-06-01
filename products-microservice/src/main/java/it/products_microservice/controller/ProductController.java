package it.products_microservice.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.products_microservice.dto.ProductDTO;
import it.products_microservice.model.Product;
import it.products_microservice.model.Type;
import it.products_microservice.services.ProductService;
import it.products_microservice.services.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Resource")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private ModelMapper modelMapper;


    @Operation(summary = "Obtener todos los productos")
    @GetMapping
    public List<ProductDTO> getAllProducts(){
        return  productService.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProductDTO createProducto(@RequestBody ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);

        Set<Type> types = productDTO.getTypes().stream()
                .map(typeDTO -> {
                    if (typeDTO.getId() != null) {
                        return typeService.findById(typeDTO.getId()).orElseGet(() -> {
                            Type newType = modelMapper.map(typeDTO, Type.class);
                            return typeService.save(newType);
                        });
                    } else {
                        Type newType = modelMapper.map(typeDTO, Type.class);
                        return typeService.save(newType);
                    }
                })
                .collect(Collectors.toSet());
        product.setTypes(types);

        Product createdProduct = productService.save(product);
        return modelMapper.map(createdProduct, ProductDTO.class);
    }

    @Operation(summary = "Obtener producto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(modelMapper.map(product, ProductDTO.class)))
                .orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Actualizar producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProducto(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.findById(id)
                .map(product -> {
                    product.setName(productDTO.getName());
                    product.setPrice(productDTO.getPrice());

                    // Guardar tipos existentes o crear y guardar nuevos tipos
                    Set<Type> types = productDTO.getTypes().stream()
                            .map(typeDTO -> {
                                if (typeDTO.getId() != null) {
                                    return typeService.findById(typeDTO.getId()).orElseGet(() -> {
                                        Type newType = modelMapper.map(typeDTO, Type.class);
                                        return typeService.save(newType);
                                    });
                                } else {
                                    Type newType = modelMapper.map(typeDTO, Type.class);
                                    return typeService.save(newType);
                                }
                            })
                            .collect(Collectors.toSet());
                    product.setTypes(types);

                    Product updatedProduct = productService.save(product);
                    return ResponseEntity.ok(modelMapper.map(updatedProduct, ProductDTO.class));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un producto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> {
                    productService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }



}
