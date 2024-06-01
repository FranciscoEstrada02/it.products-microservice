package it.products_microservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "types")
    private Set<Product> products;

    @CreationTimestamp
    @Column(name = "creatrionDate", updatable = false)
    private LocalDateTime creatrionDate;

    @UpdateTimestamp
    @Column(name = "last_editionDate")
    private LocalDateTime last_editionDate;
}
