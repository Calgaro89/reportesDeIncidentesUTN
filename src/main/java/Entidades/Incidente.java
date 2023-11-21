package Entidades;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "incidentes")
public class Incidente {
    @Id
    @GeneratedValue
    private int idIncidente;
    private String problema;
    private String descripcion;
    @OneToOne @JoinColumn(name = "idCliente")
    private Cliente cliente;
    @ManyToOne @JoinColumn(name = "idTecnico")
    private Tecnico tecnio;
    private LocalDateTime fecha_ingreso;
    private LocalDateTime fecha_solucion;
    private int tiempo_solucion;
    private String tipo_comunicacion;
    private boolean estado;
}
