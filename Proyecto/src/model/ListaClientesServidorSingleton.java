package model;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesServidorSingleton {
    
    private static ListaClientesServidorSingleton instance;
    private List<DatosCliente> clientes;
    
    private ListaClientesServidorSingleton(){
        clientes = new ArrayList<DatosCliente>();
    }
    
    public static synchronized ListaClientesServidorSingleton getInstance(){
        if(instance == null){
            instance = new ListaClientesServidorSingleton();
        }
        return instance;
    }

    public synchronized List<DatosCliente> getClientes() {
        return clientes;
    }

    public synchronized void removeCliente(String nickname){
    	clientes.removeIf(cliente -> cliente.getNickname().equals(nickname));
    }

    public synchronized void addCliente(DatosCliente cliente) {
    	clientes.add(cliente);
    }
    
    public synchronized void mandarMensajeTodos(String mensaje){
    	for (DatosCliente datosCliente : clientes) {
            datosCliente.getSalida().println(mensaje);
            datosCliente.getSalida().flush();
        }
    }
    
    public void getCantUsu() {
    	System.out.println("Usuarios en el servidor:" + clientes.size());
    }
    
    public synchronized boolean existeNickname (String nickame) {
    	System.out.println("VALIDO ESTE NICKNAME: " + nickame);
    	System.out.println("LISTA: " + getClientes());
    	for (DatosCliente cliente : clientes) {
			if (cliente.getNickname().equals(nickame)) {
				return true;
			}
		}
    	return false;
    }

    public synchronized DatosCliente getCienteEspecifico(String username){
        for (DatosCliente datosCliente : clientes) {
            if(username.equals(datosCliente.getNickname())){
                return datosCliente;
            }
        }
        return null;
    }
    
    public synchronized void enviarMensajePrivado(String remitente, String destinatario, String mensaje) {
        for (DatosCliente cliente : clientes) {
            if (cliente.getNickname().equals(destinatario)) {
                cliente.getSalida().println("[PRIVADO de " + remitente + "]: " + mensaje);
            }
        }
        for (DatosCliente cliente : clientes) {
            if (cliente.getNickname().equals(remitente)) {
                cliente.getSalida().println("El usuario '" + destinatario + "' no está conectado.");
                return;
            }
        }
    }
    
}