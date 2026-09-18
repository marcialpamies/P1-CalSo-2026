package juego.geometria; 

import java.util.*;

public enum Direccion {
	ARRIBA, ABAJO, DERECHA, IZQUIERDA;
	
	public Direccion opuesta(Direccion direccion) {
		switch (direccion) {
		case ARRIBA: return ABAJO;
		case ABAJO: return ARRIBA;
		case IZQUIERDA: return DERECHA;
		case DERECHA: return IZQUIERDA;
		default: return null;
		}
	}

	public Direccion aleatoria() {
		Direccion arrayDirecciones[] = Direccion.values();

		Random rnd = new Random();
		int indice = rnd.nextInt(4);

		return arrayDirecciones[indice];
	}
		
	
}
