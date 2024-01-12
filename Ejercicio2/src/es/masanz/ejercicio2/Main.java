package es.masanz.ejercicio2;

import es.masanz.ejercicio2.DAO.UsuarioDAO;
import es.masanz.ejercicio2.DTO.UsuarioDTO;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        UsuarioDAO gestor = new UsuarioDAO();
        gestor.conectar();
        gestor.crearUsuario("AntonioZ", "Anz", "Anz@gmail.com", "contra1");
        System.out.println(gestor.autenticar("Anz", "contra1"));
        gestor.actualizarUsuario(gestor.crearUsuario("Esdfsa","Es", "Es@gmail.com", "contra2"),"Eustaquio", "Eus", "Eus@gmail.com", "contraEus");
        List<UsuarioDTO> lista = gestor.obtenerUsuario();
        for (int i = 0; i < lista.size(); i++) {
            UsuarioDTO usuarioDTO =  lista.get(i);
            gestor.borrarUsuario(usuarioDTO);
        }
        gestor.cerrar();

    }
}