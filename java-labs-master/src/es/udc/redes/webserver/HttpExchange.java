/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * This class encapsulates a HTTP request received and a response to be generated 
 * in one exchange. It provides methods for examining the request from the client, 
 * and for building and sending the response.
 *  1- request.getRequestMethod() 
 *  2- request.getFileRequested()
 *  3- request.setState() to set the State of the request
 *  4- request.setResource() to set the resource of the request (if it has to be modified)
 *  5- request.sendResponseHeaders(); to send the response headers
 *  6- request.sendRequestBody(); to send the response body
 * @author querty
 */
public class HttpExchange {
    
    private final BufferedReader in;
    private final String method;
    private String fileRequested;
    private final String httpVersion;
    private final Headers request  = new Headers();
    private final OutputStream out;
    private final Headers response = new Headers();
    private final BufferedOutputStream dataOut;
    private State state;
    private File resource;
    
    /**
     * Checks if the string received stars with white space points
     * @param str the String to be checked
     * @return true if the string contains only white space codepoints, otherwise false
     */ 
    private boolean isBlank(String str){
        return str.startsWith(" ") || str.startsWith("\n") || str.startsWith("\t");
    }
    /**
     * Construct the request headers that are encapsulated in the Header class
     * @throws IOException if the BufferedReader in is null 
     */
    private void constructRequestHeaders() throws IOException{
        String sb = in.readLine();
        if (sb != null){
        while(!sb.isEmpty() && !isBlank(sb)){
            String str[] = sb.split(": ",2);
            request.add(str[0], str[1]);
            sb = in.readLine();
            }
        }
    }
    

    public HttpExchange(BufferedReader in,OutputStream out,BufferedOutputStream dataOut) throws IOException,NullPointerException {
        this.in = in;
        StringTokenizer parse = new StringTokenizer(in.readLine());
        this.method = parse.nextToken().toUpperCase();
        this.fileRequested = parse.nextToken();
        this.httpVersion = parse.nextToken();
        this.constructRequestHeaders();
        this.out = out;
        this.dataOut = dataOut;
        
    }    
    /**
     * Sets the name of the file requested
     * @param fileRequested a String representation of the file requested
     */
    void setFileRequested(String fileRequested) {
        this.fileRequested = fileRequested;
    }
    /**
     * Sets the file requested
     * @param fileRequested The file requested
     */
    void setResource(File resource) {
        this.resource = resource;
    }
    /**
     * Sets the state of the request
     * @param state the status code of the request encapsulated in the State enum
     */
    void setState(State state) {
        this.state = state;
    }
    /**
     * Get the file requested
     * @return resource The file requested
     */
    File getResource() {
        return resource;
    }
    /**
     * Get the HTTP version of the client
     * @return a String representation of the HTTP version of the request
     */
    String getHttpVersion() {
        return httpVersion;
    }
    /**
     * Get the method requested by the client
     * @return a String representation of the method requested by the client
     */
    String getRequestMethod(){ 
    return method;}
    /**
     * Get the file requested by the client
     * @return a String representation of file requested by the client
     */
    String getFileRequested() {
        return fileRequested;
    }
    /**
     * Get the Header requested by the client
     * @return a Header of the request of the client
     */
    Headers getRequestHeader(){
        return request;
    }
    /**
     * Get the Header response 
     * @return a Header of the response
     */
    Headers getResponse() {
        return response;
    }
    
    /**
     * Send the response headers to the client given the configuration of the server
     * @param config the configuration of the web server
     */
    void sendResponseHeaders(Configuration config){
        PrintWriter pr = new PrintWriter(out, true);
        StringBuilder sb = new StringBuilder();
        
        sb.append(config.getHTTPVersion()).append(" ").append(state.getValue());
        String time = getServerTime();
        //Add values to the response header
        response.add("Code",state.getValue());
        response.add("Date",time);
        
        sb.append("\n")
          .append("Server: ").append(config.getNAME())
          .append("\n").append("Date: ").append(time);
        
        if(resource != null){
            String size = Long.toString(resource.length());
            sb.append("\n")
            .append("Content-Type: ").append(typeOfContent())
            .append("\n")
            .append("Content-Length: ").append(size);
            response.add("Content-Length", size);
        }
        
        if (resource != null){
            sb.append("\n")
            .append("Last-Modified: ").append(new Date(resource.lastModified()));
            }
        
        //Send the headers
        pr.println(sb.toString());
        // Send blank line
        pr.println();
        
    }
    /**
     * Send the response body to the client given the configuration of the server
     */
   void sendRequestBody(){       
       try {
           int fileLenght = (int) resource.length();
           byte[] fileData = readFileData(resource,fileLenght);
           dataOut.write(fileData, 0, fileLenght);
           dataOut.flush();
           
        } catch (IOException e) {
            throw new RuntimeException(e);
        
        } finally {
                try {
                    in.close();
                    out.close();
                    dataOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }
   /**
    * Send the response body to the client given the configuration of the server
    * 
    * @param data the byte[] representation of the request body
    */
   void sendRequestBody(byte[] data){
       try {
           dataOut.write(data);
           dataOut.flush();
           
        } catch (IOException e) {
            throw new RuntimeException(e);
        
        } finally {
                try {
                    in.close();
                    out.close();
                    dataOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
   }
    /**
     * Resolves the type of content of the resource and return a String that specifies
     * the type of resource
     * @return a String representation of the type of content of the resource
     */
    private String typeOfContent() {
    String type, extension = fileRequested.substring(fileRequested.lastIndexOf(".") + 1);
        
        switch (extension) {
            case "html":
                type = "text/html";
                break;
            case "txt":
                type = "text/plain";
                break;
            case "gif":
                type = "image/gif";
                break;
            case "jpg":
                type = "image/jpeg";
                break;
            case "htm":
                type = "text/html";
                break;
            case "jpeg":
                type = "image/jpeg";
                break;
            case "png":
                type = "image/png";
                break;
            default:
                type = "application/octet-stream";
        }
        
        return type;
    }
    /**
     * Return the date of the server in a specified format 
     * "EEE, d MMM yyyy HH:mm:ss z" Wed, 4 Jul 2001 12:08:56 GMT
     * @return the date of the server in a specific format
     */
    private String getServerTime() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat(
        "EEE, dd MMM yyyy HH:mm:ss z",Locale.ENGLISH);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    return dateFormat.format(calendar.getTime());
    }
    /**
     * Reads the data of the file and returns the date in byte[] format 
     * @param file the file that is going to be read
     * @param fileLength the length of the file in bytes
     * @return the data of the file
     * @throws IOException if it cannot read the file
     */
    private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
}
