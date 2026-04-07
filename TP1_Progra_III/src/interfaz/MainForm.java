package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import java.awt.GridLayout;
import javax.swing.JTextPane;
import javax.swing.text.StyleConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	private JTextPane hola;
	
	private boolean palabraCorrecta = true;
	private boolean ganaste = false;
	private boolean perdiste = false;
	private String palabra = "aeio";
	private JTextField textField;
	private JTextField textField_1;

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

		
		initialize();
		
//		conseguir(textoUsuario1);
		
		
//		if(palabraCorrecta) {
//			System.out.println("perdiste");
//		}
		
//		hola.setSelectedTextColor(Color.black);
//		hola.setText("hola");
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
		textoImpreso1.setBounds(299, 85, 109, 32);
		frmWordle.getContentPane().add(textoImpreso1);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////TEXTO DONDE ESCRIBE EL USUARIO
		//
		textoUsuario1 = new JTextField();	
		textoUsuario1.setBackground(new Color(0, 128, 255));
		textoUsuario1.setBounds(299, 283, 109, 32);
		frmWordle.getContentPane().add(textoUsuario1);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		textoUsuario1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				algo(e);
			}
		});
		
		
		//textoImpreso1.setForeground(Color.gray);
			//textoImpreso1.setText(texto + cantidadLetras.charAt(i));	
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
	}
	
		public void algo(KeyEvent e) {
		//	System.out.println(detectarBoton(e));
			String cantidadLetras = textoUsuario1.getText();
			
			if(detectarEnter(e) && cantidadLetras.length() < 4 || cantidadLetras.length() > 4) {
				textoUsuario1.setText(cantidadLetras);
				ventanaAdvertencia();
			}
			
			if(!detectarEnter(e) && cantidadLetras.length() > 3) {
				textoUsuario1.setText("");
				ventanaAdvertencia();
			}
				
			if(detectarEnter(e) && cantidadLetras.length() == 4) {
//				textoUsuario1.setEditable(false);
//				textoUsuario1.setEnabled(false);
				textoUsuario1.setText(null);
			
				for(int i = 0; i < cantidadLetras.length(); i++) {
					if(cantidadLetras.charAt(i) == palabra.charAt(i)) {
						palabraCorrecta = true;
					}
					else {
						palabraCorrecta = false;
						if(checkCaracteres(cantidadLetras, i)) {
							textoImpreso1.setForeground(Color.yellow);
						}
						else {
							cantidadLetras.equals(palabra);
							textoImpreso1.setForeground(Color.red);
						}
						
					}
					if(palabraCorrecta) {
						textoImpreso1.setForeground(Color.green);
					}
					textoImpreso1.setVisible(true);
					textoImpreso1.setText(cantidadLetras);
					
				}		
			}		
		}	
	
	private void ventanaAdvertencia() {
			
			JInternalFrame internalFrame = new JInternalFrame("Explicacion Modo de Juego");
			internalFrame.setBounds(100, 100, 200, 50);
			internalFrame.setClosable(true);
			internalFrame.setToolTipText("advertencia");
			frmWordle.getContentPane().add(internalFrame);
			
			
			JLabel lblNewLabel = new JLabel("Tienen que ser 4 caracteres");
			internalFrame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
			lblNewLabel.setToolTipText("AVISO");
			
			
			internalFrame.setVisible(true);
	
		}
	
	
	private void agregarTextoUsuario() {
		
		
		textoUsuario1 = new JFormattedTextField();
		textoUsuario1.setBounds(100, 100, 100, 100);
		frmWordle.getContentPane().add(textoUsuario1);
		
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
