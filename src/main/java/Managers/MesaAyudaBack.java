package Managers;

import Entidades.Cliente;
import Entidades.ServicioCliente;
import Entidades.Software;
import org.example.MenuPrincipal;

import java.util.List;
import java.util.Scanner;


public class MesaAyudaBack {
    static List<Software> listaSoftwaresClientes;
    static Cliente cliente;

    // ------------- INGRESO MESA AYUDA -----------------------------------------------

    public static void ingresoMesaAyuda(){
        int opcion, maximoOvolver;
        do{
            maximoOvolver = MesaAyudaFront.mostrarOpcionesMesaDeAyuda();
            opcion = GeneralBack.leerOpcionIndices(maximoOvolver);
            opcionesIngresoMesaDeAyuda(opcion);
        } while (opcion != maximoOvolver-1);
    }
    public static void opcionesIngresoMesaDeAyuda(int opcion) {
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
                        break;
                }
            }

    // ------------- INGRESO CUENTA CON CUIT --------------------------------------------
    public static void ingresoCuentaConCuit() {
        boolean salir = false;
        do {
            cliente = AreaComercialBack.buscarClienteCUIT(GeneralBack.obtenerCuit());
                if (cliente == null) {
                    System.out.println("Cliente inexistente");
                } else {
                    salir = true;
                    ingresoMesaAyudaConCUITAprobado();
                }
        } while (!salir);
    }

    // ------------- INGRESO MESA AYUDA CUENTA CON CUIT OK ----------------------------------

    public static void ingresoMesaAyudaConCUITAprobado(){
        int opcion, maximoOvolver;
        do{
            maximoOvolver = MesaAyudaFront.mostrarOpciones(cliente);
            opcion = GeneralBack.leerOpcionIndices(maximoOvolver);
            mesaDeAyudaCuentaAsociado(opcion);
        } while (opcion != maximoOvolver-1);
    }
    public static void mesaDeAyudaCuentaAsociado(int opcion) {
        switch (opcion) {
            case 1:
                consultarSuscripciones(cliente);
                break;
            case 2:
                iniciarReporteProblemas();
                break;
            case 3:
                // consultarReportes(cliente);
                break;
            case 4:
                MenuPrincipal.menuPrincipal();
                break;
            case 5:
                System.exit(0);
                break;
        }
    }
    // ------------- LISTAR Y MOSTRAR SOFTWARES DE UN CLIENTE -------------------------------------
    public static int consultarSuscripciones(Cliente cliente) {
        int indice = 0;
         listaSoftwaresClientes = AreaComercialBack.listarSoftwareDeCliente(cliente);
        if (listaSoftwaresClientes.isEmpty()) {
            System.out.println("No posee servicios contratados.");
        } else {
            indice = mostarSoftwaresContratadosCliente(listaSoftwaresClientes);
        } return indice;
    }

    public static int mostarSoftwaresContratadosCliente(List<Software> listaSoftwaresClientes) {
        System.out.println("Servicios Contratados: ");
        int indice = 0;
        for (Software softwares : listaSoftwaresClientes) {
            indice++;
            System.out.println(indice + ". " + softwares);
        }
        return indice;
    }
    // ------------- REPORTAR UN PROBLEMA -------------------------------------
    public static void iniciarReporteProblemas() {
        int indiceMaximo = consultarSuscripciones(cliente);
        int opcion = GeneralBack.leerOpcionIndices(indiceMaximo);
        Software software = listaSoftwaresClientes.get(opcion);
        armarServicioClienteReporteProblema(software);
    }
    public static void armarServicioClienteReporteProblema(Software software){
        ServicioCliente servicioCliente = AreaComercialBack.obtenerServiciosClientes(cliente).stream()
                .filter(servicio -> servicio.getSoftware().getNombre().equals(software))
                .findFirst()
                .orElse(null);
    }

    // ------------- CREAR UN INCIDENTE -------------------------------------
}
