/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;
/**
 * Main class that executes the server
 * @author querty
 */
public class WebServer {
    
    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.start();
    }
}
