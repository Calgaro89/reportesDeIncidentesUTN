package Managers;

import java.util.InputMismatchException;

public class MetodosControl {
    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".com");
    }

    public static boolean validarFormatoCUIT(String cuit) {
        if (cuit.matches("\\d{2}-\\d{8}-\\d")){
            return true;
        }
        System.out.println("Cuit incorrecto, vuelva a ingresarlo en formato (xx-xxxxxxxx-x):");
        return false;
    }

    public static boolean otro(String text) {
        int opcion = 0;
        do {
            boolean comprobacion = true;
            System.out.println(text);
            System.out.println("1. Si");
            System.out.println("2. No");
            System.out.print("Opción: ");
            try {
                opcion = leer.nextInt();
            } catch (InputMismatchException error) {
                System.out.println("Opcion incorrecta, vuelva a intentarlo: ");
                comprobacion = false;
                leer.nextLine();
            }

            if ((opcion < 1 || opcion > 2) && comprobacion){System.out.println("Opcion incorrecta, vuelva a intentarlo: ");}

            if (opcion==2){System.exit(0);}

        } while (opcion != 1);
        return (opcion == 1);
    }

    public static int leerOpcionIndices(int indiceMaximo) {
        int indice = 0;
        while (true) {
            try {
                System.out.print("Indice (1-" + indiceMaximo + "): ");
                indice = leer.nextInt();leer.nextLine();
                if (indice >= 1 && indice <= indiceMaximo) {
                    break;
                } else {
                    System.out.println("Indice fuera de rango. Inténtelo de nuevo.");
                }
            } catch (InputMismatchException errorFormato) {
                System.out.println("Opción no válida. Por favor, elija una opción válida.");
                leer.nextLine();
            }
        }
        return indice;
    }

}
