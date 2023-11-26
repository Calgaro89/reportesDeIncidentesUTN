package Entidades;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
public class ServicioTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idServicioTecnico;
    @OneToOne
    @JoinColumn(name = "idTecnico")
    private Tecnico idTecnico;
    @OneToOne
    @JoinColumn(name = "idSoftware")
    private Software idSoftware;
}
