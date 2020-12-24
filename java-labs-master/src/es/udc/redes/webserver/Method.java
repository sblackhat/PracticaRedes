/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

/**
 * Enumeration that has all the possible HTTP methods 
 * @author querty
 */
enum Method{
    GET("GET"),
    HEAD("HEAD"),
    PUT("PUT"),
    POST("POST"),
    OPTIONS("OPTIONS"),
    DELETE("DELETE");
        
    private final String value;
        
    Method(String value) {
            this.value = value;
    }
    
    /**
     * Get the value associated in the enumeration
     * @return String value associated in the enumeration
     */
    public String getValue() {
       return this.value;
    }
    
    }
    