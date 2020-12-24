package es.udc.redes.tutorial.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * MultiThread TCP echo server.
 */
public class TcpServer {
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
                // Create a ServerThread object, with the new connection as parameter
                ServerThread sThread = new ServerThread(clientSocket);
                // Initiate thread using the start() method
                sThread.start();
            }
        // Uncomment next catch clause after implementing the logic            
        } catch (SocketTimeoutException e) {
            System.err.println("Nothing received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
        //Close the socket
            try { serverSocket.close(); }
            catch (IOException ex) { throw new RuntimeException(ex); }
        }
    
    }


}