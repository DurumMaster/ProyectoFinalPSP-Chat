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

public class ChatView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtMensaje;
	private JButton btnEnviar;
	private JTextArea taChat;
	private JLabel lblCantUsuActual;
	private JScrollPane scrollPane_1;
	private JList<String> listaUsuariosConectados;
	private JButton btnSalir;
	
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
		
		lblCantUsuActual = new JLabel("");
		lblCantUsuActual.setBounds(515, 69, 200, 54);
		getContentPane().add(lblCantUsuActual);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(531, 237, 179, 244);
		getContentPane().add(scrollPane_1);
		
		listaUsuariosConectados = new JList<String>();
		listaUsuariosConectados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(listaUsuariosConectados);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBounds(626, 560, 89, 23);
		getContentPane().add(btnSalir);
		
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


