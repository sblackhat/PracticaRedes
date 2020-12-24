
package es.udc.redes.tutorial.tcp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 *
 * @author svalle
 */

public class ServerThread extends Thread{
    
    private Socket socket;
    
    public ServerThread(Socket s) {
    
        // Store the socket s
        socket = s;
    }
    
    @Override
    public void run() {
        try {
                // Set the input channel
                BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
                // Set the output channel
                PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true);
                // Receive the client message
                String received = in.readLine();
                System.out.println("SERVER: Received " + received);
               
                // Send response to the client
                System.out.println("SERVER: Sending " + received);
                out.println(received);
                // Close the streams
                out.close();
                in.close();
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
        // Close the socket
        }
    }

}