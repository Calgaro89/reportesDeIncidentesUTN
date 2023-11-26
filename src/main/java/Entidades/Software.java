package Entidades;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String nombre;
    @Column
    private boolean estado;
}
