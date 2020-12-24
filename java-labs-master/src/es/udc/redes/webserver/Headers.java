/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP request and response headers are represented by this class which 
 * implements the interface Map<String, String>. The keys are case-insensitive 
 * Strings representing the header names and the value associated with each key 
 * is a String with one element for each occurrence of the header 
 * name in the request or response.
 * @author querty
 */
public class Headers {
    private Map<String,String> headers;
    
    /**
     * Add a new value associated with the key to the header
     * @param key a String representation of the key
     * @param value a String representation of the value 
     */
    void add(String key, String value){
        headers.put(key, value);
    }
    /**
     * returns the value from the Header values for the given key
     * @param key - the key to search for
     * @return string value associated with the key
     */
    String get(String key){
        return headers.get(key);}
    boolean containsKey(String key){
        return headers.containsKey(key);
    }    
    /**
     * sets the given value as the sole header value for the given key.
     * @param key - the header name
     * @param value - the header value to set.
     */
    void set(String key, String value){
        headers.put(key, value);
    }
    
    Headers() {
        this.headers = new HashMap<>();
    }
    
}
