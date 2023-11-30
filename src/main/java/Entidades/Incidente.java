package Entidades;
import lombok.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idIncidente;
    @Column
    private String descripcion;
    @OneToOne
    @Nullable
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "idServicioCliente")
    private ServicioCliente servicioCliente;
    @ManyToOne
    @Nullable
    @JoinColumn(name = "idTecnico")
    private Tecnico tecnico;
    @Column
    private LocalDateTime fechaIngreso;
    @Column
    @Nullable
    private LocalDateTime fechaFin;
    @Column
    @Nullable
    private int tiempoResolucion;
    @Column
    private String tipoComunicacion;
    @Column
    private boolean estado;
}
