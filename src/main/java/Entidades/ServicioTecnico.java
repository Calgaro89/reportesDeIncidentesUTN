package Entidades;

import lombok.*;
import javax.persistence.*;


@Data
@Entity
public class ServicioTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idServicioTecnico;
    @OneToOne
    @JoinColumn(name = "idTecnico")
    private Tecnico tecnico;
    @OneToOne
    @JoinColumn(name = "idSoftware")
    private Software software;
    private boolean estado;


}
