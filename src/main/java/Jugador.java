public class Jugador {

    private Mano mano;
    private Integer apuesta;
    private Integer monto;
    private String nombre;
    private Boolean esDealer;

    public Jugador(Mano mano) {
        this.mano = mano;
    }

    public Jugador(Mano mano, Boolean esDealer) {
        this.mano = mano;
        this.esDealer = esDealer;
    }

    public boolean esDealer() {
        return esDealer;
    }

    public Mano getMano() {
        return mano;
    }
}
