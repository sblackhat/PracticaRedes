/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

/**
 * Bad Request 404 
 * @author Sergio Valle Trillo
 * @version 1.0
 */
public class BadRequest extends HttpHandler {
    
    public BadRequest() {
    }
    
    /** Implements the handle function of the HTTP handler 
     * that handles the request when it is not valid
     * @param request the request encapsulated with HttpExchange
     * @param config the configuration
    */
    @Override
    public void handle(HttpExchange request, Configuration config){
        request.setState(State.BAD_REQUEST);
        request.setFileRequested("html400.html");
        request.setResource(config.getHtml400());
        request.sendResponseHeaders(config);
        request.sendRequestBody();
    }
    
}
