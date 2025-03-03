package service;

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
				cliente = new Socket();
				cliente = servidor.accept();
				
				HiloServidorChat HS = new HiloServidorChat(cliente);
				HS.start();
			}
			
		} catch (IOException e) {
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

}