/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** The object that holds the configuration of the web server
 *
 * @author querty
 */
public class Configuration {
    private static final String PROP_FILENAME = "config.properties"; 
   
    private final String NAME;
    private final int PORT_NUMBER;
    private final String DIRECTORY_INDEX;
    private final String DIRECTORY;
    private final boolean ALLOW;
    private final String HTTPVersion;
    private final File html400;
    private final File html404;
    private final File html403;
    private final File html501;
    private final String IP;
    private final File accessLog;
    private final File errorLog;
    
    /**
     * Get the port number of the web server
     * @return an integer that represents the port number of the web sever
     */

    int getPORT_NUMBER() {
        return PORT_NUMBER;
    }
    
    /**
     * Get the index file of the web server
     * @return a String representation of the index file of the server
     */
    String getDIRECTORY_INDEX() {
        return DIRECTORY_INDEX;
    }
    /**
     * Get the root path of the web server
     * @return a String representation of the root directory of the server
     */
 
    String getDIRECTORY() {
        return DIRECTORY;
    }
     /** Get the boolean value of the allow directive
      * @return the boolean value of the allow directive
     */
    boolean isALLOW() {
        return ALLOW;
    }
     /** Get the HTTP version of the web server
      * @return a String representation of the HTTP version of the web server
     */

    String getHTTPVersion() {
        return HTTPVersion;
    }
     /** Get the file that server send as a response 
      * when the status code is 400
      * @return a HTML File for the status code 501
     */
    File getHtml400() {
        return html400;
    }
     /** Get the file that server send as a response 
      * when the status code is 404
      * @return a HTML File for the status code 501
     */
    File getHtml404() {
        return html404;
    }
     /** Get the file that server send as a response 
      * when the status code is 403
      * @return a HTML File for the status code 403
     */
    File getHtml403() {
        return html403;
    }
    
     /** Get the file that server send as a response 
      * when the status code is 501
      * @return a HTML File for the status code 501
     */
    File getHtml501() {
        return html501;
    }
    
    /** Get the current name of the web server
     * @return a String representation of the name
     */
    String getNAME() {
        return NAME;
    }
    /** Get the current IP of the web server
     * @return a String representation of the IP
     */
    String getIP() {
        return IP;
    }
    
    /**
     * Get the access log file
     * @return the file that log the requests that generate a valid response
     * (2XX) and (3XX)
     */
    public File getAccessLog() {
        return accessLog;
    }
    /**
     * Get the error log file
     * @return the file that log the requests that generate an error response
     * (4XX) and (5XX)
     */
    public File getErrorLog() {
        return errorLog;
    }
    
    /**
     * Create the configuration given a config.properties file
     * @throws IOException if it cannot read the configuration file
     */
           
    Configuration() throws FileNotFoundException, IOException {
      FileInputStream in = null;
            Properties prop = new Properties();
            String defaultDir       = System.getProperty("user.dir") + "/p1/resources/";
            //Read the configuration file 
            in = new FileInputStream(defaultDir + Configuration.PROP_FILENAME);
            //Check if the file exists
            if (in != null){
                prop.load(in);
            }
            
            //Assignations
            this.NAME               = prop.getProperty("NAME","HTTP Server").trim();
            this.ALLOW              = prop.getProperty("ALLOW","false").equals("true");
            this.PORT_NUMBER        = Integer.parseInt(prop.getProperty("PORT_NUMBER","5000"));
            this.DIRECTORY_INDEX    = prop.getProperty("DIRECTORY_INDEX");
            this.DIRECTORY          = prop.getProperty("DIRECTORY",defaultDir);
            this.HTTPVersion        = prop.getProperty("VersionHTTP","HTTP/1.0");
            this.html400            = new File(defaultDir + prop.getProperty("html400",defaultDir + "html400.hmtl").trim());
            this.html403            = new File(defaultDir + prop.getProperty("html403",defaultDir + "html403.hmtl").trim());
            this.html404            = new File(defaultDir + prop.getProperty("html404",defaultDir + "html404.hmtl").trim());
            this.html501            = new File(defaultDir + prop.getProperty("html501",defaultDir + "html501.hmtl").trim());
            this.IP                 = prop.getProperty("IP","localhost");
            this.accessLog          = new File(prop.getProperty("accessLog",defaultDir + "accessLog.txt").trim());
            this.errorLog           = new File(prop.getProperty("errorLog",defaultDir + "errorLog.txt").trim());
        try{
            if (in != null){
                in.close();}
        }catch(IOException e){
            e.printStackTrace();
            }
        
        }  
    
}


