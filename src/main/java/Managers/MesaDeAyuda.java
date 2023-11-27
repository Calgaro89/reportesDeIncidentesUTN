package Managers;

import org.example.MenuPrincipal;

import java.util.Scanner;

public class MesaDeAyuda {

    private static Scanner leer = new Scanner(System.in);


        public static void mesaDeAyuda(){
        int opcion;
        do{
            System.out.println("------------------------");
            System.out.println("1 - Reportar Problemas");
            System.out.println("2 - Consultar Suscripciones");
            System.out.println("3 - Consultar Suscripciones");
            System.out.println("4 - Menu Principal");
            System.out.println("5 - Salir");

            opcion = leer.nextInt();

            switch (opcion) {
                case 1: ; break;
                case 2: ; break;
                case 3: ; break;
                case 4: MenuPrincipal.menuPrincipal(); ; break;
                case 5: MenuPrincipal.clearScreen(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);
        }





    }

