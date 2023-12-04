package challenge04.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Clase Java8 que implementa soluciones para el Challenge 04 utilizando características de Java 8.
 */
public class Java8 
{
    /**
     * Método que verifica si el checksum indicado cumple con su verdadero checksum.
     * 
     * <p>
     * Se obtiene un streams con los caracteres de la cadena, se convierten a
     * objetos y se agrupan por sus apariciones
     * en un LinkedHashMap para mantener el orden. Una vez se tiene el mapa, se
     * filtra por los caracteres que solo aparezcan una
     * vez, nos quedamos solo con las keys y las agrupamos en un String.
     * </p>
     * 
     * @param fileName Nombre de archivo en formato "leftPart-checksum".
     * @return true si la cadena cumple con el checksum esperado, false de lo contrario.
     */
    private static boolean isValid(String fileName)
    {
        String leftPart = "";
        String checksum = "";
        String correctChecksum = "";

        if (Objects.isNull(fileName) || fileName.split("-").length == 2) 
        {
            return false;
        }

        leftPart = fileName.split("-")[0];
        checksum = fileName.split("-")[1];

        correctChecksum = leftPart.chars()
                .mapToObj(character -> (char) character)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(entry -> String.valueOf(entry.getKey()))
                .collect(Collectors.joining());
        
        return checksum.equals(correctChecksum);
    }
    
    /**
     * Método que obtiene el archivo real con el indice indicado, comprobado con {@link #isValid(String)}.
     * 
     * <p>Para ello se obtiene un streams con las lineas del archivo, se filtra por las que son validas, saltamos hasta 
     * el indice deseado y nos quedamos solo con el. </p>
     * 
     * @param listPath URL del archivo que contiene la lista de nombres de archivo.
     * @param index Indice del archivo buscado.
     * @return Nombre del archivo encontrado o "No encontrado" si no cumple con las condiciones.
     */
    public static String getRealFile(String listPath, int index)
    {
        String result = "";

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(listPath).openStream())))
        {
            result = bufferedReader.lines().filter(line -> Java8.isValid(line)).skip(index-1).findFirst().orElse("");
        } 
        catch (IOException ioException) 
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }
        catch (Exception exception)
        {
            System.out.println("\nSe ha producido un error: " + exception.getMessage());
        }

        return result;
    }

    public static void main(String[] args) 
    {
        final String FILE_LIST_PATH = "https://codember.dev/data/files_quarantine.txt";
        final int SEARCHED_FILE = 33;

        System.out.println("Resultado: " + Java8.getRealFile(FILE_LIST_PATH, SEARCHED_FILE));
    }
}
