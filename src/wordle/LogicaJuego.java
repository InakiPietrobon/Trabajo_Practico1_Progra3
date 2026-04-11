package wordle;

import java.util.Random;

public class LogicaJuego {
	
	// Aca guardo las palabras separadas por idioma y cantidad de letras
	private String[] esp5 = {"PERRO", "GATOS", "LAPIZ", "SILLA", "MANGO"};
	private String[] esp8 = {"ELEFANTE", "MILANESA", "PANTALLA", "HELADERA"};
	private String[] esp10 = {"MURCIELAGO", "CARPINTERO", "BICICLETA"};

	private String[] ing5 = {"APPLE", "HOUSE", "WATER", "BREAD"};
	private String[] ing8 = {"ELEPHANT", "UMBRELLA", "BASEBALL"};
	private String[] ing10 = {"APPEARANCE", "STRAWBERRY", "BASKETBALL"};

	private String palabraSecreta;
	private int intentosPermitidos;
	private int longitudPalabra;
	private boolean victoria;
	private boolean juegoTerminado;
	private int intentosUsados;

	public void prepararPartida(String idioma, String dificultad) {
		this.victoria = false;
		this.juegoTerminado = false;
		this.intentosUsados = 0;
		String[] poolActual = null;

		// Configuro la cantidad de intentos y el largo de la palabra segun la dificultad
		switch (dificultad) {
			case "Fácil":
				this.intentosPermitidos = 8;
				this.longitudPalabra = 5;
				break;
			case "Normal":
				this.intentosPermitidos = 5;
				this.longitudPalabra = 8;
				break;
			case "Difícil":
				this.intentosPermitidos = 3;
				this.longitudPalabra = 10;
				break;
		}

		// Selecciono el arreglo de palabras correcto cruzando el idioma con la longitud
		if (idioma.equals("Español")) {
			if (longitudPalabra == 5) poolActual = esp5;
			else if (longitudPalabra == 8) poolActual = esp8;
			else poolActual = esp10;
		} else {
			if (longitudPalabra == 5) poolActual = ing5;
			else if (longitudPalabra == 8) poolActual = ing8;
			else poolActual = ing10;
		}

		Random rand = new Random();
		this.palabraSecreta = poolActual[rand.nextInt(poolActual.length)];
	}

	public int[] arriesgarPalabra(String intento) {
		int[] resultados = new int[longitudPalabra];
		boolean aciertoTotal = true;
		this.intentosUsados++;

		// Cuento cuantas veces aparece cada letra en la palabra secreta para arreglar el bug de los amarillos
		int[] letrasDisponibles = new int[256];
		for (int i = 0; i < longitudPalabra; i++) {
			letrasDisponibles[palabraSecreta.charAt(i)]++;
		}

		// Primera pasada: me fijo unicamente si hay aciertos exactos (verdes)
		for (int i = 0; i < longitudPalabra; i++) {
			char letraIntento = intento.charAt(i);
			if (letraIntento == palabraSecreta.charAt(i)) {
				resultados[i] = 2; // Marco como verde
				letrasDisponibles[letraIntento]--; // Descuento esta letra de las disponibles
			} else {
				aciertoTotal = false;
			}
		}

		// Segunda pasada: me fijo si hay letras correctas pero en mal lugar (amarillos)
		for (int i = 0; i < longitudPalabra; i++) {
			if (resultados[i] != 2) { 
				char letraIntento = intento.charAt(i);
				// Si la letra existe y todavia quedan disponibles, la marco en amarillo
				if (letrasDisponibles[letraIntento] > 0) {
					resultados[i] = 1; 
					letrasDisponibles[letraIntento]--; 
				} else {
					resultados[i] = 0; // Gris porque no esta o ya se usaron todas
				}
			}
		}

		if (aciertoTotal) {
			this.victoria = true;
			this.juegoTerminado = true;
		} else if (intentosUsados >= intentosPermitidos) {
			this.juegoTerminado = true;
		}

		return resultados;
	}

	public boolean isVictoria() { return victoria; }
	public boolean isJuegoTerminado() { return juegoTerminado; }
	public String getPalabraSecreta() { return palabraSecreta; }
}