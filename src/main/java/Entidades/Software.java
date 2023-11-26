package Entidades;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
}
