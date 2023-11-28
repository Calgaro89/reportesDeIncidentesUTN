package Managers;

import Entidades.Cliente;
import Entidades.ServicioCliente;
import org.example.MenuPrincipal;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class MesaDeAyuda {
    private static List<ServicioCliente> servicioCliente;
    private static Scanner leer = new Scanner(System.in);

    public static void ingresoMesaDeAyuda() {
        try {
            boolean salir = false;
            while (!salir) {
                System.out.println("-------MESA DE AYUDA--------");
                System.out.println("1. Ingresar");
                System.out.println("2. Nuevo Cliente");
                System.out.println("3. Volver al menu");
                System.out.println("4. Salir");
                System.out.print("Selecciona una opción: ");
                int opcion = leer.nextInt();
                leer.nextLine();
                switch (opcion) {
                    case 1:
                        ingresoCuentaConCuit();
                        break;
                    case 2:
                        System.out.println("Seleccionaste: Contratar servicios");
                        break;
                    case 3:
                        MenuPrincipal.menuPrincipal();
                        break;
                    case 4:
                        System.out.println("¡Hasta luego!");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige una opción válida.");
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Opción no válida. Por favor, elige una opción válida.");
            ingresoMesaDeAyuda();
        }
    }

    public static void ingresoCuentaConCuit() {
        Cliente cliente;
        boolean salir = false;
        do {
            System.out.println("Ingrese su cuit completo (xx-xxxxxxxx-x)");
            String cuit = leer.next();
            leer.nextLine();
            if (MetodosControl.validarFormatoCUIT(cuit)) {
                cliente = AreaComercialBack.buscarClienteCUIT(cuit);
                if (cliente == null) {
                    System.out.println("Cliente inexistente");
                } else {
                    salir = true;
                    mesaDeAyuda(cliente);
                }
            } else {
                System.out.println("Formato no válido. Asegúrate de seguir el formato xx-xxxxxxxx-x");
            }
        } while (!salir);
        leer.nextLine();
    }


    public static void mesaDeAyuda(Cliente cliente) {
         servicioCliente = AreaComercialBack.obtenerServiciosClientes(cliente);
         try {
             int opcion;
             do {
                 System.out.println("-------MESA DE AYUDA--------");
                 System.out.println("Bienvenido " + cliente.getNombre());
                 System.out.println();
                 System.out.println("1 - Consultar Suscripciones");
                 System.out.println("2 - Reportar Problemas");
                 System.out.println("3 - Consultar Reportes");
                 System.out.println("4 - Menu Principal");
                 System.out.println("5 - Salir");
                 System.out.print("Opción: ");
                 opcion = leer.nextInt(); leer.nextLine();

                 switch (opcion) {
                     case 1:
                         consultarSuscripciones(cliente);
                         break;
                     case 2:
                         reportarProblemas(cliente);
                         break;
                     case 3:
                         consultarReportes(cliente);
                         break;
                     case 4:
                         MenuPrincipal.menuPrincipal();
                         break;
                     case 5:
                         System.exit(0);
                         break;
                     default:
                         System.out.println("Opción no válida. Por favor, elija una opción válida.");
                 }
             } while (opcion < 1 || opcion > 5);
         }catch(InputMismatchException e){
             System.out.println("Opción no válida. Por favor, elija una opción válida.");
             mesaDeAyuda(cliente);
         }
        leer.nextLine();
    }

    public static void consultarSuscripciones(Cliente cliente) {
        if (servicioCliente.isEmpty()) {
            System.out.println("No posee servicios contratados.");
            if (MetodosControl.otro("Mesa de Ayuda: ¿Desea realizar otra accion?")){
                mesaDeAyuda(cliente);
            }else {System.exit(0);}
        } else {
            System.out.println("Servicios Contratados: ");
            int indice = 0;
            for (ServicioCliente servicioCliente : servicioCliente) {
                indice++;
                System.out.println(indice + ". " + servicioCliente.getSoftware().getNombre());
            }
        }
    }

    public static void reportarProblemas(Cliente cliente) {
        if (servicioCliente.isEmpty()) {
            System.out.println("No posee servicios contratados.");
            if (MetodosControl.otro("Mesa de Ayuda: ¿Desea realizar otra accion?")){
                mesaDeAyuda(cliente);
            }else {System.exit(0);}
        } else {
            System.out.println("Seleccione el número del servicio que presenta el conflicto");
            System.out.print("Servicio: ");
            int indice = leer.nextInt(); leer.nextLine();
            System.out.println("El conflicto esta en: " + servicioCliente.get(indice));
        }
    }
    public static void consultarReportes(Cliente cliente){

    }

}
