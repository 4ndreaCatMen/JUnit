package org.example;

import java.sql.*;

public class BasedeDatos {
    private Connection connection;

    public void conectar() throws SQLException {
        String url = "jdbc:sqlite:gastos_ingresos.db";
        connection = DriverManager.getConnection(url);
        System.out.println("Conexión a la base de datos establecida.");
    }

    public boolean autenticarUsuarios(String dni) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE dni = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, dni);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public void guardarTransaccion(String dni, String tipo, String concepto, double cantidad) throws SQLException {
        String query = "INSERT INTO transacciones (dni, tipo, concepto, cantidad, fecha) VALUES (?, ?, ?, ?, datetime('now'))";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, dni);
        stmt.setString(2, tipo);
        stmt.setString(3, concepto);
        stmt.setDouble(4, cantidad);
        stmt.executeUpdate();
    }

    public void cerrarConexion() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
