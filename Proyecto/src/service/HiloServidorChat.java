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

	private Socket socket;
	private BufferedReader fEntrada;
	private PrintWriter fSalida;
	private BufferedWriter writer;
	private String nickname;

	public HiloServidorChat(Socket s) {
		socket = s;
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
        try {
            nickname = fEntrada.readLine();
            
            DatosCliente clienteServidor = new DatosCliente(nickname, socket, fSalida);
            ListaClientesSingleton.getInstance().addCliente(clienteServidor);
            
            String mensajeEntradaUsu = nickname + " - Ha entrado en el chat";
            ListaClientesSingleton.getInstance().mandarMensajeTodos(mensajeEntradaUsu);
            escribirLog(mensajeEntradaUsu);
            
            String mensaje, mensajePreparado;
            while (true) {
                mensaje = fEntrada.readLine();
                
                mensajePreparado = nickname + "> " + mensaje;
                
                ListaClientesSingleton.getInstance().mandarMensajeTodos(mensajePreparado);
                escribirLog(mensajePreparado);
            }
            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en el hilo del servidor" + e.getMessage());
            System.out.println(e.getStackTrace().toString());
        } finally {
        	String mensajeSalidaUsu = nickname + " -  ha salido del chat";
        	ListaClientesSingleton.getInstance().mandarMensajeTodos(mensajeSalidaUsu);
        	escribirLog(mensajeSalidaUsu);
        	ListaClientesSingleton.getInstance().removeCliente(nickname);
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
			System.out.println("Error al escribir en el fichero" + e.getMessage());
			e.printStackTrace();
		}
	}
}