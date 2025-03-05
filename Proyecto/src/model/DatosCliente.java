package model;

import java.io.PrintWriter;

public class DatosCliente {

	private String nickname;
	private PrintWriter salida;

	public DatosCliente(String nickname, PrintWriter salida) {
		this.nickname = nickname;
		this.salida = salida;
	}

	public String getNickname() {
		return nickname;
	}

	public PrintWriter getSalida() {
		return salida;
	}
}
