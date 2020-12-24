package es.udc.redes.tutorial.udp.server;

import java.net.*;

/**
 * Implements an UDP Echo Server.
 */
public class UdpServer {

    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Format: es.udc.redes.tutorial.udp.server.UdpServer <port_number>");
            System.exit(-1);
        }
        
        DatagramSocket serverDatagram = null;
        try {
            // Create a server socket
            serverDatagram = new DatagramSocket(Integer.parseInt(argv[0]));
            // Set max. timeout to 300 secs
            serverDatagram.setSoTimeout(300000);
            byte[] packet = new byte[1024];
            
            while (true) {
                // Prepare datagram for reception
                DatagramPacket received = new DatagramPacket(packet,packet.length);
                // Receive the message
                serverDatagram.receive(received);
                //Print the received datagram 
                StringBuilder sb = new StringBuilder("SERVER : Received : ");
                sb.append(new String(received.getData())).append(" length : ")
                  .append(received.getLength()).append(" from ")
                  .append(received.getAddress().toString())
                  .append(" : ").append(received.getPort());
                System.out.println(sb.toString());
                System.out.println("\n" + received.getPort());
                // Prepare datagram to send response
                DatagramPacket response = new DatagramPacket(received.getData(),
                received.getData().length,received.getAddress(),received.getPort());
                // Send response
                serverDatagram.send(response);
                StringBuilder s = new StringBuilder("SERVER : Sending ");
                s.append(new String(response.getData())).append(" to ")
                .append(response.getAddress().toString()).append(" : ")
                .append(response.getPort());  
                System.out.println(s.toString());
            }
          
        // Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
           System.err.println("No requests received in 300 secs ");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
        // Close the socket
            if (serverDatagram != null)
                serverDatagram.close();
        }
    }
}
