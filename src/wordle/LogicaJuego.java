package wordle;


import java.util.ArrayList;
import java.util.Random;

/* Clase encargada de la lógica del juego
 * No contiene elementos visuales, solo manejo de datos */
public class LogicaJuego {
    private String palabraSecreta;
    private int intentosRestantes;
    private boolean juegoTerminado;
    private boolean victoria;
    private String[] listaPalabras = {"SILLA", "PATIO", "LUGAR", "SESOS", "TORTA", "PERRO", "LETRA"}; // Lista de palabras

    public LogicaJuego() {
        Random rand = new Random();
        this.palabraSecreta = listaPalabras[rand.nextInt(listaPalabras.length)]; // Elije una palabra random
        this.intentosRestantes = 6; // El usuario tiene 6 intentos
        this.juegoTerminado = false;
        this.victoria = false;
    }
    
     // Procesa la palabra ingresada por el usuario y devuelve el estado de cada letra
     public int[] arriesgarPalabra(String intento) {
         intento = intento.toUpperCase();
         int[] resultado = new int[5]; // 0: Gris, 1: Amarillo, 2: Verde         
         /* Creamos una lista de letras que contiene de forma repetida todas las letras de la palabra:
          * servira para saber cuantas letras de un mismo tipo existen en la palabra y
          * de esta manera saber si ponerla en verde, amarillo o gris */
         ArrayList<Character> letrasContenidas = new ArrayList<>();
         
         for (int i = 0; i < 5; i++) {
             letrasContenidas.add(palabraSecreta.charAt(i));
         }
         
         // Verificamos si el usuario gano
         if (intento.equals(palabraSecreta)) {
             victoria = true;
             juegoTerminado = true; 
         }
         /** Dividimos en 2 ciclos la revisión de las letras con respecto a la palabra para evitar bugs dados
          * por la falta de información de cuantas letras verdes existen
          */

         // Primera pasada: buscamos las letras verdes
         for (int i = 0; i < 5; i++) { // Iteramos letra por letra
             char letraIntento = intento.charAt(i);
             if (letraIntento == palabraSecreta.charAt(i)) { // Verificamos si la letra ingresada coincide con la letra de la palabra del juego
                 resultado[i] = 2; // Verde
                 letrasContenidas.remove((Object) letraIntento); // Consumimos esa reserva (Casteamos a Object para que no tome letraIntento como un indice)
             }
         }

         // Segunda pasada: buscamos las letras amarillas y grises
         for (int i = 0; i < 5; i++) {
             // Si en la pasada anterior ya lo marcamos en verde, lo ignoramos
             if (resultado[i] == 2) {
                 continue;
             }
             
             char letraIntento = intento.charAt(i);
             
             // Verificamos si la letra ingresada esta contenida en la palabra
             if (letrasContenidas.contains(letraIntento)) { // Lo hacemos sobre letrasContenidas para asegurarnos que no haya sido marcada
                 resultado[i] = 1; // Amarillo
                 letrasContenidas.remove((Object) letraIntento); // Consumimos esa reserva (Casteamos a Object para que no tome letraIntento como un indice)
             } else {
                 resultado[i] = 0; // Gris
             }
         }
         
         intentosRestantes--; // Consumimos un intento
         if (intentosRestantes <= 0 && !victoria) { // Si se queda sin intentos, pierde
        	 juegoTerminado = true; 
         }

         return resultado;
     }
    

    // Metodos para que la interfaz consulte el estado actual
    public int getIntentosRestantes() { return intentosRestantes; }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public boolean isVictoria() { return victoria; }
    public String getPalabraSecreta() { return palabraSecreta; }
}
