package Entidades;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    @Column(unique = true)
    private String cuit;
    @Column
    private String nombre;
    @Column
    private long celular;
    @Column
    private String mail;
    @Column
    private boolean estado;
}
