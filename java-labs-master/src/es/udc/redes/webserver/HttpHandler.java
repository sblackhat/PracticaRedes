/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author querty
 */
public abstract class HttpHandler {
    
    /**
     * Handle the given request and generate an appropriate response.
     * 
     * @param request the encapsulated object that has the information to 
     * deal with the HTTP request
     * @param config Configuration of the HTTP server
     * @throws java.io.IOException
     * @throws java.lang.NullPointerException if HttpExchange is nulls
     * 
     *  
     */
    
    /**
     * Handles the HTTP request given a request and a configuration of the server
     * @param request the request encapsulated with HttpExchange
     * @param config the configuration
    */
    public abstract void handle(HttpExchange request,Configuration config);
    
    public final void log(HttpExchange request, Socket s,Configuration config){
       Headers responseHeader = request.getResponse();
       String statusCode = responseHeader.get("Code");
       String date = responseHeader.get("Date");
       String ip = s.getInetAddress().toString();
       File file = null;
       StringBuilder sb = new StringBuilder("Request : ");
       sb.append(request.getRequestMethod()).append(" ")
         .append(request.getFileRequested()).append(" ")
         .append(request.getHttpVersion())
         .append("\n")
         .append("IP : ").append(ip)
         .append("\n")
         .append("Date : ").append(date).append("\n");
       if (statusCode.startsWith("2") || statusCode.startsWith("3")){
           file = config.getAccessLog();
           sb.append("Code : ").append(responseHeader.get("Code"))
             .append("\n")
             .append("Size : ").append(responseHeader.get("Content-Length"));
       }else{
           file = config.getErrorLog();
           sb.append("Error : ").append(responseHeader.get("Code"));
       }
       writer(file,sb.toString());
    }
    /**
     * Write a file given a the data represented in a String
     * @param file the file that is going to be written
     * @param data the String representation of the data
     */
    private void writer(File file, String data) {
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}