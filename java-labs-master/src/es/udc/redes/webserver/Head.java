/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**Implements the handle function of the HTTP handler 
 * that handles the request when its method is a HEAD
 * @author querty
 */
public class Head extends HttpHandler{
    /** Implements the handle function of the HTTP handler 
     * that handles the request when its method is a HEAD
     * @param request the request encapsulated with HttpExchange
     * @param config the configuration
    */    
    @Override
    public void handle(HttpExchange request, Configuration config){
       String requestedFile = request.getFileRequested();
        File resource = new File(config.getDIRECTORY() + requestedFile);
        
         if(resource.exists() && resource.isFile()){
            request.setResource(resource);
            if(Modified(request,resource)){
                request.setState(State.OK);
                request.sendResponseHeaders(config);
                             
            }else{
                request.setState(State.NOT_MODIFIED);
                request.sendResponseHeaders(config);
            }
            
        }else{
            if(requestedFile.trim().equals("/")){
                    resource = new File(config.getDIRECTORY() + "/" + config.getDIRECTORY_INDEX());
                    request.setFileRequested(config.getDIRECTORY_INDEX());
                    if(Modified(request,resource)){
                        request.setState(State.OK);
                        request.setResource(resource);
                        request.sendResponseHeaders(config);
                        
                    }else{
                        request.setState(State.NOT_MODIFIED);
                        request.sendResponseHeaders(config);
                    }
            }else if(resource.isDirectory()) {
                if(config.isALLOW()){
                    request.setState(State.OK);
                    request.setResource(null);
                    request.sendResponseHeaders(config);
                }else{
                    request.setState(State.FORBIDDEN);
                    request.setFileRequested("403.html");
                    request.setResource(config.getHtml403());
                    request.sendResponseHeaders(config);
                    
                }   
            }else{
                request.setState(State.NOT_FOUND);
                request.setFileRequested("404.html");
                request.setResource(config.getHtml404());
                request.sendResponseHeaders(config);
                
            }
        }
    }
    
    private boolean Modified(HttpExchange request, File resource){
        Date server = null;
        Date clientDate = null;
        try{  
           Headers hd = request.getRequestHeader();
           String s = hd.get("If-Modified-Since");
           if (s != null){
            server = new Date(resource.lastModified());
            SimpleDateFormat dateFormat = new SimpleDateFormat(
            "EEE MMM dd HH:mm:ss zzz yyyy",Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            clientDate = dateFormat.parse(s);
            return server.after(clientDate);
           }else{
               return true;
           }
        }catch(ParseException e){
            System.out.println("Invalid date recibed by the client");
            throw new RuntimeException();
        }
        }

    }
    

