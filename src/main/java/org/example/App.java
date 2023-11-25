package org.example;

import Entidades.Cliente;
import Entidades.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class App
{
    public static void main( String[] args ) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_PU"); //Creo una unidad de persistencia con el nombre 'a', la buscará en src/resources/persistence.xml
        EntityManager entityManager = emf.createEntityManager();//Instancio la implementacion de JPA que trajimos con el ManagerFactory
        EntityTransaction entityTransaction = entityManager.getTransaction();//Equivalente a Statement en JDBC

        entityTransaction.begin();//Equivalente a createStatement()
        Cliente x = new Cliente();//Creo la entidad a persistir
        x.setNombre("Pablo");
        x.setCuit("20-9999999-20");
        x.setCelular(111111111L);
        x.setMail("xxxxx@gmail.com");
        x.setEstado(true);

        entityManager.persist(x); //Ejecuta un insert, pueden hacerse varias operaciones antes de enviarse

        entityTransaction.commit();//Ejecuta la consulta a la base de datos

        System.out.println("Hello world!");

    }
}
