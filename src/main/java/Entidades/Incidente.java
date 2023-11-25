package Entidades;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
//@Builder
@NoArgsConstructor
@Entity

public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idIncidente;
    @ManyToOne
    private Servicios servicio;
    private String descripcion;
    @ManyToOne @JoinColumn(name = "idCliente")
    private Cliente cliente;
    @ManyToOne @JoinColumn(name = "idTecnico")
    private Tecnico tecnio;
    private LocalDateTime fecha_ingreso;
    private LocalDateTime fecha_solucion;
    private int tiempo_solucion;
    private String tipo_comunicacion;
    private boolean estado;

}
