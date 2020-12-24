/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Encapsulates each thread of the server in this object
 * It is in charge of the comunication process
 * @author querty
 */
public class ConnectionHandler extends Thread{
    
    private final Socket socket;
    private final Configuration config;
    private final HandlerCreator factory;
    
    
    public ConnectionHandler(Socket s,Configuration config) {
    
        // Store the socket s
        socket = s;
        this.config = config;
        this.factory = new HandlerCreator();
    }
    
    @Override
    public void run() {
        try {
                // Set the input channel
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // Set the output channel
                OutputStream out = socket.getOutputStream();
               //Set the data output channel
                BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream());
                // Encapsulate the request
                HttpExchange request = new HttpExchange(in,out,dataOut);
                //Create the appropiate method according to the request
                HttpHandler handler = factory.createHandler(request);
                //Handle the request
                handler.handle(request,config);
                handler.log(request,socket,config);
                // Close the streams
                try {
                    in.close();
                    this.socket.close();
                    out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
                
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }catch(NullPointerException e){
            
        }
    }
}
