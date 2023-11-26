package org.example;
import Managers.ClienteManager;
import Managers.RRHHManager;
import Entidades.Cliente;
import Entidades.Tecnico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class App
{
    public static void main( String[] args ) {
       /*
       Cliente c1 = new Cliente
       c1.setNombre("Mateo");
       c1.setCelular(342468795);
       c1.setEstado(true);
       c1.setMail("mym@gmail.com");
       c1.setCuit("19-53886952-0");

       Managers.ClienteManager.cargarCliente(c1);


       List<Cliente> clientes = ClienteManager.obtenerTodosLosClientes();
       Cliente cliente = clientes.get(0);
       cliente.setNombre("Carmiel");
       ClienteManager.actualizarDatosCliente(cliente);

       ClienteManager.eliminarCliente(4);
       */
        Cliente cliente = ClienteManager.obtenerTodosLosClientes().get(0);
        ClienteManager.bajaCliente(cliente);
    }
}
