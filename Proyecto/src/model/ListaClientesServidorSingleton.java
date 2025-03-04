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
        System.out.println("Cliente removido del servidor: " + nickname);
    }

    public synchronized void addCliente(DatosCliente cliente) {
    	clientes.add(cliente);
        System.out.println("Cliente añadido: " + cliente.getNickname()); 
    }
    
    public synchronized void mandarMensajeTodos(String mensaje){
    	System.out.println("Lista de clientes: " + getInstance().getClientes());
    	System.out.println("Envio mensaje a todos: " + mensaje);
    	for (DatosCliente datosCliente : clientes) {
        	System.out.println(datosCliente.toString());
            datosCliente.getSalida().println(mensaje);
            datosCliente.getSalida().flush();
        }
    }
    
    public void getCantUsu() {
    	System.out.println("Usuarios en el servidor:" + clientes.size());
    }

    public synchronized DatosCliente getCienteEspecifico(String username){
        for (DatosCliente datosCliente : clientes) {
            if(username.equals(datosCliente.getNickname())){
                return datosCliente;
            }
        }
        return null;
    }
    
}