/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.tutorial.tcp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author svalle
 */
public class MonoThreadTcpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: TcpServer <port>");
            System.exit(-1);
        }
        ServerSocket serverSocket = null;
        try {
            // Create a server socket
            serverSocket = new ServerSocket(Integer.parseInt(argv[0]));
            // Set a timeout of 300 secs
            serverSocket.setSoTimeout(300000);
            
            
            while (true) {
                // Wait for connections
                Socket clientSocket = serverSocket.accept();
                // Set the input channel
                BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
                // Set the output channel
                PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
                // Receive the client message
                String received = in.readLine();
                System.out.println("Monothread_SERVER: Received " + received);
               
                // Send response to the client
                System.out.println("Monothread_SERVER: Sending " + received);
                out.println(received);
                // Close the streams
                out.close();
                in.close();
            }
        // Uncomment next catch clause after implementing the logic            
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
         //Close the socket
            try {
               serverSocket.close();
            } catch (IOException ex) {
               throw new RuntimeException(ex);
            }
            
        }
    }
}