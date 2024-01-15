package es.masanz.ejercicio3;


import es.masanz.ejercicio3.DAO.UsuarioDAO;
import es.masanz.ejercicio3.DTO.UsuarioDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

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
    public boolean testSchema() {
        ResultSet rs = null;
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?");
            stmt.setString(1, nombre);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    public void crearUsuarios() {


        try (Connection connection = conexion) {

            File file = new File("Ejercicio2/resources/Inserts");
            Scanner scanner = new Scanner(file);

            String q = "INSERT INTO usuarios (full_name, user, email, password, creation_date, modification_date) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(q);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine().replace("'", "").replace("(", "").replace(")", "").trim();
                String[] data = line.split(", ");

                UsuarioDTO usuarioDTO = new UsuarioDTO(data[0],data[1],data[2],data[3]);
                pstmt.setString(1, usuarioDTO.getFullName());
                pstmt.setString(2, usuarioDTO.getUserName());
                pstmt.setString(3, usuarioDTO.getEmail());
                pstmt.setString(4, usuarioDTO.getPassword());
                pstmt.setDate(5, usuarioDTO.getCreationDate());
                pstmt.setDate(6, usuarioDTO.getModificationDate());
                pstmt.executeUpdate();
            }


            scanner.close();
        } catch (SQLException | FileNotFoundException e) {
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
