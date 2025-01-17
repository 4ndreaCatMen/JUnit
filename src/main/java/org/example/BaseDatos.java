package org.example;

import java.sql.*;

// Esta clase maneja todo lo relacionado con la base de datos SQLite
public class BaseDatos {

    // URL de nuestra base de datos SQLite
    private static final String DB_URL = "jdbc:sqlite:usuarios.db";

    // Este bloque se ejecuta automáticamente la primera vez que usamos la clase
    static {
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            // Creamos la tabla "usuarios" si no existe
            String createTable = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "dni TEXT PRIMARY KEY, " + // El DNI será único y servirá como identificador
                    "saldo REAL DEFAULT 0.0" + // El saldo empieza en 0.0 por defecto
                    ");";
            stmt.execute(createTable); // Ejecutamos el comando para crear la tabla
        } catch (SQLException e) {
            // Por si algo sale mal al iniciar la base de datos
            System.out.println("Error inicializando la base de datos: " + e.getMessage());
        }
    }

    // Comprueba si un usuario está en la base de datos usando su DNI
    public static boolean authenticateUser(String dni) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT dni FROM usuarios WHERE dni = ?")) {
            pstmt.setString(1, dni); // Pasamos el DNI al query
            ResultSet rs = pstmt.executeQuery(); // Ejecutamos el query
            return rs.next(); // Si devuelve algo, significa que el usuario existe
        } catch (SQLException e) {
            // Si algo falla durante la consulta
            System.out.println("Error autenticando al usuario: " + e.getMessage());
            return false;
        }
    }

    // Verifica si un DNI tiene el formato correcto
    public static boolean validateDNI(String dni) {
        // El DNI debe tener 8 números seguidos de una letra
        return dni.matches("\\d{8}[A-Za-z]");
    }

    // Registra un nuevo usuario en la base de datos
    public static boolean registerUser(String dni) {
        // Primero verificamos que el DNI tenga el formato correcto
        if (!validateDNI(dni)) {
            System.out.println("Error: El DNI debe tener 8 números seguidos de una letra. Ejemplo válido: 12345678A");
            return false; // Si no es válido, no hacemos nada más
        }

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO usuarios (dni, saldo) VALUES (?, 0.0)")) {
            pstmt.setString(1, dni); // Pasamos el DNI
            pstmt.executeUpdate(); // Guardamos al usuario en la base de datos
            return true; // Todo salió bien
        } catch (SQLException e) {
            // Si no se pudo registrar al usuario
            System.out.println("Error registrando al usuario: " + e.getMessage());
            return false;
        }
    }

    // Obtiene el saldo de un usuario usando su DNI
    public static double getSaldo(String dni) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT saldo FROM usuarios WHERE dni = ?")) {
            pstmt.setString(1, dni); // Pasamos el DNI al query
            ResultSet rs = pstmt.executeQuery(); // Ejecutamos el query
            if (rs.next()) {
                return rs.getDouble("saldo"); // Si encontramos al usuario, devolvemos su saldo
            }
        } catch (SQLException e) {
            // Si algo falla al buscar el saldo
            System.out.println("Error obteniendo el saldo: " + e.getMessage());
        }
        return 0.0; // Si no encontramos al usuario, devolvemos 0 como saldo por defecto
    }

    // Actualiza el saldo de un usuario en la base de datos
    public static void updateSaldo(String dni, double nuevoSaldo) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE usuarios SET saldo = ? WHERE dni = ?")) {
            pstmt.setDouble(1, nuevoSaldo); // Le decimos cuál es el nuevo saldo
            pstmt.setString(2, dni); // Y a qué usuario pertenece
            pstmt.executeUpdate(); // Guardamos el cambio en la base de datos
        } catch (SQLException e) {
            // Si algo falla mientras actualizamos el saldo
            System.out.println("Error actualizando el saldo: " + e.getMessage());
        }
    }
}
