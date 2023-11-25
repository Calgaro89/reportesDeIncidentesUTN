package Entidades;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Problema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descripcion;

    @ManyToOne
    private Incidente incidente;
}

