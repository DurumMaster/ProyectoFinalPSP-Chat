package model;

import java.io.PrintWriter;
import java.net.Socket;

public class DatosCliente {
    
    private String nickname;
    private Socket cliente;
    private PrintWriter salida;

    public DatosCliente(String nickname, Socket cliente, PrintWriter salida){
        this.nickname = nickname;
        this.cliente = cliente;
        this.salida = salida;
    }

    public Socket getCliente() {
        return cliente;
    }

    public String getNickname() {
        return nickname;
    }

    public PrintWriter getSalida() {
        return salida;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSalida(PrintWriter salida) {
        this.salida = salida;
    }
}
