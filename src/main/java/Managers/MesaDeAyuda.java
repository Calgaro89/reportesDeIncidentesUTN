package Managers;

import Entidades.Cliente;
import Entidades.ServicioCliente;
import Entidades.Software;
import org.example.MenuPrincipal;


import java.util.List;
import java.util.Scanner;


public class MesaDeAyuda {
    private static List<ServicioCliente> servicioCliente;
    private static Scanner leer = new Scanner(System.in);

    public static void ingresoMesaDeAyuda() {
        boolean salir = false;
        while (!salir) {
            System.out.println("-------MESA DE AYUDA--------");
            System.out.println("1. Ingresar");
            System.out.println("2. Nuevo Cliente");
            System.out.println("3. Volver al menu");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = leer.nextInt();

            switch (opcion) {
                case 1:ingresoCuentaConCuit();break;
                case 2:System.out.println("Seleccionaste: Contratar servicios");break;
                case 3:MenuPrincipal.menuPrincipal();break;
                case 4:System.out.println("¡Hasta luego!");salir = true; break;
                default: System.out.println("Opción no válida. Por favor, elige una opción válida.");
            }
        }
    }

    public static void ingresoCuentaConCuit() {
        Cliente cliente;
        boolean salir = false;
        do {
            System.out.println("Ingrese su cuit completo (xx-xxxxxxxx-x)");
            String cuit = leer.next();
            if (validarFormatoCUIT(cuit)) {
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
    }

    public static boolean validarFormatoCUIT(String cuit) {
    return cuit.matches("\\d{2}-\\d{8}-\\d");
    }

    public static void mesaDeAyuda(Cliente cliente) {
         servicioCliente = AreaComercialBack.obtenerServiciosClientes(cliente);
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
            opcion = leer.nextInt();

            switch (opcion) {
                case 1: consultarSuscripciones(cliente); break;
                case 2: reportarProblemas(cliente);break;
                case 3: consultarReportes(cliente);break;
                case 4: MenuPrincipal.menuPrincipal();break;
                case 5: break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);
    }

    public static void consultarSuscripciones(Cliente cliente) {
        if (servicioCliente.isEmpty()) {
            System.out.println("No posee servicios contratados.");
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
        } else {
            System.out.println("Seleccione el número del servicio que presenta el conflicto");
            System.out.print("Servicio: ");
            int indice = leer.nextInt();
            System.out.println("El conflicto esta en: " + servicioCliente.get(indice));
        }
    }
    public static void consultarReportes(Cliente cliente){
    }

}
