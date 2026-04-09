package interfaz;

import java.util.Random;


public class ListaPalabras {
	
	String[] palabrasWordle = {"hola", "pene", "tres", "aaaa", "aeio", "1mas"};
	
	
	
	public String obtenerPalabraAleatoria() {
		Random random = new Random();
		return this.palabrasWordle[random.nextInt(palabrasWordle.length)];	
	}
}
