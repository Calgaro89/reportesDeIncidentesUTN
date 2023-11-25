package Entidades;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;

@Data
@NoArgsConstructor
//@Builder
@Entity

public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTecnico;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private int dni;
    @Column
    private boolean estado;
}
