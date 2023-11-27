package Managers;

import Entidades.Cliente;
import org.example.MenuPrincipal;

import java.util.Scanner;

public class AreaComercialFront {
    private static Scanner leer = new Scanner(System.in);
    public static void areaComercial(){
        int opcion;
        do{
            System.out.println();
            System.out.println("-------AREA COMERCIAL--------");
            System.out.println("1 - Alta de Cliente");
            System.out.println("2 - Actualizar Cliente");
            System.out.println("3 - Baja Cliente");
            System.out.println("4 - Eliminar Cliente");
            System.out.println("5 - Menu Principal");
            System.out.println("6 - Salir");
            System.out.print("Opción: ");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1: cargarNuevoCliente(); break;
                case 2: actualizarCliente(); break;
                case 3: bajaCliente(); break;
                case 4: eliminarCliente();break;
                case 5: MenuPrincipal.menuPrincipal(); break;
                case 6: ; break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);
    }

    public static void cargarNuevoCliente(){
        Cliente cliente = Scanners.crearCliente();
        AreaComercialBack.cargarCliente(cliente);
        areaComercial();
    }

    public static void actualizarCliente(){
        Cliente cliente = Scanners.buscarClientesParametros();
        Scanners.modificarDatos(cliente);
        areaComercial();
    }

    public static void bajaCliente(){
       Cliente cliente = Scanners.buscarClientesParametros();
       AreaComercialBack.bajaCliente(cliente);
       areaComercial();
    }

    public static void eliminarCliente(){
        Cliente cliente = Scanners.buscarClientesParametros();
        AreaComercialBack.eliminarCliente(cliente);
        areaComercial();
    }
}
