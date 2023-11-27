package Managers;

import Entidades.Cliente;
import org.example.MenuPrincipal;

import java.util.Scanner;

public class AreaComercialFront {
    private static Scanner leer = new Scanner(System.in);
    public static void areaComercial(){
        int opcion;
        do{
            System.out.println("-------AREA COMERCIAL--------");
            System.out.println("1 - Alta de Cliente");
            System.out.println("2 - Actualizar Cliente");
            System.out.println("3 - Eliminar Cliente");
            System.out.println("4 - Menu Principal");
            System.out.println("5 - Salir");
            System.out.print("Opción: ");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1: cargarNuevoCliente(); break;
                case 2: actualizarCliente(); break;
                case 3: ; break;
                case 4: MenuPrincipal.menuPrincipal(); break;
                case 5: Managers.LimpiarPantalla.limpiarPantalla(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);
    }

    public static void cargarNuevoCliente(){
        Cliente cliente = Scanners.crearCliente();
        AreaComercialBack.cargarCliente(cliente);
    }

    public static void actualizarCliente(){
        Scanners.buscarClientesParametros();

    }
}
