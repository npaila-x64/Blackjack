import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Jugador {

    private List<Mano> manos;
    private Integer manoEnJuego;
    private Integer apuesta;
    private Integer monto;
    private String nombre;
    private Boolean esDealer;

    public static Jugador crearJugador() {
        Jugador jugador = new Jugador();
        List<Mano> manos = new LinkedList<>();
        Mano mano = new Mano();
        manos.add(mano);
        jugador.setManos(manos);
        jugador.setNombre("Jugador");
        jugador.setManoEnJuego(0);
        jugador.setMonto();
        jugador.setApuesta();
        jugador.setEsDealer(false);
        return jugador;
    }

    public static Jugador crearDealer() {
        Jugador dealer = crearJugador();
        dealer.setNombre("Dealer");
        dealer.setEsDealer(true);
        return dealer;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    private void setMonto() {
        this.monto = 0;
    }

    private void setApuesta() {
        this.apuesta = 0;
    }

    public void agregarAMonto(Integer cantidad) {
        monto = monto + cantidad;
    }

    public void restarAMonto(Integer cantidad) {
        monto = monto - cantidad;
    }

    public Integer getMonto() {
        return monto;
    }

    public void apostar(Integer apuesta) {
        restarAMonto(apuesta);
        this.apuesta = this.apuesta + apuesta;
    }

    public void reducirApuesta(Integer cantidad) {
        apuesta = apuesta - cantidad;
    }

    public void setApuesta(Integer apuesta) {
        this.apuesta = apuesta;
    }

    public Integer getApuesta() {
        return apuesta;
    }

    private void setManos(List<Mano> manos) {
        this.manos = manos;
    }

    public void setEsDealer(Boolean esDealer) {
        this.esDealer = esDealer;
    }

    public void setManoEnJuego(Integer manoEnJuego) {
        this.manoEnJuego = manoEnJuego;
    }

    public void setManoEnJuego(Mano mano) {
        this.manoEnJuego = manos.indexOf(mano);
    }

    public boolean esDealer() {
        return esDealer;
    }

    public Mano getManoEnJuego() {
        return manos.get(manoEnJuego);
    }

    public List<Mano> getManos() {
        return new ArrayList<>(manos);
    }

    public void partirMano() {
        Mano primeraMano = new Mano();
        Mano segundaMano = new Mano();
        primeraMano.agregarCarta(manos.get(manoEnJuego).getPrimeraCarta());
        segundaMano.agregarCarta(manos.get(manoEnJuego).getSegundaCarta());
        eliminarManoDeJuego(manos.get(manoEnJuego));
        manos.add(primeraMano);
        manos.add(segundaMano);
        this.manoEnJuego = manos.indexOf(primeraMano);
    }

    private void eliminarManoDeJuego(Mano mano) {
        this.manos.remove(mano);
    }

    @Override
    public String toString() {
        return String.format("[Jugador: nombre=%s, esDealer=%s, monto=%s, apuesta=%s, manos=%s]",
                nombre, esDealer, monto, apuesta, manos);
    }
}
