package Managers;

import Entidades.Cliente;

public class Scanners {
    public static java.util.Scanner leer = new java.util.Scanner(System.in);

    public static Cliente crearCliente() {
        System.out.println("-------AREA COMERCIAL--------");
        System.out.print("Nombre: ");
        String nombre = leer.next();

        System.out.println("Cuit: ");
        String cuit = leer.next();

        while (!MesaDeAyuda.validarFormatoCUIT(cuit)) {
            System.out.println("Formato de CUIT incorrecto. Asegúrate de seguir el formato xx-xxxxxxxx-x");
            System.out.print("Cuit: ");
            cuit = leer.next();
        }

        System.out.print("Celular: ");
        long celular = 0;
        try {
            celular = leer.nextLong();
        } catch (NumberFormatException error) {
            System.out.println("Celular Incorrecto");
            leer.nextLine();
        }
        System.out.print("E-Mail: ");
        String mail = leer.next();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setCuit(cuit);
        cliente.setCelular(celular);
        cliente.setMail(mail);
        return cliente;
    }

    public static void buscarClientesParametros() {
        int opcion;
        do {
            opcion = 0;
            System.out.println("-------AREA COMERCIAL--------");
            System.out.println("Buscar cliente por");
            System.out.println("1 - Nombre");
            System.out.println("2 - CUIT");
            System.out.println("3 - iD");
            System.out.println("4 - E-Mial");
            System.out.println("5 - Celular");
            System.out.println("6 - Volver al menu");
            try {
                System.out.print("Opcion: ");
                opcion = leer.nextInt();
            } catch (NumberFormatException error) {
                buscarClientesParametros();
            }
            String consulta = null;
            String parametro = null;
            long valorInt = 0;
            String valorString = null;
            Cliente cliente;

            switch (opcion) {
                case 1:
                    consulta = "SELECT t FROM Cliente t WHERE nombre = :nombre";
                    parametro = "nombre";
                    System.out.print("Nombre: ");
                    valorString = leer.next();
                    break;
                case 2:
                    consulta = "SELECT t FROM Cliente t WHERE cuit = :cuit";
                    parametro = "cuit";
                    System.out.print("CUIT: ");
                    valorString = leer.next();
                    break;
                case 3:
                    consulta = "SELECT t FROM Cliente t WHERE iD = :iD";
                    parametro = "iD";
                    System.out.print("iD: ");
                    valorInt = leer.nextInt();
                    break;
                case 4:
                    consulta = "SELECT t FROM Cliente t WHERE mail = :mail";
                    parametro = "mail";
                    System.out.print("email: ");
                    valorString = leer.next();
                    break;
                case 5:
                    consulta = "SELECT t FROM Cliente t WHERE celular = :celular";
                    parametro = "celular";
                    System.out.print("celular: ");
                    valorInt = leer.nextLong();
                    break;
                case 6:
                    AreaComercialFront.areaComercial();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
            cliente = AreaComercialBack.buscarClienteParametros(consulta, parametro, valorInt, valorString);
            System.out.println(cliente);

            System.out.println("-------AREA COMERCIAL--------");
            System.out.println("Modificar datos del cliente: " + cliente.getNombre());
            System.out.println("1. Si");
            System.out.println("2. No");
            System.out.print("Opcion: ");
            int opcion2 = 0;
            try {
                opcion2 = leer.nextInt();
            } catch (NumberFormatException error) {
                System.out.println("Dato incorrecto");
            }

            switch (opcion2) {
                case 1:
                    modificarDatos(cliente);
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        } while (opcion != 7);
    }

    public static void modificarDatos(Cliente cliente) {
            int opcion = 0;
            System.out.println("-------AREA COMERCIAL--------");
            System.out.println("Modificar: ");
            System.out.println("1 - Nombre");
            System.out.println("2 - CUIT");
            System.out.println("3 - E-Mial");
            System.out.println("4 - Celular");
            System.out.println("5 - Volver al menu");
            try {
                System.out.print("Opcion: ");
                opcion = leer.nextInt();
            } catch (NumberFormatException error) {
                modificarDatos(cliente);
            }
            switch(opcion){
                case 1:
                    System.out.print("Nuevo nombre: ");
                    cliente.setNombre(leer.next());
                    break;
                case 2:
                    System.out.print("Nuevo CUIT: ");
                    cliente.setCuit(leer.next());
                    break;
                case 3:
                    System.out.print("Nuevo e-mail: ");
                    cliente.setMail(leer.next());
                    break;
                case 4:
                    System.out.print("Nuevo celular: ");
                    try {
                        cliente.setCelular(leer.nextLong());
                    } catch (NumberFormatException error){
                        System.out.println("Formato celular incorrecto");
                        modificarDatos(cliente);
                    }
                    break;
                case 5: buscarClientesParametros(); break;
                default: System.out.println("Opción no válida. Por favor, elija una opción válida.");modificarDatos(cliente);
            }
            AreaComercialBack.actualizarDatosCliente(cliente);
    }
}