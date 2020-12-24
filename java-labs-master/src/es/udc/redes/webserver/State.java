/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

/**
 * Enumeration that holds the different status codes implemented
 * @author querty
 */
enum State {
    /**1XX status code
    *This codes are related to informational response
    */
    CONTINUE("100 Continue"),
    SWITCHING_PROTOCOLS("101 Switching Protocols"),
    PROCESSING("102 Processing"),
    /**
     * 2xx STATUS CODES
    * This class of status codes indicates the action 
    * requested by the client was received, understood, and accepted.
    */
    OK("200 OK"), 
    /**
     * 3XX STATUS CODES
     * This class of status code indicates 
     * the client must take additional action to complete the request
     */
    NOT_MODIFIED("304 Not Modified"),
    /**
     * 4XX STATUS CODES
     * This class of status code is intended for situations 
     * in which the error seems to have been caused by the client
     * 
     */
    BAD_REQUEST("400 Bad Request"), 
    FORBIDDEN("403 Forbidden"),
    NOT_FOUND("404 Not Found"),
    /**
     * 5xx STATUS CODES
     * Response status codes beginning with the digit "5" indicate cases 
     * in which the server is aware that 
     * it has encountered an error or is otherwise incapable of performing the request.
     */
    NOT_IMPLEMENTED("501 Not Implemented");
        
    private final String value;
        
    State(String value) {
            this.value = value;
    }
        
    public String getValue() {
       return this.value;
    }
}
