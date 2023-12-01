package Managers;

import Entidades.Cliente;

public class AreaComercialFront {

    public static int mostrarTablaAreaComercial() {
        System.out.println();
        System.out.println("AREA COMERCIAL:");
        System.out.println("1 - Nuevo Cliente");
        System.out.println("2 - Clientes Asociados");
        System.out.println("3 - Menu Principal");
        System.out.println("4 - Salir");
        return 4;
    }


    public static int mostrarTablaBuscarClienteAsociado() {
            System.out.println("AREA COMERCIAL: Buscar cliente por: ");
            System.out.println("1 - Nombre");
            System.out.println("2 - CUIT");
            System.out.println("3 - iD");
            System.out.println("4 - E-Mial");
            System.out.println("5 - Celular");
            System.out.println("6 - Volver al menu");
            return 6;
    }

    public static int mostrarTablaClientesAsociado(Cliente cliente) {
        System.out.println("AREA COMERCIAL: Cliente Asociado " + cliente.getNombre());
        System.out.println("1 - Actualizar Datos Cliente");
        System.out.println("2 - Baja Cliente");
        System.out.println("3 - Eliminar Cliente");
        System.out.println("4 - Consulta Cliente");
        System.out.println("5 - Nuevo Servicio");
        System.out.println("6 - Baja Servicio");
        System.out.println("7 - Volver");
        return 7;
    }

    public static int mostrarMenuModificarCliente(Cliente cliente) {
        System.out.println();
        System.out.println("AREA COMERCIAL: Modificar el cliente - " + cliente.getNombre());
        System.out.println("1 - Nombre");
        System.out.println("2 - CUIT");
        System.out.println("3 - e-mail");
        System.out.println("4 - Celular");
        System.out.println("5 - Volver al menu");
        return 5;
    }

}