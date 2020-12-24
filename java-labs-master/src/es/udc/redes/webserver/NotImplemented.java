/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

/**
 * Not implemented
 * Implements the handle function of the HTTP handler 
 * that handles the request when the method specified is not implemented yet
 * @author querty
 */
public class NotImplemented extends HttpHandler {

    public NotImplemented() {
    }
    
    /** Implements the handle function of the HTTP handler 
     * that handles the request when the method specified is not implemented yet
     * @param request the request encapsulated with HttpExchange
     * @param config the configuration
    */
    @Override
    public void handle(HttpExchange request, Configuration config) throws NullPointerException {
        request.setState(State.NOT_IMPLEMENTED);
        request.setFileRequested("501.html");
        request.setResource(config.getHtml501());
        request.sendResponseHeaders(config);
        request.sendRequestBody();
    }
    
}
