package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import controller.Controller;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

public class ChatView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtMensaje;
	private JButton btnEnviar;
	private JTextArea taChat;
	private JLabel lblCantUsuActual;
	private JList<String> listaUsuariosConectados;
	private JButton btnSalir;
	
	public ChatView() {
		getContentPane().setBackground(new Color(0, 0, 0));
		configurarFrame();
		configurarComponentes();
	}
	
	private void configurarComponentes() {
		getContentPane().setLayout(null);
		
		txtMensaje = new JTextField();
		txtMensaje.setToolTipText("");
		txtMensaje.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMensaje.setBounds(388, 683, 319, 64);
		getContentPane().add(txtMensaje);
		txtMensaje.setColumns(10);
		
		btnEnviar = new JButton("");
		btnEnviar.setBackground(new Color(0, 0, 0));
		btnEnviar.setIcon(new ImageIcon(ChatView.class.getResource("/img/avion-de-papel.png")));
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEnviar.setBounds(706, 683, 64, 64);
		getContentPane().add(btnEnviar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(388, 128, 382, 545);
		getContentPane().add(scrollPane);
		
		taChat = new JTextArea();
		scrollPane.setViewportView(taChat);
		taChat.setEditable(false);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 393, 368, 354);
		getContentPane().add(scrollPane_1);
		
		listaUsuariosConectados = new JList<String>();
		scrollPane_1.setViewportView(listaUsuariosConectados);
		listaUsuariosConectados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnSalir = new JButton("Abandonar la sala");
		btnSalir.setBackground(new Color(255, 0, 0));
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSalir.setBounds(388, 83, 382, 34);
		getContentPane().add(btnSalir);
		
		lblCantUsuActual = new JLabel("Usuarios en línea:");
		lblCantUsuActual.setBounds(388, 30, 236, 25);
		getContentPane().add(lblCantUsuActual);
		lblCantUsuActual.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCantUsuActual.setForeground(new Color(236, 121, 187));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(0, 0, 382, 117);
		getContentPane().add(lblLogo);
		lblLogo.setIcon(new ImageIcon(ChatView.class.getResource("/img/logo_psp.JPG")));
		
		JLabel lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(ChatView.class.getResource("/img/foto.png")));
		lblFoto.setBounds(10, 128, 368, 254);
		getContentPane().add(lblFoto);
		
	}

	private void configurarFrame() {
		setSize(800,800);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
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
        setTitle("ChatHub: " + nickname);
        return nickname;
    }
    
    public void mostrarMensaje(String mensaje) {
    	taChat.append(mensaje + "\n");
    }

    public void actualizarCantidadUsuarios(String cant) {
    	lblCantUsuActual.setText("Usuarios en línea: " + cant);
    }
    
    public void actualizarListaUsuarios(String listaUsuarios) {
    	String[] usuarios = listaUsuarios.split(",");
    	DefaultListModel<String> modelo = new DefaultListModel<>();
    	for (String usuario : usuarios) {
    		modelo.addElement(usuario);
    	}
    	listaUsuariosConectados.setModel(modelo);
    }
    
	public void hacerVisible() {
		setVisible(true);		
	}
	
	public void setListener (Controller c) {
		btnEnviar.addActionListener(c);
		txtMensaje.addActionListener(c);
		btnSalir.addActionListener(c);
		listaUsuariosConectados.addListSelectionListener(c);
	}
	
	public JButton getBtnEnviar() {
		return btnEnviar;
	}
	
	public JButton getBtnSalir() {
		return btnSalir;
	}
	
	public JTextField getTxtMensaje() {
		return txtMensaje;
	}
	
	public JList<String> getListaUsuariosConectados() {
		return listaUsuariosConectados;
	}
}


