public class Jugador {

    private Mano mano;
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
        this.mano = mano;
        apuesta = 0;
        monto = 0;
    }

    public boolean esDealer() {
        return esDealer;
    }

    public Mano getMano() {
        return mano;
    }

    @Override
    public String toString() {
        return mano.toString();
    }
}
