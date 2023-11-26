package org.example;

import Entidades.Tecnico;
import Managers.RRHHManager;

import java.util.Scanner;
import java.util.stream.Collectors;

public class MesaEntrada {

    private static Scanner leer = new Scanner(System.in);
    public static void main(String[] args) {

        menuPrincipal();

}

public static void menuPrincipal(){
    int opcion;
    do{
        System.out.println("Menu Principal");
        System.out.println("1 - Mesa de Ayuda");
        System.out.println("2 - Area Comercial");
        System.out.println("3 - Recursos Humanos");
        System.out.println("4 - Salir");


        opcion = leer.nextInt();

        switch (opcion) {
            case 1: mesaDeAyuda(); break;
            case 2: areaComercial();break;
            case 3: recursosHumanos(); break;
            case 4: clearScreen(); break;
            default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
        }
    } while (opcion < 1 || opcion > 4);


}

        public static void areaComercial(){
        int opcion;
        do{
          System.out.println("------------------------");
          System.out.println("1 - ");
          System.out.println("2 - ");
          System.out.println("3 - ");
            System.out.println("4 - Menu Principal");
          System.out.println("5 - Salir");

            opcion = leer.nextInt();

            switch (opcion) {
                case 1: ; break;
                case 2: ; break;
                case 3: ; break;
                case 4: menuPrincipal(); break;
                case 5: clearScreen(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);

        }

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
                case 4: menuPrincipal(); ; break;
                case 5: clearScreen(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion < 1 || opcion > 5);
        }

        public static void recursosHumanos(){
            int opcion;
            System.out.println("------------------------");
            System.out.println("1 - Alta de Tecnico");
            System.out.println("2 - Actualizar Tenico");
            System.out.println("3 - Lista de Tecnicos");
            System.out.println("4 - Eliminar Tecnico");
            System.out.println("5 - Menu Principal");
            System.out.println("6 - Salir");

            opcion = leer.nextInt();
            Tecnico tecnico ;

            switch (opcion) {

                case 1:
                    tecnico = new Tecnico();
                    System.out.println("Ingrese nombre");
                    tecnico.setNombre(leer.next());
                    System.out.println("Ingrese apellido");
                    tecnico.setApellido(leer.next());
                    System.out.println("Ingrese DNI");
                    tecnico.setDni(leer.nextInt());
                    tecnico.setEstado(true);
                    RRHHManager.cargarTecnico(tecnico); break;
                case 2:
                    tecnico = new Tecnico();
                    System.out.println("Ingrese DNI del Tecnico");
                    int dni = leer.nextInt();
                    for (Tecnico tec : RRHHManager.obtenerTodosLosTecnicos()){
                        if (tec.getDni() == dni){
                            tecnico = tec;
                            System.out.println(tec);
                        }

                    }

                   // RRHHManager.actualizarDatosTecnico(tecnico);
                    ;break;
                case 3: RRHHManager.obtenerTodosLosTecnicos().forEach(System.out::println); break;
                case 4: ; break;
                case 5: menuPrincipal(); break;
                case 6: clearScreen(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }while (opcion < 1 || opcion > 6);

        }


    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }

