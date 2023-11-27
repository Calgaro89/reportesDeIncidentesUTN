package Entidades;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class ServicioCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idServicioCliente;
    @OneToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "idSoftware")
    private Software software;
    @Basic
    private boolean estado;
}
