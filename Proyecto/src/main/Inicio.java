package main;

import java.awt.EventQueue;

import controller.Controller;
import model.Cliente;
import model.ListaClientesSingleton;
import view.ChatView;

public class Inicio {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				ChatView cv = new ChatView();
				
				String nickname = cv.solicitarNickname();
				
				Cliente cliente = new Cliente("localhost", 6969, nickname, cv);					
				ListaClientesSingleton.getInstance().addCliente(cliente);
				
				Controller c = new Controller(cv, cliente);
				
				cv.setListener(c);
				cv.hacerVisible();
				
				cliente.leer = true;
				
			}
		});
		
	}

}
