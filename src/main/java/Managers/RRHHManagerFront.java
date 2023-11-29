package Managers;

import Entidades.Tecnico;
import org.example.MenuPrincipal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RRHHManagerFront {
    private static Tecnico tecnico = null;
    private static Scanner leer = new Scanner(System.in);

    public static void recursosHumanos() {
        int opcion;
        try{
        do {
            System.out.println();
            System.out.println("------- RRHH ------");
            System.out.println("1 - Nuevo Técnico");
            System.out.println("2 - Técnico asociado");
            System.out.println("3 - Menu Principal");
            System.out.println("4 - Salir");
            System.out.print("Opción: ");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    Tecnico nuevoTecnico = RRHHManagerBack.cargarTecnico();
                    RRHHManagerBack.armarServicioTecnico(nuevoTecnico);
                    break;
                case 2:
                    tecnico = RRHHManagerBack.buscarTecnicoParametros();
                    tecnicoAsociado(tecnico);
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
        }while (MetodosControl.otro("RRHH: ¿Desea realizar otra accion?"));
        } catch (InputMismatchException errorFormato) {
            System.out.println("Formato incorrecto. Utilice números");
            leer.next();
        }
        leer.nextLine();
    }

    public static void tecnicoAsociado(Tecnico tecnico) {
        int opcion = 0;
        try{
        do {
            System.out.println();
            System.out.println("------- RRHH ------");
            System.out.println("1 - Actualizar Datos Personales Tecnico");
            System.out.println("2 - Baja Tecnico");
            System.out.println("3 - Alta Expertise Tecnico");
            System.out.println("4 - Baja Expertise Tecnico");
            System.out.println("5 - Lista de Tecnicos por Expertise");
            System.out.println("6 - Eliminar Tecnico");
            System.out.print("Opción: ");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    RRHHManagerBack.actualizarDatosPersonalesTecnico(tecnico);
                    break;
                case 2:
                    RRHHManagerBack.bajaTecnico(tecnico);
                    break;
                case 3:
                    RRHHManagerBack.armarServicioTecnico(tecnico);
                    break;
                case 4:
                    //RRHHManagerBack;
                    break;
                case 5:

                    break;
                case 6:
                    RRHHManagerBack.eliminarTecnico(tecnico);
                    break;
                default:  System.out.println("Por favor, elija valores entre el 1 y el 6");

            }
        } while (MetodosControl.otro("¿Realizar otra accion?"));
        } catch (InputMismatchException errorFormato) {
            System.out.println("Formato incorrecto. Utilice números");
            leer.next();
        }
        leer.next();
    }
}