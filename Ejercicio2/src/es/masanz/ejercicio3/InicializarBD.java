package es.masanz.ejercicio3;


import es.masanz.ejercicio3.DAO.UsuarioDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class InicializarBD {

    public String nombre;
    public Connection conexion;

    public InicializarBD(String nombre) {
        this.nombre = nombre;
        conectar();
    }
    public void conectar() {
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("Ejercicio2/resources/db.properties");
            props.load(in);
            in.close();

            String url = props.getProperty("DB_URL");
            String usuario = props.getProperty("DB_USER");
            String contrasena = props.getProperty("DB_PASSWORD");
            conexion = DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException | IOException e) {
            e.printStackTrace();

        }
    }
    public void testSchema() {

        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?");
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("The database " + nombre + " exists.");
            } else {
                System.out.println("The database " + nombre + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void testUsuarios() {


            try {
                PreparedStatement stmt = conexion.prepareStatement("SELECT *\n" +
                        "FROM INFORMATION_SCHEMA.TABLES\n" +
                        "WHERE TABLE_SCHEMA = ?\n" +
                        "AND TABLE_NAME = ?\n");
                stmt.setString(1, nombre);
                stmt.setString(2, "usuarios");
                ResultSet tables = stmt.executeQuery();
                if (tables.next()) {
                    System.out.println("The table exists.");
                } else {
                    System.out.println("The table does not exist.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


    }
    public void cerrar() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
