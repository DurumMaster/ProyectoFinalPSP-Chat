package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Cliente;
import model.ListaClientesSingleton;
import view.ChatView;

public class Controller implements ActionListener {

	private ChatView cv;
	private Cliente cliente;
	
	public Controller(ChatView cv, Cliente cliente) {
		this.cv = cv;
		this.cliente = cliente;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (e.getSource().equals(cv.getBtnEnviar())) {
				enviarMensaje();
			}
		}
	}

	private void enviarMensaje() {
		String mensaje = cv.getTxtMensaje().getText().trim();
		if(!mensaje.isEmpty()) {
			cliente.enviarMensaje(mensaje);
			cv.getTxtMensaje().setText("");
		}
	}
	
	
}
