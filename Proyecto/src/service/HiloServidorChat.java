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
        DatosCliente dc = null;
        try {
            nickname = fEntrada.readLine();
            dc = new DatosCliente(nickname, socket, fSalida);
            System.out.println(nickname + " - Ha entrado en el chat\n" + socket.toString());
            ListaClientesSingleton.getInstance().addCliente(dc);
            while (true) {
                
                mensaje = fEntrada.readLine();
                
                mensaje = nickname + "> " + mensaje;

                ListaClientesSingleton.getInstance().mandarMensajeTodos(mensaje);

                writer.write(mensaje);
                writer.newLine();
                writer.flush();
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
		ListaClientesSingleton.getInstance().removeCliente(dc);
		System.out.println("Finalizada conexión con el cliente: " + socket.toString());
		
		fSalida.close();
		try {
			fEntrada.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}