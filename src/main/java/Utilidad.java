import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidad {

    private static int pedirValor() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public static int pedirOpcionHasta(int limite) {
        try {
            return pedirValorEnteroEnIntervalo(limite);
        } catch (InputMismatchException e) {
            mostrarOpcionInvalida();
            return pedirOpcionHasta(limite);
        }
    }

    public static int pedirValorEnteroEnIntervalo(int limite) throws InputMismatchException {
        int valor = new Scanner(System.in).nextInt();
        if (valor < 0 || valor > limite) {
            throw new InputMismatchException("El parámetro dado sobrepasa el límite");
        }
        return valor;
    }

    public static void mostrarOpcionInvalida() {
        System.out.print("Por favor, escoja una opción válida\n> ");
    }
}
