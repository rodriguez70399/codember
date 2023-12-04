package challenge03.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase principal que resuelve el Challenge 03 utilizando métodos de Java 8.
 * Se encarga de validar y buscar contraseñas en un archivo según ciertas políticas.
 */
public class Java8 
{
    private final static String REGEX = "^(\\d+)-(\\d+) ([A-Za-z]): ([A-Za-z]+)$";
    private final static Pattern PATTERN = Pattern.compile(REGEX);    

    /**
     * Método que comprueba si una clave cumple con su política
     * leyendo la línea que incluye ambas.
     * 
     * <p>
     *  Para ello, después de obtener cada parte con los grupos 
     *  de la expresión regular, usamos streams para filtrar el string
     *  y quedarnos con los caracteres que sean iguales al buscado.
     * </p>
     * 
     * @param info Política y clave en el formato especificado.
     * @return true si la clave cumple con su política.
     */
    private static boolean isValid(String info)
    {
        int minAppearance = 0;
        int maxAppearance = 0;
        final char searchedCharacter;
        String key = null;
        Matcher matcher = Java8.PATTERN.matcher(info);
        boolean isValid = false;
        long appearance = 0;

        if (Objects.nonNull(info) && matcher.matches())
        {
            minAppearance = Integer.parseInt(matcher.group(1));
            maxAppearance = Integer.parseInt(matcher.group(2));
            searchedCharacter = matcher.group(3).charAt(0);
            key = matcher.group(4);

            appearance = key.chars().filter(currentCharacter -> currentCharacter == searchedCharacter).count();

            isValid = appearance >= minAppearance && appearance <= maxAppearance;
        }

        return isValid;
    }

    /**
     * Método que busca la clave inválida que se indique en una lista con claves y sus políticas
     * en una sola línea.
     * 
     * <p>
     *  Para ello usa streams para filtrar las claves que sean incorrectas usando el método {@link #isValid(String)},
     *  nos saltamos las anteriores al índice indicado y obtenemos el elemento. Finalmente, obtenemos solo la clave usando split.
     * </p>
     * 
     * @param filePath Url del archivo que contiene la lista de claves y sus políticas.
     * @param index Índice de la clave buscada.
     * @return Clave inválida indicada.
     */
    public static String findInvalidPassword(String filePath, int index)
    {
        String info = "";
        
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(filePath).openStream())))
        {
            info = bufferedReader
            .lines()
            .filter(line -> !Java8.isValid(line))
            .skip(index-1)
            .findFirst()
            .orElse("");
        } 
        catch (IOException ioException) 
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }
        catch (Exception exception)
        {
            System.out.println("\nSe ha producido un error: " + exception.getMessage());
        }
        
        return info.isEmpty() ? "" : info.split(" ")[2]; 
    }

    /**
     * Busca la clave válida que se indique en una lista con claves y sus políticas en una línea.
     * 
     * <p>
     *  Para ello usa streams para filtrar las claves que sean correctas usando el método {@link #isValid(String)},
     *  nos saltamos las anteriores al índice indicado y obtenemos el elemento. Finalmente, obtenemos solo la clave usando split.
     * </p>
     * 
     * @param filePath Url del archivo que contiene la lista de claves y sus políticas.
     * @param index Índice de la clave buscada.
     * @return Clave válida indicada.
     */
    public static String findValidPassword(String filePath, int index)
    {
        String info = "";
        
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(filePath).openStream())))
        {
            info = bufferedReader
            .lines()
            .filter(line -> Java8.isValid(line))
            .skip(index-1)
            .findFirst()
            .orElse("");
        } 
        catch (IOException ioException) 
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }
        catch (Exception exception)
        {
            System.out.println("\nSe ha producido un error: " + exception.getMessage());
        }
        
        return info.isEmpty() ? "" : info.split(" ")[2];
    }

    public static void main(String[] args) 
    {
        final String FILE_PATH = "https://codember.dev/data/encryption_policies.txt";
        final int SEARCHED_KEY = 42;

        System.out.println(Java8.findInvalidPassword(FILE_PATH, SEARCHED_KEY));
    }   
}
