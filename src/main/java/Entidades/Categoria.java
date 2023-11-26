package Entidades;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCategoria;
    @Column
    private String tipoCategoria;
    @Column
    private int dificultad;
}
