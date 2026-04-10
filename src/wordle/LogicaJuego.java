package wordle;


import java.util.Random;

/**
 * Clase encargada de la lógica del juego.
 * No contiene elementos visuales, solo manejo de datos.
 */
public class LogicaJuego {
    private String palabraSecreta;
    private int intentosRestantes;
    private boolean juegoTerminado;
    private boolean victoria;
    private String[] listaPalabras = {"SILLA"}; // Lista de palabras

    public LogicaJuego() {
        Random rand = new Random();
        this.palabraSecreta = listaPalabras[rand.nextInt(listaPalabras.length)]; // elije una palabra random
        this.intentosRestantes = 6; // El usuario tiene 6 intentos
        this.juegoTerminado = false;
        this.victoria = false;
    }

    
     /* Procesa la palabra ingresada por el usuario y devuelve el estado de cada letra.*/

    public int[] arriesgarPalabra(String intento) {
        intento = intento.toUpperCase();
        int[] resultado = new int[5]; // 0: Gris, 1: Amarillo, 2: Verde 
        
        // Verificación de Victoria
        if (intento.equals(palabraSecreta)) {
            victoria = true;
            juegoTerminado = true; 
        }
        // Verificación de cada letra para el "estado"
        for (int i = 0; i < 5; i++) {
            char letraIntento = intento.charAt(i);
            if (letraIntento == palabraSecreta.charAt(i)) {
                resultado[i] = 2; // Verde: letra y posición correcta
            } else if (palabraSecreta.contains(String.valueOf(letraIntento))) {
                resultado[i] = 1; // Amarillo: letra está pero en otra posición 
            } else {
                resultado[i] = 0; // Gris: la letra no está 
            }
        }
        
        intentosRestantes--;// Control de intentos
        if (intentosRestantes <= 0 && !victoria) {
            juegoTerminado = true; // El usuario pierde tras 6 intentos
        }

        return resultado;
    }
    

    // Metodos para que la interfaz consulte el estado actual
    public int getIntentosRestantes() { return intentosRestantes; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public boolean isVictoria() { return victoria; }
    public String getPalabraSecreta() { return palabraSecreta; }
}