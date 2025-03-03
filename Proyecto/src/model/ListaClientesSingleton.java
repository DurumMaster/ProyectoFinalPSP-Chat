package model;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesSingleton {
    
    private static ListaClientesSingleton instance;
    private List<Cliente> clientes;
    
    private ListaClientesSingleton(){
        clientes = new ArrayList<Cliente>();
    }
    
    public static synchronized ListaClientesSingleton getInstance(){
        if(instance == null){
            instance = new ListaClientesSingleton();
        }
        return instance;
    }

    public synchronized void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public synchronized List<Cliente> getClientes() {
        return clientes;
    }

    public synchronized void removeCliente(Cliente dc){
    	System.out.println("Cliente eliminado: " + dc.getNickname());
        clientes.remove(dc);
    }

    public synchronized void addCliente(Cliente dc){
    	System.out.println("Cliente añadido: " + dc.getNickname());
        clientes.add(dc);
    }

    public synchronized void mandarMensajeTodos(String mensaje){
        for (Cliente datosCliente : clientes) {
            datosCliente.getfSalida().println(mensaje);
            datosCliente.getfSalida().flush();
        }
    }

    public synchronized Cliente getCienteEspecifico(String username){
        for (Cliente datosCliente : clientes) {
            if(username.equals(datosCliente.getNickname())){
                return datosCliente;
            }
        }
        return null;
    }
    
}