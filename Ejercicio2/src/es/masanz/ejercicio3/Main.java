package es.masanz.ejercicio3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        InicializarBD inicializarBD = new InicializarBD("ut2");
        if (!inicializarBD.testSchema()) {

            try {

                Connection conn = inicializarBD.conexion;

                Statement stmt = conn.createStatement();

                String sql = new String(Files.readAllBytes(Paths.get("path_to_your_sql_file.sql")));

                stmt.execute(sql);

                System.out.println("Database created successfully");

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        inicializarBD.testUsuarios();
        inicializarBD.crearUsuarios();
        inicializarBD.cerrar();
    }

}
