package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Mensaje de bienvenida
        System.out.println("Bienvenido al sistema de control de gastos e ingresos.");
        String dni;

        // Bucle para validar el DNI introducido
        while (true) {
            System.out.print("Por favor, introduzca su DNI (8 números y 1 letra): ");
            dni = scanner.nextLine();
            // Validar que el DNI sea correcto
            if (BaseDatos.validateDNI(dni)) {
                break; // Salir del bucle si el DNI es válido
            } else {
                System.out.println("El DNI introducido no es válido. Inténtelo de nuevo.");
            }
        }

        // Comprobar si el usuario está registrado en la base de datos
        if (!BaseDatos.authenticateUser(dni)) {
            System.out.println("Usuario no encontrado. Se procederá a registrarlo.");
            if (!BaseDatos.registerUser(dni)) {
                System.out.println("Error al registrar el usuario. Por favor, intente de nuevo.");
                return; // Salir del programa si no se pudo registrar
            }
        }

        // Autenticación exitosa
        System.out.println("Autenticación completada. Bienvenido.");
        Usuarios user = new Usuarios(dni); // Crear un objeto Usuarios para manejar su saldo

        // Bucle principal del menú
        while (true) {
            // Mostrar opciones al usuario
            System.out.println("\n¿Qué desea realizar?\n1. Gasto\n2. Ingreso\n3. Salir");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1: // Registrar un gasto
                    int gastoOption = 0;
                    while (gastoOption < 1 || gastoOption > 3) { // Validar que la opción sea válida
                        System.out.println("Seleccione un concepto de gasto:\n1. Vacaciones\n2. Alquiler\n3. Vicios");
                        if (scanner.hasNextInt()) {
                            gastoOption = scanner.nextInt();
                            scanner.nextLine();
                            if (gastoOption < 1 || gastoOption > 3) {
                                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                            }
                        } else {
                            System.out.println("Entrada inválida. Debe ingresar un número.");
                            scanner.nextLine(); // Consumir entrada inválida
                        }
                    }

                    // Pedir la cantidad del gasto
                    System.out.print("Ingrese la cantidad: ");
                    double gasto = scanner.nextDouble();
                    scanner.nextLine();

                    // Registrar el gasto llamando a la clase Gastos
                    Gastos.addGasto(user, gastoOption, gasto);
                    break;

                case 2: // Registrar un ingreso
                    int ingresoOption = 0;
                    while (ingresoOption < 1 || ingresoOption > 2) { // Validar que la opción sea válida
                        System.out.println("Seleccione un concepto de ingreso:\n1. Nómina\n2. Venta en páginas de segunda mano");
                        if (scanner.hasNextInt()) {
                            ingresoOption = scanner.nextInt();
                            scanner.nextLine();
                            if (ingresoOption < 1 || ingresoOption > 2) {
                                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                            }
                        } else {
                            System.out.println("Entrada inválida. Debe ingresar un número.");
                            scanner.nextLine(); // Consumir entrada inválida
                        }
                    }

                    // Pedir la cantidad del ingreso
                    System.out.print("Ingrese la cantidad: ");
                    double ingreso = scanner.nextDouble();
                    scanner.nextLine();

                    // Registrar el ingreso llamando a la clase Ingresos
                    Ingresos.addIngreso(user, ingresoOption, ingreso);
                    break;

                case 3: // Salir del sistema
                    System.out.println("Saliendo del sistema. Gracias.");
                    System.exit(0); // Terminar el programa
                    break;

                default: // Opción inválida en el menú principal
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }
}
