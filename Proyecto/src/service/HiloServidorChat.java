package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.DatosCliente;
import model.ListaClientesSingleton;

public class HiloServidorChat extends Thread {

    /*
        - Solicitar nickname (No se pueden 2 nombres iguales)
        - Bucle infinito:
            - Recoger el mensaje recibido
            - Transformar el mensaje a como se envía
            - Escribirlo en el fichero
            - Mandar mensaje a todos
    */

	BufferedReader fEntrada;
	PrintWriter fSalida;
	Socket socket = null;
    BufferedWriter writer;

	public HiloServidorChat(Socket s) {
		socket = s;
		// Streams de entrada y salida
		try {
			fSalida = new PrintWriter(socket.getOutputStream(), true);
			fEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new FileWriter("chatlog.txt", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		String nickname = "";
        String mensaje;
        try {
            nickname = fEntrada.readLine();
            String entrada = nickname + " - Ha entrado en el chat";
            ListaClientesSingleton.getInstance().mandarMensajeTodos(entrada);
            escribirLog(entrada);
            while (true) {
                
                mensaje = fEntrada.readLine();
                
                mensaje = nickname + "> " + mensaje;

                ListaClientesSingleton.getInstance().mandarMensajeTodos(mensaje);
                escribirLog(mensaje);
            }
            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al mandar el mensaje");
            System.out.println(e.getStackTrace().toString());
        } finally {
        	
        	escribirLog(nickname + " -  ha salido del chat");
        	ListaClientesSingleton.getInstance().mandarMensajeTodos(nickname + " -  ha salido del chat");
        	try {
        		if(fSalida != null) fSalida.close();
        		if(fEntrada != null) fEntrada.close();
        		if(socket != null) socket.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}			
		}
	}
	
	private void escribirLog(String mensaje) {
		try {
			writer.write(mensaje);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}