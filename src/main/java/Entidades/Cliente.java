package Entidades;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue
    private int idCliente;
    private String cuit;
    private String nombre;
    private long celular;
    private String mail;
    private boolean estado;
}
