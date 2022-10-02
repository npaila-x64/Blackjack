import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidad {

    private static int pedirValor() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public static int pedirOpcion() {
        try {
            return pedirValor();
        } catch (InputMismatchException e) {
            return pedirOpcion();
        }
    }
}
