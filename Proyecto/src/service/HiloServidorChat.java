package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.DatosCliente;
import model.ListaClientesServidorSingleton;

public class HiloServidorChat extends Thread {

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
            ListaClientesServidorSingleton.getInstance().addCliente(clienteServidor);
            ListaClientesServidorSingleton.getInstance().getCantUsu();
            
            String mensajeEntradaUsu = nickname + " - Ha entrado en el chat";
            ListaClientesServidorSingleton.getInstance().mandarMensajeTodos(mensajeEntradaUsu);
            escribirLog(mensajeEntradaUsu);
            
            String mensaje, mensajePreparado;
            while (true) {
                mensaje = fEntrada.readLine();
                
                mensajePreparado = nickname + "> " + mensaje;
                
                ListaClientesServidorSingleton.getInstance().mandarMensajeTodos(mensajePreparado);
                escribirLog(mensajePreparado);
            }
            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en el hilo del servidor" + e.getMessage());
            System.out.println(e.getStackTrace().toString());
        } finally {
        	String mensajeSalidaUsu = nickname + " -  ha salido del chat";
        	ListaClientesServidorSingleton.getInstance().mandarMensajeTodos(mensajeSalidaUsu);
        	escribirLog(mensajeSalidaUsu);
        	ListaClientesServidorSingleton.getInstance().removeCliente(nickname);
        	ListaClientesServidorSingleton.getInstance().getCantUsu();
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