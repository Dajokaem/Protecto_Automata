/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Scanner;

import java.util.Scanner;

public class ValidadorExpresion {

    // Método para verificar si la expresión es válida
    public static boolean esValida(String expresion) {
        // Eliminar espacios en blanco
        expresion = expresion.replaceAll("\\s+", "");

        // Comprobar que la expresión no comience ni termine con un operador
        if (expresion.startsWith("+") || expresion.startsWith("-") || 
            expresion.startsWith("*") || expresion.startsWith("/") ||
            expresion.endsWith("+") || expresion.endsWith("-") || 
            expresion.endsWith("*") || expresion.endsWith("/")) {
            return false;
        }

        // Bandera para controlar si estamos dentro de una secuencia de letras o números
        boolean esVariable = false;
        boolean esNumero = false;

        // Comprobar la secuencia de caracteres
        char anterior = ' '; // Inicializar con un espacio vacío (ningún carácter previo)
        for (char actual : expresion.toCharArray()) {
            if (actual == '+' || actual == '-' || actual == '*' || actual == '/') {
                // Si dos operadores están seguidos, la expresión es inválida
                if (anterior == '+' || anterior == '-' || anterior == '*' || anterior == '/') {
                    return false;
                }
                // Reiniciar banderas después de un operador
                esVariable = false;
                esNumero = false;
            } else if (Character.isDigit(actual)) {
                if (esVariable) {
                    // Si ya estábamos en una variable y encontramos un número, es inválido
                    return false;
                }
                esNumero = true; // Ahora estamos en una secuencia de números
            } else if (Character.isLowerCase(actual)) {
                if (esNumero) {
                    // Si ya estábamos en una secuencia de números y encontramos una letra, es inválido
                    return false;
                }
                esVariable = true; // Ahora estamos en una secuencia de letras
            } else {
                // Si encontramos algo que no es un operador, número o letra minúscula, es inválido
                return false;
            }
            anterior = actual;
        }

        return true; // Si pasó todas las validaciones, la expresión es válida
    }

    
}

