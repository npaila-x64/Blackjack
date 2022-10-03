import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private List<Mano> manos;
    private Mano manoEnJuego;
    private Integer apuesta;
    private Integer monto;
    private String nombre;
    private Boolean esDealer;

    public Jugador(String nombre, Mano mano) {
        this.nombre = nombre;
        inicializar(mano);
    }

    public Jugador(Mano mano) {
        inicializar(mano);
    }

    public Jugador(String nombre, Mano mano, Boolean esDealer) {
        this.nombre = nombre;
        this.esDealer = esDealer;
        inicializar(mano);
    }

    public Jugador(Mano mano, Boolean esDealer) {
        this.esDealer = esDealer;
        inicializar(mano);
    }

    private void inicializar(Mano mano) {
        this.manos = new ArrayList<>();
        this.manos.add(mano);
        setManoEnJuego(mano);
        apuesta = 0;
        monto = 0;
    }

    public void setManoEnJuego(Mano manoEnJuego) {
        this.manoEnJuego = manoEnJuego;
    }

    public boolean esDealer() {
        return esDealer;
    }

    public Mano getManoEnJuego() {
        return manoEnJuego;
    }

    public List<Mano> getManos() {
        return manos;
    }

    @Override
    public String toString() {
        return manos.toString();
    }
}
