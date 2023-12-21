package challenge05.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Clase Java8 que implementa soluciones para el Challenge 05 utilizando características de Java 8.
 */
public class Java8 
{
    /**
     * Método que obtiene la palabra secreta a partir de un archivo usando los usuarios inválidos utilizando expresiones lambda y streams.
     * 
     * <p>Se usa el metodo {@link ClassicJava#isValid(String)} para validar cada usuario.</p>
     *
     * @param filePath La URL del archivo que contiene las entradas de usuario.
     * @return La palabra secreta formada por las iniciales de los usuarios inválidos.
     */
    public static String getSecretWordFromInvalidUserFile(String filePath)
    {
        String secretWord = "";

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(filePath).openStream())))
        {
            secretWord = bufferedReader
                .lines()
                .filter(line -> !ClassicJava.isValid(line))
                .map(line -> String.valueOf(line.split(",")[1].charAt(0)))
                .collect(Collectors.joining());
        }
        catch(IOException ioException)
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }

        return secretWord;
    }

    public static void main(String[] args) 
    {
        final String FILE_LIST_PATH = "https://codember.dev/data/database_attacked.txt";
        System.out.println("\nPalabra secreta: " + Java8.getSecretWordFromInvalidUserFile(FILE_LIST_PATH));
        //youh4v3beenpwnd
    }
}
