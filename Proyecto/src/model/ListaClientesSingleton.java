package model;

import java.util.ArrayList;
import java.util.List;

import view.ChatView;

public class ListaClientesSingleton {
    
    private static ListaClientesSingleton instance;
    private static List<ClienteChat> clientes;
    
    private ListaClientesSingleton(){
        clientes = new ArrayList<ClienteChat>();
    }
    
    public static synchronized ListaClientesSingleton getInstance(){
        if(instance == null){
            instance = new ListaClientesSingleton();
        }
        return instance;
    }

    public synchronized List<ClienteChat> getClientes() {
        return clientes;
    }

    public synchronized void removeCliente(String nickname){
    	clientes.removeIf(cliente -> cliente.getNickname().equals(nickname));
        System.out.println("Cliente removido : " + nickname);
    }
    
//    public synchronized ClienteChat getCienteEspecifico(String username){
//        for (ClienteChat datosCliente : clientes) {
//            if(username.equals(datosCliente.getNickname())){
//                return datosCliente;
//            }
//        }
//        return null;
//    }
    
    public synchronized boolean existeNickname (String nickame) {
    	for (ClienteChat clienteChat : clientes) {
    		System.out.println(clienteChat);
			if (clienteChat.getNickname().equals(nickame)) {
				return true;
			}
		}
    	return false;
    }

	public synchronized ClienteChat addCliente(String host, int puerto, String nickname, ChatView cv) {
		ClienteChat cliente = new ClienteChat(host, puerto, nickname, cv);
    	clientes.add(cliente);
        System.out.println("Cliente añadido App: " + cliente.getNickname());
        return cliente;
	}
    
}