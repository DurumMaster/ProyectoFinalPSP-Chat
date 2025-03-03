package model;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesSingleton {
    
    private static ListaClientesSingleton instance;
    private List<DatosCliente> clientes;
    
    private ListaClientesSingleton(){
        clientes = new ArrayList<DatosCliente>();
    }
    
    public static synchronized ListaClientesSingleton getInstance(){
        if(instance == null){
            instance = new ListaClientesSingleton();
        }
        return instance;
    }

    public synchronized void setClientes(List<DatosCliente> clientes) {
        this.clientes = clientes;
    }

    public synchronized List<DatosCliente> getClientes() {
        return clientes;
    }

    public synchronized void removeCliente(DatosCliente dc){
    	System.out.println("Cliente eliminado: " + dc.getNickname());
        clientes.remove(dc);
    }

    public synchronized void addCliente(DatosCliente dc){
    	System.out.println("Cliente añadido: " + dc.getNickname());
        clientes.add(dc);
    }

    public synchronized void mandarMensajeTodos(String mensaje){
        for (DatosCliente datosCliente : clientes) {
            datosCliente.getSalida().println(mensaje);
        }
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