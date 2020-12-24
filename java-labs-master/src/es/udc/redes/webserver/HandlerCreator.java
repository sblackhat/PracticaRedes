/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

/**
 * It is a factory class that creates the appropriate type of request handler
 * given a request
 * @author querty
 */
public class HandlerCreator {

    HandlerCreator() {
    }
    
    /**
     * creates the appropriate type of request handler
     * given a request
     * @param request an object of type HTTPExchange that encapsulates
     *        the request of the user
     * @return HttpHandler the correct object that holds the correct method to
     *         handle the request
     */
    public HttpHandler createHandler(HttpExchange request){
        HttpHandler handler;
        String method = request.getRequestMethod();
        String file   = request.getFileRequested();
        String http   = request.getHttpVersion();
        
        if (nullChecks(file,method,http) && isValidMethod(method) && isCorrectVersion(http)){
            switch (method) {
                case "GET":
                    handler = new GET();
                    break;
                case "HEAD":            
                    handler = new Head();
                    break;
                default:
                    handler = new NotImplemented();
                    break;
            }
        }else{
            handler = new BadRequest();
        }
    return handler;
    }
    
    /**
     * Checks if method received if a valid method in HTTP
     * @param method a String representation of the method
     * @return a boolean that is true if the method is valid
     */

    private boolean isValidMethod(String method) {
        for (Method m : Method.values()) { 
            if(method.equals(m.getValue())) return true;
        }
    return false;}
    /**
     * Checks if the file, method and the HTTP version of the request are different
     * from null, blank of empty
     * @param file the file requested by the client
     * @param method the method requested by the client
     * @param httpVersion the HTTP version of the client
     * @return a boolean variable that is true if the described condition holds
     */
    private boolean nullChecks(String file,String method, String httpVersion){
        if( file != null 
            && method != null 
            && httpVersion  != null)
            if (file.isEmpty() || isBlank(file) || method.isEmpty() || httpVersion.isEmpty() 
                || isBlank(method) || isBlank(httpVersion))
                return false;
        return true;
    }
    /**
     * Checks if the string received stars with white space points
     * @param str the String to be checked
     * @return true if the string contains only white space codepoints, otherwise false
     */ 
    private boolean isBlank(String str){
        return str.startsWith(" ") || str.startsWith("\n") || str.startsWith("\t");
    }
    /**
     * Checks if the string received is valid version of HTTP
     * @param method the String to be checked
     * @return true if the string is a valid version of HTTP, otherwise false
     */ 
    private boolean isCorrectVersion(String method){
       return method.equals("HTTP/1.1") || method.equals("HTTP/1.0") || method.equals("HTTP/2");
    }
}

