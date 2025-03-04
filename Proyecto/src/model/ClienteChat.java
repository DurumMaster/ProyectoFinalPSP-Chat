package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import view.ChatView;

public class ClienteChat implements Runnable{
	
	private Socket socket;
	private PrintWriter fSalida;
	private ChatView cv;
	private String nickname;
	
	public ClienteChat(String host, int puerto, String nickname, ChatView cv) {
		try {
			socket = new Socket(host, puerto);
			
			fSalida = new PrintWriter(socket.getOutputStream(), true);
			
			this.cv = cv;
			this.nickname = nickname;
			
			cargarChatLog();
			
			enviarMensaje(nickname);
			
			new Thread(this).start();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(cv, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(), "Error servidor",  JOptionPane.ERROR_MESSAGE);
			System.out.println("Error al conectar con el servidor: " + e.getMessage());
			System.exit(0);
		}
	}
	
	private void cargarChatLog() {
	    try (BufferedReader br = new BufferedReader(new FileReader("chatlog.txt"))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            cv.mostrarMensaje(linea);
	        }
	    } catch (IOException e) {
	        System.out.println("Error carga chatlog");
	    }

	}

	public void enviarMensaje(String mensaje) {
		fSalida.println(mensaje);
	}
	
	public void cerrarConexion() {
		try {
            if(fSalida != null)
                fSalida.close();
            if(socket != null)
                socket.close();
        } catch (Exception e) {
            System.out.println("Fallo al cerrar la conexión");
        }
	}

	@Override
	public void run() {
		BufferedReader fEntrada = null;
		try {
			fEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String mensaje;
			while((mensaje = fEntrada.readLine()) != null) {
				cv.mostrarMensaje(mensaje);
			}
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(cv, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(), "Error servidor",  JOptionPane.ERROR_MESSAGE);
			System.out.println("Error al recibir el mensaje");
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
	
	public String getNickname() {
		return nickname;
	}
	
	public PrintWriter getfSalida() {
		return fSalida;
	}
}
