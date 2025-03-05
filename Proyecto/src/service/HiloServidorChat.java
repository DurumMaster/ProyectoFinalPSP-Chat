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

            while (ListaClientesServidorSingleton.getInstance().existeNickname(nickname)) {
                fSalida.println("Este nickname ya está en uso. Prueba con otro:");
                nickname = fEntrada.readLine();
            }
            
            DatosCliente clienteServidor = new DatosCliente(nickname, socket, fSalida);
            ListaClientesServidorSingleton.getInstance().addCliente(clienteServidor);
            ListaClientesServidorSingleton.getInstance().getCantUsu();
            
            String mensajeEntradaUsu = nickname + " - Ha entrado en el chat";
            ListaClientesServidorSingleton.getInstance().mandarMensajeTodos(mensajeEntradaUsu);
            escribirLog(mensajeEntradaUsu);
            
            String mensaje;
            while ((mensaje = fEntrada.readLine()) != null) {
            	if (mensaje.startsWith("/privado")) {
                    String[] partes = mensaje.split(" ", 3);
                    if (partes.length == 3) {
                        String destinatario = partes[1];
                        String contenido = partes[2];
                        ListaClientesServidorSingleton.getInstance().enviarMensajePrivado(nickname, destinatario, contenido);
                    } else {
                        fSalida.println("Formato incorrecto. Usa: /privado <nickname> <mensaje>");
                    }
                } else {
                    String mensajeFormateado = nickname + "> " + mensaje;
                    ListaClientesServidorSingleton.getInstance().mandarMensajeTodos(mensajeFormateado);
                    escribirLog(mensajeFormateado);
                }
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