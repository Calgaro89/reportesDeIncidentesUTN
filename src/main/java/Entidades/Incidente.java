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
    @Column(nullable = true)
    @JoinColumn(name = "idTecnico")
    private Tecnico tecnico;

    @Column
    private LocalDateTime fechaIngreso;

    @Column(nullable = true)
    private LocalDateTime fechaEstimadaFin;

    @Column
    private LocalDateTime fechaRealFin;
    @Column
    private String tipoComunicacion;

    @Column
    private boolean estado;
}
