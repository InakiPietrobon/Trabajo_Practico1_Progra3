package wordle;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.Timer;

public class InterfazGrafica {

	private JFrame frame;
	private LogicaJuego logica;
	private JTextField txtIngreso;
	private JButton btnArriesgar;
	private JLabel[][] matrizGrafica; 
	private int intentoActual;
	private int filasTotales;
	private int columnasTotales;
	private String idiomaElegido;
	private Timer temporizador;
	private int segundosTranscurridos;
	private JLabel lblTemporizador;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazGrafica window = new InterfazGrafica();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InterfazGrafica() {
		// Le doy el estilo visual de Windows
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logica = new LogicaJuego();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("W-UNGS-dle");
		frame.setBounds(100, 100, 500, 500); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null); // Uso absolute layout para acomodar los botones manual
		
		mostrarMenuIdioma();
	}

	private void mostrarMenuIdioma() {
		frame.getContentPane().removeAll(); // Limpio la ventana antes de mostrar el menu
		
		JButton btnEsp = new JButton("Español");
		btnEsp.setBounds(150, 150, 200, 50);
		btnEsp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idiomaElegido = "Español";
				mostrarMenuReglas();
			}
		});
		
		JButton btnIng = new JButton("English");
		btnIng.setBounds(150, 220, 200, 50);
		btnIng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idiomaElegido = "English";
				mostrarMenuReglas();
			}
		});

		frame.getContentPane().add(btnEsp);
		frame.getContentPane().add(btnIng);
		frame.revalidate();
		frame.repaint();
	}

	private void mostrarMenuReglas() {
	    frame.getContentPane().removeAll();
	    
	    // Definición de textos según idioma
	    String titulo = idiomaElegido.equals("English") ? "RULES" : "REGLAS";
	    String general = idiomaElegido.equals("English") ? "Guess the hidden word." : "Adivina la palabra oculta.";
	    String verde = idiomaElegido.equals("English") ? "Green: correct letter and position." : "Verde: letra y posición correcta.";
	    String amarillo = idiomaElegido.equals("English") ? "Yellow: correct letter, wrong position." : "Amarillo: letra correcta, posición incorrecta.";
	    String gris = idiomaElegido.equals("English") ? "Gray: letter not in word." : "Gris: letra no contenida.";
	    String boton = idiomaElegido.equals("English") ? "I understand (Press OK)" : "Entendido (Presione OK)";

	    JLabel lblTitulo = new JLabel(titulo);
	    lblTitulo.setBounds(50, 50, 400, 30);
	    lblTitulo.setHorizontalAlignment(JLabel.CENTER);
	    
	    JLabel lblTexto = new JLabel(general);
	    lblTexto.setBounds(20, 90, 450, 30);
	    lblTexto.setHorizontalAlignment(JLabel.CENTER);
	    
	    JLabel lblTextoVerde = new JLabel(verde);
	    lblTextoVerde.setBounds(20, 120, 450, 30);
	    lblTextoVerde.setHorizontalAlignment(JLabel.CENTER);
	    
	    JLabel lblTextoAmarillo = new JLabel(amarillo);
	    lblTextoAmarillo.setBounds(20, 150, 450, 30);
	    lblTextoAmarillo.setHorizontalAlignment(JLabel.CENTER);
	    
	    JLabel lblTextoGris = new JLabel(gris);
	    lblTextoGris.setBounds(20, 180, 450, 30);
	    lblTextoGris.setHorizontalAlignment(JLabel.CENTER);
	    
	    JButton btnOk = new JButton(boton);
	    btnOk.setBounds(150, 300, 200, 40);
	    btnOk.addActionListener(e -> mostrarMenuDificultad());

	    frame.getContentPane().add(lblTitulo);
	    frame.getContentPane().add(lblTexto);
	    frame.getContentPane().add(lblTextoVerde);
	    frame.getContentPane().add(lblTextoAmarillo);
	    frame.getContentPane().add(lblTextoGris);
	    frame.getContentPane().add(btnOk);
	    
	    frame.revalidate();
	    frame.repaint();
	}

	private void mostrarMenuDificultad() {
	    frame.getContentPane().removeAll();
	    
	    String txtFacil = idiomaElegido.equals("English") ? "Easy (5 letters - 8 tries)" : "Fácil (5 letras - 8 intentos)";
	    String txtNormal = idiomaElegido.equals("English") ? "Normal (8 letters - 5 tries)" : "Normal (8 letras - 5 intentos)";
	    String txtDificil = idiomaElegido.equals("English") ? "Hard (10 letters - 3 tries)" : "Difícil (10 letras - 3 intentos)";

	    JButton btnFacil = new JButton(txtFacil);
	    btnFacil.setBounds(100, 100, 300, 40);
	    btnFacil.addActionListener(e -> iniciarJuego(8, 5, "Fácil"));

	    JButton btnNormal = new JButton(txtNormal);
	    btnNormal.setBounds(100, 160, 300, 40);
	    btnNormal.addActionListener(e -> iniciarJuego(5, 8, "Normal"));

	    JButton btnDificil = new JButton(txtDificil);
	    btnDificil.setBounds(100, 220, 300, 40);
	    btnDificil.addActionListener(e -> iniciarJuego(3, 10, "Difícil"));

	    frame.getContentPane().add(btnFacil);
	    frame.getContentPane().add(btnNormal);
	    frame.getContentPane().add(btnDificil);
	    frame.revalidate();
	    frame.repaint();
	}

	private void iniciarJuego(int filas, int columnas, String dificultad) {
		this.filasTotales = filas;
		this.columnasTotales = columnas;
		this.intentoActual = 0;
		this.matrizGrafica = new JLabel[filas][columnas];
		
		logica.prepararPartida(idiomaElegido, dificultad);

		frame.getContentPane().removeAll();
		
		// --- NUEVO: Configuración e inicio del temporizador ---
				lblTemporizador = new JLabel(idiomaElegido.equals("English")? "Time: 00:00": "Tiempo: 00:00");
				lblTemporizador.setBounds(350, 5, 120, 20); // Esquina superior derecha
				frame.getContentPane().add(lblTemporizador);

				segundosTranscurridos = 0;
				// El Timer de Swing ejecuta el actionPerformed cada 1000 milisegundos (1 segundo)
				temporizador = new Timer(1000, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						segundosTranscurridos++;
						int minutos = segundosTranscurridos / 60;
						int segundos = segundosTranscurridos % 60;
						// Formateamos para que siempre muestre dos dígitos (ej: 01:05)
						lblTemporizador.setText(String.format(idiomaElegido.equals("English")? "Time: %02d:%02d": "Tiempo: %02d:%02d", minutos, segundos));
						
					}
				});
				temporizador.start(); // Inicia el conteo
		
		// Aca armo el panel de la grilla dependieno de la dificultad elegida
		JPanel panelGrid = new JPanel();
		panelGrid.setBounds(50, 30, 380, 300);// Baje un poco el Y (de 20 a 30) para dar espacio al reloj
		panelGrid.setLayout(new GridLayout(filas, columnas, 5, 5));
		frame.getContentPane().add(panelGrid);

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				JLabel label = new JLabel("", JLabel.CENTER);
				label.setOpaque(true);
				label.setBackground(Color.LIGHT_GRAY);
				matrizGrafica[i][j] = label;
				panelGrid.add(label);
			}
		}

		txtIngreso = new JTextField();
		txtIngreso.setBounds(50, 350, 150, 30);
		frame.getContentPane().add(txtIngreso);
		
		// Agrego esto para que sea mas comodo y funcione con el Enter del teclado
		txtIngreso.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ejecutarIntento();
				}
			}
		});
		
		btnArriesgar = new JButton(idiomaElegido.equals("English") 
		        ? "Try": "Arriesgar");
		btnArriesgar.setBounds(210, 350, 140, 30);
		btnArriesgar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ejecutarIntento();
			}
		});
		
		frame.getContentPane().add(btnArriesgar);
		frame.revalidate();
		frame.repaint();
		
		// Le damos foco al cuadro de texto al arrancar para no tener que hacer clic
		txtIngreso.requestFocusInWindow();
	}
	
	private void ejecutarIntento() {
		String palabra = txtIngreso.getText().toUpperCase().trim();
		
		if (palabra.length() != columnasTotales) {
		    String msg = idiomaElegido.equals("English") 
		        ? "The word must have " + columnasTotales + " letters." 
		        : "La palabra tiene que tener " + columnasTotales + " letras.";
		    JOptionPane.showMessageDialog(frame, msg); 
		    return;
		}
		
		for (int i = 0; i < palabra.length(); i++) {
			char letra = palabra.charAt(i);
			if (!Character.isLetter(letra)) {
				String msg = idiomaElegido.equals("English")? "Only letters are allowed. Avoid special characters.": "Solo se permiten letras. Evite caracteres especiales.";
				JOptionPane.showMessageDialog(frame, msg); 
				return;
			}
		}
		
		// Le paso la palabra a la logica y me devuelve el arreglo de colores
		int[] resultados = logica.arriesgarPalabra(palabra);
		
		for (int i = 0; i < columnasTotales; i++) {
			matrizGrafica[intentoActual][i].setText(String.valueOf(palabra.charAt(i)));
			
			if (resultados[i] == 2) {
				matrizGrafica[intentoActual][i].setBackground(Color.GREEN);
			} else if (resultados[i] == 1) {
				matrizGrafica[intentoActual][i].setBackground(Color.YELLOW);
			} else {
				matrizGrafica[intentoActual][i].setBackground(Color.GRAY);
			}
		}

		intentoActual++;
		txtIngreso.setText("");

		if (logica.isVictoria()) {
			// --- NUEVO: Detener tiempo y mostrar mensaje de victoria ---
						temporizador.stop();
						String tiempoFinal = obtenerTiempoFormateado();
						String msg = idiomaElegido.equals("English") ? "Congratulations, you won the game!!! \nYour time was: " + tiempoFinal
								:"¡Felicidades, ganaste el juego!\nTu tiempo fue: " + tiempoFinal;
						JOptionPane.showMessageDialog(frame, msg);
						bloquearEntrada(); 
					} else if (logica.isJuegoTerminado()) {
						// --- NUEVO: Detener tiempo y mostrar mensaje de derrota ---
						temporizador.stop();
						String tiempoFinal = obtenerTiempoFormateado();
						String msg = idiomaElegido.equals("English") ? "Game over. The correct word was: " + logica.getPalabraSecreta() + "\nYour time was: " + tiempoFinal
																		:"Fin del juego. La palabra correcta era: " + logica.getPalabraSecreta() + "\nTu tiempo fue: " + tiempoFinal;
						JOptionPane.showMessageDialog(frame, msg);
						bloquearEntrada();
		}
	}
	// --- NUEVO: Método auxiliar para no repetir código del formato del tiempo ---
		private String obtenerTiempoFormateado() {
			int minutos = segundosTranscurridos / 60;
			int segundos = segundosTranscurridos % 60;
			return String.format("%02d:%02d", minutos, segundos);
		}
	
	private void bloquearEntrada() {
		btnArriesgar.setEnabled(false);
		txtIngreso.setEnabled(false);
		txtIngreso.setEditable(false);
	}
}