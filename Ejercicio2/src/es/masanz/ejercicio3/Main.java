package es.masanz.ejercicio3;

public class Main {

    public static void main(String[] args) {

        InicializarBD inicializarBD = new InicializarBD("ut2");
        inicializarBD.testSchema();
        inicializarBD.testUsuarios();
        inicializarBD.cerrar();

    }

}
