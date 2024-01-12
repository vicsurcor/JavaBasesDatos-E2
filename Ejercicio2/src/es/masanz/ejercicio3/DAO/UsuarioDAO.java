package es.masanz.ejercicio3.DAO;

import es.masanz.ejercicio2.DTO.UsuarioDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UsuarioDAO {

    private final Connection conexion;
    public UsuarioDAO(Connection conexion) {
        this.conexion = conexion;
    }


    public UsuarioDTO crearUsuario(String fullName, String userName, String email, String password){

        UsuarioDTO usuario = new UsuarioDTO(fullName,userName,email,password);

        try {
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO usuarios (full_name, user, email, password, creation_date, modification_date) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, usuario.getFullName());
            stmt.setString(2, usuario.getUserName());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setDate(5, usuario.getCreationDate());
            stmt.setDate(6, usuario.getModificationDate());
            stmt.executeUpdate();

        } catch (SQLException e) {
            return null;
        }

        return usuario;
    }

    public boolean borrarUsuario(UsuarioDTO usuario){

        try {
            PreparedStatement stmt = conexion.prepareStatement("DELETE FROM usuarios where user = ?");
            stmt.setString(1, usuario.getUserName());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            return false;
        }

        return true;
    }

    public UsuarioDTO actualizarUsuario(UsuarioDTO usuario, String fullName, String userName, String email, String password){

        UsuarioDTO usuarioAct = usuario;
        usuarioAct.setModificationDate();

        try {
            PreparedStatement stmt = conexion.prepareStatement("Update usuarios set full_name = ?, user = ?, email = ?, password = ?, creation_date = ?, modification_date = ? where fullname = ?");
            stmt.setString(1, usuarioAct.getFullName());
            stmt.setString(2, usuarioAct.getUserName());
            stmt.setString(3, usuarioAct.getEmail());
            stmt.setString(4, usuarioAct.getPassword());
            stmt.setDate(5, usuarioAct.getCreationDate());
            stmt.setDate(6, usuarioAct.getModificationDate());
            stmt.setString(7, usuarioAct.getFullName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            return null;
        }

        return usuarioAct;

    }

    public List<UsuarioDTO> obtenerUsuario(){


        List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO(rs.getString("full_name"), rs.getString("user"), rs.getString("email"), rs.getString("password"));
                usuario.setCreationDate(rs.getDate("creation_date"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return lista;
    }

    public boolean autenticar(String usuario, String contrasena) {
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM usuarios WHERE user = ? AND password = ?");
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            // Si rs tiene al menos un resultado, entonces el usuario y la contraseña son válidos
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
