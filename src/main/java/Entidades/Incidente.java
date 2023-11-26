package Entidades;
import lombok.*;

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
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "idTecnico")
    private Tecnico tecnico;
    @Column
    private LocalDateTime fechaIngreso;
    @Column
    private LocalDateTime fechaFin;
    @Column
    private int tiempoResolucion;
    @Column
    private boolean estado;

}
