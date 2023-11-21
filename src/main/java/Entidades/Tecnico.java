package Entidades;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;

@Data
@Builder
@Entity
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
