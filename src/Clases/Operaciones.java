/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author jos56
 */
public class Operaciones {

    public Operaciones() {
    }
    
    
    
    
    
    
    public static boolean esExpresionValida(String expresion) {
        // Verificar que la expresión comience con '<' y termine con '>'
        return expresion.startsWith("<") && expresion.endsWith(">") && expresion.length() > 2;
    }

    public static double evaluar(String expresion) throws Exception {
        List<String> tokens = tokenize(expresion);
        List<String> posfija = infijaAPosfija(tokens);
        return evaluaPosfija(posfija);
    }

    public static List<String> tokenize(String expresion) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        
        for (char c : expresion.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                currentToken.append(c);
            } else {
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                }
                if (!Character.isWhitespace(c)) {
                    tokens.add(String.valueOf(c));
                }
            }
        }
        
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        
        return tokens;
    }

    public static List<String> infijaAPosfija(List<String> tokens) throws Exception {
        List<String> salida = new ArrayList<>();
        Stack<String> operadores = new Stack<>();

        Map<String, Integer> precedencia = new HashMap<>();
        precedencia.put("+", 1);
        precedencia.put("-", 1);
        precedencia.put("*", 2);
        precedencia.put("/", 2);
        precedencia.put("^", 3);
        precedencia.put("sqrt", 4);
        precedencia.put("(", 0);

        for (String token : tokens) {
            if (isNumero(token)) {
                salida.add(token);
            } else if (token.equals("(")) {
                operadores.push(token);
            } else if (token.equals(")")) {
                while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                    salida.add(operadores.pop());
                }
                operadores.pop(); 
            } else {
                while (!operadores.isEmpty() && precedencia.get(token) <= precedencia.get(operadores.peek())) {
                    salida.add(operadores.pop());
                }
                operadores.push(token);
            }
        }

        while (!operadores.isEmpty()) {
            salida.add(operadores.pop());
        }

        return salida;
    }

    public static double evaluaPosfija(List<String> posfija) throws Exception {
        Stack<Double> stack = new Stack<>();

        for (String token : posfija) {
            if (isNumero(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double resultado;
                if (token.equals("sqrt")) {
                    double b = stack.pop();
                    resultado = Math.sqrt(b);
                } else {
                    double b = stack.pop();
                    double a = stack.pop();
                    resultado = aplicarOperacion(a, b, token);
                }
                stack.push(resultado);
            }
        }

        return stack.pop();
    }

    public static boolean isNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double aplicarOperacion(double a, double b, String operador) throws Exception {
        switch (operador) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new Exception("División por cero");
                }
                return a / b;
            case "^":
                return Math.pow(a, b);
            default:
                throw new Exception("Operador desconocido: " + operador);
        }
    }
}
