package Entidades;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;

@Data
@Builder
@Entity
@Table(name="tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue
    private int idTecnico;
    private String nombre;
    private String apellido;
    private int dni;
    @OneToMany (mappedBy = "software")
    private HashSet conocimientos;
    private boolean estado;
}
