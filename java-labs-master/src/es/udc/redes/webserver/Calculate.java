/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.udc.redes.webserver;

import java.util.Map;

/**
 * Very _simple calculator
 * @author querty
 */
public class Calculate implements MiniServlet{

    @Override
    public String doGet(Map<String, String> parameters) throws Exception {
            int i = Integer.parseInt(parameters.get("firstOperand"));
            int j =  Integer.parseInt(parameters.get("secondOperand"));
            String result;
            switch(parameters.get("operation")){
                case "%2B":
                    i = i+j;
                    result = Integer.toString(i);;
                    break;
                case "-":
                    i = i-j;
                    result = Integer.toString(i);
                    break;
                case "*":
                    i = i*j;
                    result = Integer.toString(i);
                    break;
                case "%2F":
                    i = i/j;
                    result = Integer.toString(i);
                    break;
                default:
                    result = "Operation not Implemented";
            }
                
    return printHeader() + printBody(result) + printEnd();}
    
    private String printHeader() {
		return "<html><head> <title>Very Simple Calculator</title> </head> ";
	}

	private String printBody(String result) {
		return "<body> <h3> Result " + result + "</h3></body>";
	}

	private String printEnd() {
		return "</html>";
	}
    
}
