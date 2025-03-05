package main;

import java.awt.EventQueue;

import controller.Controller;
import model.ClienteChat;
import view.ChatView;

public class Inicio {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				ChatView cv = new ChatView();

				ClienteChat cliente = new ClienteChat("localhost", 6969, cv);
				Controller c = new Controller(cv, cliente);

				cv.setListener(c);
				cv.hacerVisible();
			}
		});

	}

}