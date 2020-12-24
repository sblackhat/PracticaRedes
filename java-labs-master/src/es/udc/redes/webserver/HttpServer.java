/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * This class implements a simple HTTP server. A HttpServer is bound to an IP 
 * address and port number and listens for incoming TCP connections from clients 
 * on this address. The configuration is stored in the configuration object. 
 * @author querty
 */
public class HttpServer {
    private ServerSocket serverSocket;
    private Configuration configuration;

    public HttpServer() {
        try {
            //Set the configuration of the server
             this.configuration = new Configuration();
            // Create a server socket
            this.serverSocket = new ServerSocket(configuration.getPORT_NUMBER());        
            
        }catch(IOException e ){
            System.out.println("Error : " + e.getMessage());
        }
    }
    /**
     * Function that starts the HTTP server
     */
    public void start(){
        try{
        while (true) {
                // Wait for connections
                Socket clientSocket = serverSocket.accept();
                // Create a ServerThread object, with the new connection as parameter
                ConnectionHandler sThread = new ConnectionHandler(clientSocket,configuration);
                // Initiate thread using the start() method
                sThread.start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
        //Close the socket
            try { serverSocket.close(); }
            catch (IOException ex) { throw new RuntimeException(ex); }
        }
    }


}