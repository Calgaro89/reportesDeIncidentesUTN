package Entidades;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity
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
