package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import view.ChatView;

public class ClienteChat implements Runnable {

	private Socket socket;
	private PrintWriter fSalida;
	private BufferedReader fEntrada;
	private ChatView cv;
	private String nickname;

	public ClienteChat(String host, int puerto, ChatView cv) {
		try {
			socket = new Socket(host, puerto);

			fSalida = new PrintWriter(socket.getOutputStream(), true);
			fEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			this.cv = cv;
			this.nickname = cv.solicitarNickname();

			enviarNicknameAlServidor();

			cargarChatLog();

			new Thread(this).start();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(cv, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(), "Error servidor",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	private void enviarNicknameAlServidor() {
		try {
			enviarMensaje(nickname);
			String respuesta = fEntrada.readLine();

			while (respuesta.equals("Este nickname ya está en uso. Prueba con otro.")) {
				JOptionPane.showMessageDialog(cv, respuesta, "Error", JOptionPane.ERROR_MESSAGE);
				this.nickname = cv.solicitarNickname();
				enviarMensaje(nickname);
				respuesta = fEntrada.readLine();
			}

		} catch (Exception e) {
			System.out.println("Error al enviar nickname: " + e.getMessage());
		}
	}

	private void cargarChatLog() {
		try (BufferedReader br = new BufferedReader(new FileReader("chatlog.txt"))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				cv.mostrarMensaje(linea);
			}
		} catch (IOException e) {
			System.out.println("Error al cargar chatlog.txt");
		}

	}

	public void enviarMensaje(String mensaje) {
		fSalida.println(mensaje);
	}

	public void cerrarConexion() {
		try {
			if (fSalida != null)
				fSalida.close();
			if (socket != null)
				socket.close();
		} catch (Exception e) {
			System.out.println("Fallo al cerrar la conexión");
		}
	}

	public void enviarMensajePrivado(String destinatario, String mensaje) {
		if (fSalida != null && mensaje != null && !mensaje.trim().isEmpty()) {
			fSalida.println("/privado " + destinatario + " " + mensaje);
		}
	}

	@Override
	public void run() {
		BufferedReader fEntrada = null;
		try {
			fEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String mensaje;
			while ((mensaje = fEntrada.readLine()) != null) {
				if (mensaje.startsWith("USUARIOS:")) {
					String listaUsuarios = mensaje.substring(9);
					cv.actualizarListaUsuarios(listaUsuarios);
				} else if (mensaje.startsWith("CONECTADOS:")) {
					cv.actualizarCantidadUsuarios(mensaje.substring(11));
				} else {
					cv.mostrarMensaje(mensaje);
				}
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(cv, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(), "Error servidor",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			if (fEntrada != null) {
				try {
					fEntrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.exit(0);
		}
	}

}
