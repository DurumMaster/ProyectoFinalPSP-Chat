package model;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesServidorSingleton {

	private static ListaClientesServidorSingleton instance;
	private List<DatosCliente> clientes;

	private ListaClientesServidorSingleton() {
		clientes = new ArrayList<DatosCliente>();
	}

	public static synchronized ListaClientesServidorSingleton getInstance() {
		if (instance == null) {
			instance = new ListaClientesServidorSingleton();
		}
		return instance;
	}

	public synchronized void removeCliente(String nickname) {
		clientes.removeIf(cliente -> cliente.getNickname().equals(nickname));
	}

	public synchronized void addCliente(DatosCliente cliente) {
		clientes.add(cliente);
	}

	public synchronized void mandarMensajeTodos(String mensaje) {
		for (DatosCliente datosCliente : clientes) {
			datosCliente.getSalida().println(mensaje);
			datosCliente.getSalida().flush();
		}
	}

	public int getCantUsu() {
		int cant = clientes.size();
		System.out.println("Usuarios en el servidor:" + cant);
		return cant;
	}

	public synchronized boolean existeNickname(String nickame) {
		for (DatosCliente cliente : clientes) {
			if (cliente.getNickname().equals(nickame)) {
				return true;
			}
		}
		return false;
	}

	public synchronized void enviarMensajePrivado(String remitente, String destinatario, String mensaje) {
		for (DatosCliente cliente : clientes) {
			if (cliente.getNickname().equals(destinatario)) {
				cliente.getSalida().println("[PRIVADO de " + remitente + "]: " + mensaje);
			}
		}
	}

	public synchronized void mandarCantidadUsuarios(int cantidad) {
		for (DatosCliente cliente : clientes) {
			cliente.getSalida().println("CONECTADOS:" + cantidad);
		}
	}

	public synchronized void mandarListaUsuarios() {
		List<String> nicknames = new ArrayList<>();
		for (DatosCliente cliente : clientes) {
			nicknames.add(cliente.getNickname());
		}
		String listaUsuarios = "USUARIOS:" + String.join(",", nicknames);

		for (DatosCliente cliente : clientes) {
			cliente.getSalida().println(listaUsuarios);
		}
	}

}