package main;

import java.awt.EventQueue;

import controller.Controller;
import view.ChatView;

public class Inicio {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				ChatView cv = new ChatView();
				Controller c = new Controller(cv);
				
				cv.setListener(c);
				
				cv.hacerVisible();
			}
		});
	}

}
