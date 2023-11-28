package Managers;

import Entidades.Cliente;
import org.example.MenuPrincipal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AreaComercialFront {
    private static Scanner leer = new Scanner(System.in);
    private static Cliente cliente;
    public static void areaComercial(){
        try {
            int opcion;
            do {
                System.out.println();
                System.out.println("------- AREA COMERCIAL --------");
                System.out.println("1 - Nuevo Cliente");
                System.out.println("2 - Clientes");
                System.out.println("3 - Menu Principal");
                System.out.println("4 - Salir");
                System.out.print("Opción: ");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 1:
                        AreaComercialBack.cargarCliente();
                        areaComercial();
                        break;
                    case 2:
                        clientes();
                        break;
                    case 3:
                        MenuPrincipal.menuPrincipal();
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elija una opción válida.");
                }
            } while (opcion < 1 || opcion > 4);
        }catch (InputMismatchException e){
            System.out.println("Opción no válida. Por favor, elija una opción válida.");
            leer.nextLine();
            areaComercial();

        }
    }

    public static void clientes(){

        try {
            int opcion;
            cliente = Scanners.buscarClientesParametros();
            String texto = "¿Desea hacer otra consulta?";
            do {
                System.out.println();
                System.out.println("------- AREA COMERCIAL --------");
                System.out.println("1 - Actualizar Cliente");
                System.out.println("2 - Baja Cliente");
                System.out.println("3 - Eliminar Cliente");
                System.out.println("4 - Consulta Cliente");
                System.out.println("5 - Volver");
                System.out.println("6 - Salir");
                System.out.print("Opción: ");
                opcion = leer.nextInt();

                switch (opcion) {
                    case 1:
                        AreaComercialBack.actualizarDatosCliente(cliente);
                        break;
                    case 2:
                        AreaComercialBack.bajaCliente(cliente);
                        break;
                    case 3:
                        AreaComercialBack.eliminarCliente(cliente);
                        break;
                    case 4:
                        AreaComercialBack.consultaCliente(cliente);
                        break;
                    case 5:
                        areaComercial();
                        break;
                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elija una opción válida.");
                }
            } while (Scanners.otro(texto));
        }catch (InputMismatchException e){
            System.out.println("Opción no válida. Por favor, elija una opción válida.");
            leer.nextLine();
            clientes();
        }
        leer.nextLine();
    }
}
