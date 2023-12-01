package Entidades;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;

@Entity
@Data
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTecnico;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column(unique = true)
    private int dni;
    @Column
    private boolean estado;
    @ManyToOne
    @JoinColumn(name = "idIncidente")
    private Incidente incidente;
}
