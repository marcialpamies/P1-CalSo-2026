package juego.geometria;

public class circulo {
    
	public static int DEFAULT_RADIO = 5;
    
    private Punto centro;
    private int radio;

    public circulo(Punto centroIni, int radioIni){
        radio = radioIni;
    }

    public circulo(){
        this(new Punto(), DEFAULT_RADIO);
    }
    
    public Punto getCentro() {
        return new Punto(centro);
    }

    public int getRadio() {
        return radio;
    }

    public double getPerimetro() {
        return 2 * Math.PI * radio;
    }
    
    public void desplazar(int incX, int incY){
        centro = centro.desplazar(incX, incY);
    }
    
    public void escalar(double porcentaje){
        radio = (int)(radio * (porcentaje/100));
    }
}