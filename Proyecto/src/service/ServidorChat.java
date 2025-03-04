package service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {
	
    public static void main(String[] args) {
		ServerSocket servidor = null;
        Socket cliente = null;
        
		try {
			servidor = new ServerSocket(6969);
			System.out.println("Servidor Iniciado...");
			
			while(true) {
				cliente = servidor.accept();
				HiloServidorChat hilo = new HiloServidorChat(cliente);
				hilo.start();
			}
			
		} catch (IOException e) {
			resetChatLog();
			e.printStackTrace();
		}finally{
            try {
                if(servidor != null){
                    servidor.close();
                }
                if (cliente != null) {
                    cliente.close();
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
	
	}

	private static void resetChatLog() {
		System.out.println("ME VA A BORRAR EL LOG");
	    try (FileWriter writer = new FileWriter("chatlog.txt", false)) {
	    	writer.flush();
	    	writer.close();
	    } catch (IOException e) {
	        System.out.println("Error limpiar chatlog");
	    }
	}

}