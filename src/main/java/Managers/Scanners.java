package Managers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class Scanners {

    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    public static String obtenerStringSinFormato(String titulo){
        System.out.print(titulo + ": ");
        String textoIngresado = leer.next();
        leer.nextLine();
        return textoIngresado;
    }

    public static int obtenerNumeroInt(String tituloLineaIngreso) {
        boolean numeroCorrecto = false;
        int numero = 0;
        do {
            try {
                System.out.println(tituloLineaIngreso + ": ");
                numero = leer.nextInt();
                leer.nextLine();
                numeroCorrecto = true;
            } catch (InputMismatchException error) {
                System.out.println("Formato " + tituloLineaIngreso + " incorrecto: ");
                leer.nextLine();
            }
        } while (!numeroCorrecto);
        return numero;
    }

    public static long obtenerNumeroLong(String tituloLineaIngreso) {
        boolean numeroCorrecto = false;
        long numero = 0;
        do {
            try {
                System.out.println(tituloLineaIngreso + ": ");
                numero = leer.nextInt();
                leer.nextLine();
                numeroCorrecto = true;
            } catch (InputMismatchException error) {
                System.out.println("Formato " + tituloLineaIngreso + " incorrecto: ");
                leer.nextLine();
            }
        } while (!numeroCorrecto);
        return numero;
    }

    public static LocalDate obtenerLocalDate(String tituloLineaIngreso) {
        boolean formatoCorrecto = false;
        LocalDate localDate = null;

        do {
            try {
                System.out.println(tituloLineaIngreso + " (Formato: yyyy-MM-dd): ");
                String input = leer.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                localDate = LocalDate.parse(input, formatter);
                formatoCorrecto = true;
            } catch (DateTimeParseException error) {
                System.out.println("Formato " + tituloLineaIngreso + " incorrecto. Inténtalo de nuevo.");
            }
        } while (!formatoCorrecto);

        return localDate;
    }

}
