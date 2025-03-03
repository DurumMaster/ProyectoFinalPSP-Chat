package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteChat{

	private String nickname;
	private String mensaje;
	
    public ClienteChat() {
        String host = "localhost";
        int puerto = 6969;
        
        PrintWriter fSalida = null;
        BufferedReader fEntrada = null;
        Socket cliente = null;
        BufferedReader in = null;
        
		try {
			cliente = new Socket(host, puerto);
            
			fSalida = new PrintWriter(cliente.getOutputStream(), true);
			fEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            			
			String mensajeUsu, mensaje;
			
            System.out.print("Introduzca su nickname: ");
            fSalida.println(nickname);

			while (true) {
				//fSalida.println(this.mensaje);
				mensaje = fEntrada.readLine();
				System.out.println(mensaje);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("Finalizado envío de cadenas al servidor");
				if(cliente != null) {
					cliente.close();
				}
				if(fSalida != null) {
					fSalida.close();
				}
				if(fEntrada != null) {
					fEntrada.close();
				}				
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
    }
    
    public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
    public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}