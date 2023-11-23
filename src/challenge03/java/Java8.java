package challenge03.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Clase con la solucion al Challenge 03 usando metodos de Java 8
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
     * @param lines Lista de claves con sus políticas en una sola línea.
     * @param index Índice de la clave buscada.
     * @return Clave inválida indicada.
     */
    public static String findInvalidPassword(List<String> lines, int index)
    {
        String info = lines.stream().filter(line -> !Java8.isValid(line)).skip(index-1).findFirst().orElse(null);
        return Objects.nonNull(info) ? info.split(" ")[2] : null;
    }

    /**
     * Busca la clave válida que se indique en una lista con claves y sus políticas en una línea.
     * 
     * <p>
     *  Para ello usa streams para filtrar las claves que sean correctas usando el método {@link #isValid(String)},
     *  nos saltamos las anteriores al índice indicado y obtenemos el elemento. Finalmente, obtenemos solo la clave usando split.
     * </p>
     * 
     * @param lines Lista de claves con sus políticas en una sola línea.
     * @param index Índice de la clave buscada.
     * @return Clave válida indicada.
     */
    public static String findValidPassword(List<String> lines, int index)
    {
        String info = lines.stream().filter(line -> Java8.isValid(line)).skip(index-1).findFirst().orElse(null);
        return Objects.nonNull(info) ? info.split(" ")[2] : null;
    }

    public static void main(String[] args) 
    {
        List<String> keys;
        final String FILE_PATH = "https://codember.dev/data/encryption_policies.txt";
        final int SEARCHED_KEY = 13;

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(FILE_PATH).openStream())))
        {
            keys = bufferedReader.lines().collect(Collectors.toCollection(ArrayList::new));
            System.out.println(Java8.findInvalidPassword(keys, SEARCHED_KEY));
        } 
        catch (IOException ioException) 
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }
        catch (Exception exception)
        {
            System.out.println("\nSe ha producido un error: " + exception.getMessage());
        }
    }   
}
