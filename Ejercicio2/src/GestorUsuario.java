import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuario {

    private Connection conexion;
    public GestorUsuario() {

        conectar();

    }
    public void conectar() {
        try {

            String url = "jdbc:mysql://localhost:3306/acda";
            String usuario = "root";
            String contrasena = "rootACDA";
            conexion = DriverManager.getConnection(url, usuario, contrasena);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario crearUsuario(String fullName, String userName, String email, String password){

        Usuario usuario = new Usuario(fullName,userName,email,password);

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

    public boolean borrarUsuario(Usuario usuario){

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

    public Usuario actualizarUsuario(Usuario usuario, String fullName, String userName, String email, String password){

        Usuario usuarioAct = usuario;
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

    public List<Usuario> obtenerUsuario(){


        List<Usuario> lista = new ArrayList<Usuario>();
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("full_name"), rs.getString("user"), rs.getString("email"), rs.getString("password"));
                usuario.setCreationDate(rs.getDate("creation_date"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return lista;
    }

    boolean autenticar(String usuario, String contrasena) {
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
