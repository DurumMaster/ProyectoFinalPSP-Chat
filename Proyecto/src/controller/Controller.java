package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.ChatView;

public class Controller implements ActionListener {

	ChatView cv;
	public Controller(ChatView cv) {
		this.cv = cv;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			if (e.getSource().equals(cv.getBtnEnviar())) {
				
			}
		}
	}
	
	
}
