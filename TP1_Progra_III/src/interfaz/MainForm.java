package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JTextPane;
import javax.swing.text.StyleConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.security.Key;
import java.text.NumberFormat.Style;

import javax.swing.DropMode;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.Canvas;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSeparator;

public class MainForm {
	
	
	
	private JFrame frmWordle;
	
	private JTextField textoUsuario1;
	private JTextField textoImpreso1;
	
	private JTextField letrasCorrectas;
	private JTextField letrasIncorrectas;
	private JTextField letrasCasiCorrectas;
	
	private static boolean palabraCorrecta = true;

	ListaPalabras palabraRandom = new ListaPalabras();
	String palabra;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					MainForm window = new MainForm();
					window.frmWordle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	
	public MainForm() {
		
		palabra = palabraRandom.obtenerPalabraAleatoria();
		initialize();
			
	}

	/**
	 * Initialize the contents of the frame.
	 */

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void initialize() {
		
		frmWordle = new JFrame();
		frmWordle.getContentPane().setEnabled(false);
		frmWordle.setTitle("Wordle");
		frmWordle.setBounds(100, 100, 687, 589);
		frmWordle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWordle.getContentPane().setLayout(null);
		
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////TUTORIAL
		//
		JInternalFrame internalFrame = new JInternalFrame("Explicacion Modo de Juego");
		internalFrame.setBounds(326, 367, 335, 183);
		internalFrame.setClosable(true);
		internalFrame.setToolTipText("Tutorial");
		frmWordle.getContentPane().add(internalFrame);
		
		
		JLabel lblNewLabel = new JLabel("Explicación Wordlepene");
		internalFrame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		lblNewLabel.setToolTipText("Tutorial");
		
		
		internalFrame.setVisible(true);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////TEXTO DONDE SE MUESTRAN LAS PALABRAS
		//
		textoImpreso1 = new JTextField();
		textoImpreso1.setFocusable(false);
		textoImpreso1.setVisible(false);
		textoImpreso1.setBounds(174, 85, 109, 32);
		frmWordle.getContentPane().add(textoImpreso1);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////TEXTO DONDE ESCRIBE EL USUARIO
		//
		textoUsuario1 = new JTextField();	
		textoUsuario1.setBackground(Color.gray);
		textoUsuario1.setBounds(174, 308, 109, 32);
		frmWordle.getContentPane().add(textoUsuario1);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		textoUsuario1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				leerTecla(e);
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////LETRAS
		//
		
		letrasCorrectas = new JTextField();		
		letrasCorrectas.setFocusable(false);
		letrasCorrectas.setBounds(561,85,100,69);
		letrasCorrectas.setBackground(Color.green);;
		frmWordle.getContentPane().add(letrasCorrectas);
		
		letrasCasiCorrectas = new JTextField();		
		letrasCasiCorrectas.setFocusable(false);
		letrasCasiCorrectas.setBounds(561,245,100,69);
		letrasCasiCorrectas.setBackground(Color.yellow);
		frmWordle.getContentPane().add(letrasCasiCorrectas);
		
		letrasIncorrectas = new JTextField();		
		letrasIncorrectas.setFocusable(false);
		letrasIncorrectas.setBounds(561,165,100,69);
		letrasIncorrectas.setBackground(Color.red);
		frmWordle.getContentPane().add(letrasIncorrectas);

		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
	}
	
		//CREAR OTRAS FUNCIONES Y QUE ESTA NO HAGA TODO, SI ES POSIBLE PASAR A OTRAS CLASES E IR ORGANIZANDO
		public void leerTecla(KeyEvent e) {
		//	System.out.println(detectarBoton(e));
			String cantidadLetras = textoUsuario1.getText();
			
			if(detectarEnter(e) && cantidadLetras.length() < 4) {
				textoUsuario1.setText(cantidadLetras);
				ventanaAdvertencia();
			}
			
			if(!detectarEnter(e) && cantidadLetras.length() > 3) {
				textoUsuario1.setText(null);
				ventanaAdvertencia();
			}
				
			if(detectarEnter(e) && cantidadLetras.length() == 4) {
				textoUsuario1.setText(null);
			
				for(int i = 0; i < cantidadLetras.length(); i++) {
					if(cantidadLetras.charAt(i) == palabra.charAt(i)) {
						letrasCorrectas.setText(letrasCorrectas.getText() + cantidadLetras.charAt(i) + " ");
						textoImpreso1.setForeground(Color.green);
					}
					else {
						palabraCorrecta = palabraCorrecta && false;
						if(checkCaracteres(cantidadLetras, i)) {
//							textoImpreso1.setForeground(Color.yellow);
							letrasCasiCorrectas.setText(letrasCasiCorrectas.getText() + cantidadLetras.charAt(i) + " ");
						}
						else {
							letrasIncorrectas.setText(letrasIncorrectas.getText() + cantidadLetras.charAt(i) + " ");
//							textoImpreso1.setForeground(Color.red);
						}
						
										
					}
					
				}	
				if(palabraCorrecta) {
					System.out.print("es la palabra /break");
					
				}	
				if(!palabraCorrecta) {
					System.out.print("no es la palabra ");
				}
				textoImpreso1.setVisible(true);
				textoImpreso1.setText(cantidadLetras);
			}	
			palabraCorrecta = true;
		}	
	
	private void ventanaAdvertencia() {
			
			JInternalFrame FrameAdvertencia = new JInternalFrame("Advertencia");
			FrameAdvertencia.setBounds(100, 100, 200, 50);
			FrameAdvertencia.setClosable(true);
			FrameAdvertencia.setToolTipText("advertencia");
			frmWordle.getContentPane().add(FrameAdvertencia);
			
			
			JLabel lblNewLabel = new JLabel("Tienen que ser 4 caracteres");
			FrameAdvertencia.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
			lblNewLabel.setToolTipText("AVISO");
			
			FrameAdvertencia.setVisible(true);
			
			
	
		}
	
	//SI ESTA EL CARACTER ESTA EN LA PALABRA ES TRUE SI NO ES FALSE
		private boolean checkCaracteres(String cadena, int i) {
			for(int j = 0; j < cadena.length(); j++) {
				if(cadena.charAt(i) == palabra.charAt(j)) {
					return true;
				}
			}
			return false;
			
		}
		
		private boolean detectarEnter(KeyEvent xd) {
			boolean bandera = false;
			if(KeyEvent.VK_ENTER== xd.getKeyChar()) {
				bandera = true;
			}
			
			return bandera;
		}
		
	

}
