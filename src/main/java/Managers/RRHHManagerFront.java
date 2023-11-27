package Managers;

import Entidades.Tecnico;
import org.example.MenuPrincipal;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class RRHHManagerFront {

    private static Scanner leer = new Scanner(System.in);
    public static void recursosHumanos() {
        int opcion;
        do {
            System.out.println();
            System.out.println("------- RRHH ------");
            System.out.println("1 - Alta de Tecnico");
            System.out.println("2 - Actualizar Tenico");
            System.out.println("3 - Lista de Tecnicos");
            System.out.println("4 - Eliminar Tecnico");
            System.out.println("5 - Menu Principal");
            System.out.println("6 - Salir");
            System.out.print("Opción: ");
            opcion = leer.nextInt();
            Tecnico tecnico;

            switch (opcion) {

                case 1:
                    Tecnico nuevoTecnico = RRHHManagerBack.cargarTecnico();
                    RRHHManagerBack.agregarExpertiseTecnico(nuevoTecnico);
                    break;
                case 2:
                    tecnico = new Tecnico();
                    System.out.println("Ingrese DNI del Tecnico");
                    int dni = leer.nextInt();
                    for (Tecnico tec : RRHHManagerBack.obtenerTodosLosTecnicos()) {
                        if (tec.getDni() == dni) {
                            tecnico = tec;
                            System.out.println(tec);
                        }
                    }
                    // RRHHManager.actualizarDatosTecnico(tecnico);
                    ;
                    break;
                case 3:
                    ;
                    RRHHManagerBack.obtenerTodosLosTecnicos().forEach(System.out::println);
                    break;
                case 4:
                    ;
                    break;
                case 5:
                    ;
                    MenuPrincipal.menuPrincipal();
                    break;
                case 6:
                    ;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        }
        while (Scanners.otro("Desea realizar otra accion?"));
    }
}