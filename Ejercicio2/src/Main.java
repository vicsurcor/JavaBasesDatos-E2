import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        GestorUsuario gestor = new GestorUsuario();
        gestor.conectar();
        gestor.crearUsuario("AntonioZ", "Anz", "Anz@gmail.com", "contra1");
        System.out.println(gestor.autenticar("Anz", "contra1"));
        gestor.actualizarUsuario(gestor.crearUsuario("Esdfsa","Es", "Es@gmail.com", "contra2"),"Eustaquio", "Eus", "Eus@gmail.com", "contraEus");
        List<Usuario> lista = gestor.obtenerUsuario();
        for (int i = 0; i < lista.size(); i++) {
            Usuario usuario =  lista.get(i);
            gestor.borrarUsuario(usuario);
        }
        gestor.cerrar();

    }
}