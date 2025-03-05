package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ClienteChat;
import view.ChatView;

public class Controller implements ActionListener, ListSelectionListener {

	private ChatView cv;
	private ClienteChat cliente;

	public Controller(ChatView cv, ClienteChat cliente) {
		this.cv = cv;
		this.cliente = cliente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (e.getSource().equals(cv.getBtnEnviar())) {
				enviarMensaje();
			} else if (e.getSource().equals(cv.getBtnSalir())) {
				salir();
			}
		} else if (e.getSource() instanceof JTextField) {
			if (e.getSource().equals(cv.getTxtMensaje())) {
				enviarMensaje();
			}
		}
	}

	private void salir() {
		int opcion = JOptionPane.showConfirmDialog(cv, "¿Estás seguro de que quieres salir del chat?", "Salir",
				JOptionPane.YES_NO_OPTION);

		if (opcion == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(cv, "Has abandonado el chat.", "Salir", JOptionPane.INFORMATION_MESSAGE);
			cliente.cerrarConexion();
			System.exit(0);
		}
	}

	private void enviarMensaje() {
		String mensaje = cv.getTxtMensaje().getText().trim();
		if (!mensaje.isEmpty()) {
			cliente.enviarMensaje(mensaje);
			cv.getTxtMensaje().setText("");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String usuarioSelec = cv.getListaUsuariosConectados().getSelectedValue();
		if (usuarioSelec != null) {
			cv.getTxtMensaje().setText("/privado " + usuarioSelec + " ");
			cv.getTxtMensaje().requestFocus();
		}
	}

}
