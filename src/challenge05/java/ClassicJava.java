package challenge05.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Clase que implementa la solucion para el Challenge 05 usando metodos simples de java.
 */
public class ClassicJava 
{
    /**
     * Método que valida la entrada de usuario según los criterios especificados en el reto.
     * 
     * <p>Para ello se usa la clase {@link Regex} con expresiones regulares 
     * y metodos para validar cada campo.</p>
     * 
     * @param input La cadena de entrada del usuario en formato "id,username,email,age,location".
     * @return true si la entrada cumple con los criterios, false de lo contrario.
     */
    public static boolean isValid(String input)
    {
        String[] splitedInput = input.split(",");

        if (splitedInput.length < 3)
        {
            return false;            
        }

        String id = splitedInput[0];
        String username = splitedInput[1];
        String email  = splitedInput[2];
        String age = splitedInput.length > 3? splitedInput[3] : "";
        String location = splitedInput.length > 4? splitedInput[4] : "";

        return Regex.checkRequiredField(id, Regex.ALPHANUMERIC_PATTERN) 
            && Regex.checkRequiredField(username, Regex.ALPHANUMERIC_PATTERN)
            && Regex.checkRequiredField(email, Regex.EMAIL_PATTERN)
            && Regex.checkOptionalField(age, Regex.NUMBER_PATTERN)
            && Regex.checkOptionalField(location, Regex.TEXT_PATTERN);
    }

    /**
     * Método que obtiene la palabra secreta a partir de un archivo usando los usuarios inválidos.
     *
     * <p>Se usa el metodo {@link #isValid(String)} para validar cada usuario.</p>
     * 
     * @param filePath La URL del archivo que contiene las entradas de usuario.
     * @return La palabra secreta formada por las iniciales de los usuarios inválidos.
     */
    public static String getSecretWordFromInvalidUserFile(String filePath)
    {
        StringBuilder secretWord = new StringBuilder();
        String line = "";

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(filePath).openStream())))
        {
            while ((line = bufferedReader.readLine()) != null) 
            {
                if (!ClassicJava.isValid(line)) 
                {
                    secretWord.append(line.split(",")[1].charAt(0));                 
                }
            }
        }
        catch(IOException ioException)
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }

        return secretWord.toString();
    }

    public static void main(String[] args) 
    {
        final String FILE_LIST_PATH = "https://codember.dev/data/database_attacked.txt";
        System.out.println("\nPalabra secreta: " + ClassicJava.getSecretWordFromInvalidUserFile(FILE_LIST_PATH));
        //youh4v3beenpwnd
    }
}
