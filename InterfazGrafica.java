package wordle;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
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
	private LogicaJuego logica;// Instancia de la clase que maneja las reglas 
	private JTextField txtIngreso;
	private JButton btnArriesgar;
	private JLabel[][] matrizGrafica = new JLabel[6][5]; //6 intentos por 5 letras
	private int intentoActual = 0;//ubica en q fila de la cuadricula estamos

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
		
		logica = new LogicaJuego(); // Se crea el motor del juego
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("W-UNGS-dle"); 
		frame.setBounds(100, 100, 400, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Panel para la cuadrícula de las letras 6x5
		JPanel panelGrid = new JPanel();
		panelGrid.setBounds(50, 20, 300, 300);
		frame.getContentPane().add(panelGrid);
		panelGrid.setLayout(new GridLayout(6, 5, 5, 5)); // 6 filas, 5 columnas

		// Crear los cuadraditos visuales
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				JLabel label = new JLabel("", JLabel.CENTER);
				label.setOpaque(true);
				label.setBackground(Color.LIGHT_GRAY);// borde para que se vea como casillas
				matrizGrafica[i][j] = label;
				panelGrid.add(label);
			}
		}

		// Campo de entrada de texto
		txtIngreso = new JTextField();
		txtIngreso.setBounds(50, 350, 150, 30);
		frame.getContentPane().add(txtIngreso);
		
		txtIngreso.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				// Si la tecla presionada es Enter, ejecutamos la misma lógica que el botón de accion
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ejecutarIntento();
				}
			}
		});
		
		// Botón de accion
        btnArriesgar = new JButton("Arriesgar"); 
        btnArriesgar.setBounds(210, 350, 140, 30);
        frame.getContentPane().add(btnArriesgar);
        
        btnArriesgar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ejecutarIntento();
            }
        });
	}
	
	/* Este método conecta la interfaz con la lógica. 
	 * Se encarga de pedir la validación y pintar los resultados. 
	 */
	private void ejecutarIntento() {
		String palabra = txtIngreso.getText().toUpperCase().trim();
		
		// Validación de longitud
		if (palabra.length() != 5) {
			JOptionPane.showMessageDialog(frame, "Debe ser de 5 letras"); 
			return;
		}
		
		// Llamamos a la lógica para obtener el estado de cada letra 
		int[] resultados = logica.arriesgarPalabra(palabra);
		
		// Pintamos la fila correspondiente en la interfaz según el estado 
		for (int i = 0; i < 5; i++) {
			matrizGrafica[intentoActual][i].setText(String.valueOf(palabra.charAt(i)));
			
			if (resultados[i] == 2) { // Correcta y posición correcta 
				matrizGrafica[intentoActual][i].setBackground(Color.GREEN);
			} else if (resultados[i] == 1) { // Existe pero posición incorrecta 
				matrizGrafica[intentoActual][i].setBackground(Color.YELLOW);
			} else { // No está en la palabra 
				matrizGrafica[intentoActual][i].setBackground(Color.GRAY);
			}
		}

		intentoActual++;// Pasamos a la siguiente fila/intento
		txtIngreso.setText("");// Limpiamos el campo para el próximo turno

		// Verificar estados finales 
		if (logica.isVictoria()) {
            JOptionPane.showMessageDialog(frame, "¡Ganaste!");
            bloquearEntrada(); //bloquear todo despues de ganar
        } else if (logica.isJuegoTerminado()) {
            JOptionPane.showMessageDialog(frame, "Perdiste. Era: " + logica.getPalabraSecreta());
            bloquearEntrada();//bloquear todo despues de perder
        }
	}
	private void bloquearEntrada() {
		btnArriesgar.setEnabled(false);
		txtIngreso.setEnabled(false);
		txtIngreso.setEditable(false);
	}
}