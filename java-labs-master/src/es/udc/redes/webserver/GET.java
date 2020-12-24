package es.udc.redes.webserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/** Holds the implementation of the GET method
 *
 * @author querty
 */
public class GET extends HttpHandler{
    
    /** Implements the handle function of the HTTP handler 
     * that handles the request when its method is a GET
     * @param request the request encapsulated with HttpExchange
     * @param config the configuration
    */

    @Override
    public void handle(HttpExchange request, Configuration config){
        String requestedFile = request.getFileRequested();
        File resource = new File(config.getDIRECTORY() + requestedFile);
        
        if(requestedFile.contains(".do")){
            javaAplet(request,config);
        }else{
        
        if(resource.exists() && resource.isFile()){
            request.setResource(resource);
            if(Modified(request,resource)){
                request.setState(State.OK);
                request.sendResponseHeaders(config);
                request.sendRequestBody();             
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
                        request.sendRequestBody();
                    }else{
                        request.setState(State.NOT_MODIFIED);
                        request.sendResponseHeaders(config);
                    }
            }else if(resource.isDirectory()) {
                
                if(config.isALLOW()){
                    StringBuilder URL = new StringBuilder("http://");
                    URL.append(config.getIP())
                       .append(":").append(config.getPORT_NUMBER())
                       .append(requestedFile);
                    if (!requestedFile.endsWith("/")) URL.append("/");
                    String html = link(resource.list(), URL.toString());
                    request.setState(State.OK);
                    request.setResource(null);
                    request.sendResponseHeaders(config);
                    request.sendRequestBody(html.getBytes());
                }else{
                    request.setState(State.FORBIDDEN);
                    request.setFileRequested("403.html");
                    request.setResource(config.getHtml403());
                    request.sendResponseHeaders(config);
                    request.sendRequestBody();
                }   
            }else{
                request.setState(State.NOT_FOUND);
                request.setFileRequested("html404.html");
                request.setResource(config.getHtml404());
                request.sendResponseHeaders(config);
                request.sendRequestBody();
            }
        }
        }
    }
    /**
     * Checks if the resource has been modified
     * @param request the user's request
     * @param resource the resource we want to check
     * @return false if it has not been modified, otherwise true
     */
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
    /**
     * Create a dynamic HTML listing the directories given a resource
     * @param files files that it has to link
     * @param URL URL of the request
     * @return HTML document with the links
     */
    private String link(String[] files, String URL) {
        StringBuilder links = new StringBuilder();
        
        links.append("<html>")
             .append("<head></head>")
             .append("<body>")
             .append("<ul>");
        
        for (String file : files) {
            links.append("<li>").append("<a href=\"").append(URL)
                 .append(file).append("\">").append(file).append("</a>")
                 .append("</li>");
        }
        
        links.append("</ul></body></html>");
    return links.toString();}
   /**
    * Links the dynamic request to the specific java aplet
    * @param request request of the client
    * @param config configuration file
    */ 
   private void javaAplet(HttpExchange request, Configuration config){
        int i;
        Map<String,String> parameters = new HashMap();
        String file = request.getFileRequested();
        String aplet = new String("es.udc.redes.webserver.");
        aplet += file.substring(file.lastIndexOf("/")+1,file.indexOf(".do?"));
        String rest = file.substring(file.indexOf(".do?")+4,file.length());
        String[] str = rest.split("&");
        for(i = 0; i < str.length;i++){
            String s[] = str[i].split("=");
            if(s.length == 0){
                 parameters.put("", "");
            }else if (s.length == 1){
                parameters.put(s[0], "");
            }else{
                parameters.put(s[0], s[1]);   
                 }
        }
       try{
        String response = ServerUtils.processDynRequest(aplet, parameters);
        request.setState(State.OK);
        request.setResource(null);
        request.sendResponseHeaders(config);
        request.sendRequestBody(response.getBytes());
       }catch(Exception e){
           e.printStackTrace();
           request.setState(State.NOT_FOUND);
           request.setFileRequested("404.html");
           request.setResource(config.getHtml404());
           request.sendResponseHeaders(config);
           request.sendRequestBody();
       }
    }

}


