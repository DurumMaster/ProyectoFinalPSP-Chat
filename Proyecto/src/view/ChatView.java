package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controller.Controller;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class ChatView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtMensaje;
	private JButton btnEnviar;
	private JTextArea taChat;
	private JTextArea taUsuarios;
	private JLabel lblCantUsuActual;
	
	public ChatView() {
		configurarFrame();
		configurarComponentes();
	}
	
	private void configurarComponentes() {
		getContentPane().setLayout(null);
		
		txtMensaje = new JTextField();
		txtMensaje.setBounds(66, 612, 471, 66);
		getContentPane().add(txtMensaje);
		txtMensaje.setColumns(10);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEnviar.setBounds(601, 623, 136, 45);
		getContentPane().add(btnEnviar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(66, 69, 410, 470);
		getContentPane().add(scrollPane);
		
		taChat = new JTextArea();
		taChat.setEditable(false);
		scrollPane.setViewportView(taChat);
		
		taUsuarios = new JTextArea();
		taUsuarios.setBounds(601, 185, 136, 329);
		getContentPane().add(taUsuarios);
		
		lblCantUsuActual = new JLabel("");
		lblCantUsuActual.setBounds(619, 102, 96, 21);
		getContentPane().add(lblCantUsuActual);
		
	}

	private void configurarFrame() {
		setSize(800,800);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
    public String solicitarNickname() {
    	String nickname = "";
        do {
            nickname = JOptionPane.showInputDialog(this, "Introduce tu nickname:", "Bienvenido", JOptionPane.QUESTION_MESSAGE);
            
            if (nickname == null) {
            	System.exit(0);
            } else if (nickname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nickname no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (nickname.trim().isEmpty());
        setTitle("Chat divertido: " + nickname);
        return nickname;
    }
    
    public void mostrarMensaje(String mensaje) {
    	taChat.append(mensaje + "\n");
    }

	public void hacerVisible() {
		setVisible(true);		
	}
	
	public void setListener (Controller c) {
		btnEnviar.addActionListener(c);
		txtMensaje.addActionListener(c);
	}
	
	public JButton getBtnEnviar() {
		return btnEnviar;
	}
	
	public JTextField getTxtMensaje() {
		return txtMensaje;
	}

}


