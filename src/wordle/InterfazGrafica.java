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
		
		JLabel lblTitulo = new JLabel("REGLAS");
		lblTitulo.setBounds(50, 50, 400, 30);
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblTitulo);

		JLabel lblTexto = new JLabel("Adivina la palabra oculta.");
		lblTexto.setBounds(20, 90, 450, 30);
		lblTexto.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblTexto);
		
		JLabel lblTextoVerde = new JLabel("El color verde indica letra contenida y posición correcta.");
		lblTextoVerde.setBounds(20, 120, 450, 30);
		lblTextoVerde.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblTexto);
		
		JLabel lblTextoAmarillo = new JLabel("El color amarillo indica letra contenida y posición incorrecta.");
		lblTextoAmarillo.setBounds(20, 150, 450, 30);
		lblTextoAmarillo.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblTexto);
		
		JLabel lblTextoGris = new JLabel("El color gris indica letra no contenida y posición inexistente.");
		lblTextoGris.setBounds(20, 180, 450, 30);
		lblTextoGris.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblTexto);
		
		JButton btnOk = new JButton("Entendido (Presione OK)");
		btnOk.setBounds(150, 300, 200, 40);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarMenuDificultad();
			}
		});

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
		
		JButton btnFacil = new JButton("Fácil (5 letras - 8 intentos)");
		btnFacil.setBounds(100, 100, 300, 40);
		btnFacil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarJuego(8, 5, "Fácil");
			}
		});

		JButton btnNormal = new JButton("Normal (8 letras - 5 intentos)");
		btnNormal.setBounds(100, 160, 300, 40);
		btnNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarJuego(5, 8, "Normal");
			}
		});

		JButton btnDificil = new JButton("Difícil (10 letras - 3 intentos)");
		btnDificil.setBounds(100, 220, 300, 40);
		btnDificil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarJuego(3, 10, "Difícil");
			}
		});

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
		
		// Aca armo el panel de la grilla dependieno de la dificultad elegida
		JPanel panelGrid = new JPanel();
		panelGrid.setBounds(50, 20, 380, 300);
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
		
		btnArriesgar = new JButton("Arriesgar");
		btnArriesgar.setBounds(210, 350, 140, 30);
		btnArriesgar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ejecutarIntento();
			}
		});
		
		frame.getContentPane().add(btnArriesgar);
		frame.revalidate();
		frame.repaint();
	}
	
	private void ejecutarIntento() {
		String palabra = txtIngreso.getText().toUpperCase().trim();
		
		if (palabra.length() != columnasTotales) {
			JOptionPane.showMessageDialog(frame, "La palabra tiene que tener " + columnasTotales + " letras."); 
			return;
		}
		
		for (int i = 0; i < palabra.length(); i++) {
			char letra = palabra.charAt(i);
			if (!Character.isLetter(letra)) {
				JOptionPane.showMessageDialog(frame, "Solo se permiten letras. Evite caracteres especiales."); 
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
			JOptionPane.showMessageDialog(frame, "¡Felicidades, ganaste el juego!");
			bloquearEntrada(); 
		} else if (logica.isJuegoTerminado()) {
			JOptionPane.showMessageDialog(frame, "Fin del juego. La palabra correcta era: " + logica.getPalabraSecreta());
			bloquearEntrada();
		}
	}
	
	private void bloquearEntrada() {
		btnArriesgar.setEnabled(false);
		txtIngreso.setEnabled(false);
		txtIngreso.setEditable(false);
	}
}