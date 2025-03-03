package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
			fSalida.println(nickname);
			this.nickname = nickname;
			this.cv = cv;
			new Thread(this).start();
		} catch (Exception e) {
			System.out.println("Ha ocurrido un problema al generar el usuario");
		}
	}
	
	public void enviarMensaje(String mensaje) {
		fSalida.println(mensaje);
	}
	
	public void cerrarConexion() {
		try {
			fSalida.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Fallo al cerrar la conexion");
		}
	}
	
	public PrintWriter getfSalida() {
		return fSalida;
	}
	
	public String getNickname() {
		return nickname;
	}

	@Override
	public void run() {
		BufferedReader fEntrada = null;
		System.out.println("A");
		try {
			fEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("A");
			String mensaje;
			while((mensaje = fEntrada.readLine()) != null) {
				cv.mostrarMensaje(mensaje);
				System.out.println("A");
			}
		} catch (IOException e) {
			System.out.println("Error al recibir el mensaje");
			e.printStackTrace();
		} finally {
			try {
				if(fEntrada != null) fEntrada.close();
			} catch (Exception e2) {
				System.out.println("Error al cerrar la entrada");
			}
		}
	}
	
}
