package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;

import view.ChatView;

public class Cliente implements Runnable{
	
	private Socket socket;
	private PrintWriter fSalida;
	private String nickname;
	private ChatView cv;
	public boolean leer;
	
	public Cliente(String host, int puerto, String nickname, ChatView cv) {
		try {
			socket = new Socket(host, puerto);
			fSalida = new PrintWriter(socket.getOutputStream(), true);			
			this.nickname = nickname;
			this.cv = cv;
			
			fSalida.println(nickname);
			
			new Thread(this).start();
		} catch (Exception e) {
			System.out.println("Error al conectar con el servidor: " + e.getMessage());
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
		try (BufferedReader fEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String mensaje;
			while((mensaje = fEntrada.readLine()) != null) {
				cv.mostrarMensaje(mensaje);
			}
		} catch (IOException e) {
			System.out.println("Error al recibir el mensaje");
			e.printStackTrace();
		}
		
		// TODO: Cerrar salida??
	}
	
}
