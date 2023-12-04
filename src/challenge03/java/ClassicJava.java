package challenge03.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Clase que implementa la solucion para el Challenge 03 usando metodos simples de java.
 */
public class ClassicJava 
{
    private final static String REGEX = "^(\\d+)-(\\d+) ([A-Za-z]): ([A-Za-z]+)$";
    private final static Pattern PATTERN = Pattern.compile(REGEX);

    public final static String VALID_LIST = "valid";
    public final static String INVALID_LIST = "invalid";

    /**
     * Método que comprueba si una clave cumple con su política
     * leyendo la línea que incluye ambas.
     * 
     * <p>
     *  Para ello separa el string por partes y comprueba recorriendo
     *  la clave letra a letra si el rango de apariciones de la letra
     *  se cumple.
     * </p>
     * 
     * @param info Política y clave en el formato especificado.
     * @return true si la clave cumple con su política.
     */
    private static boolean isValid(String info)
    {
        String[] splitedInfo;
        String[] appearanceRange;
        int minAppearance = 0;
        int maxAppearance = 0;
        char searchedCharacter = ' ';
        String key = null;
        int appearance = 0;
        boolean isValid = false;

        if (Objects.nonNull(info) && PATTERN.matcher(info).matches()) 
        {
            splitedInfo = info.split(" ");
            appearanceRange = splitedInfo[0].split("-");
            minAppearance = Integer.parseInt(appearanceRange[0]);
            maxAppearance = Integer.parseInt(appearanceRange[1]);
            searchedCharacter = splitedInfo[1].charAt(0);
            key = splitedInfo[2];
    
            for (int i = 0; i < key.length(); i++) 
            {
                if (key.charAt(i) == searchedCharacter)
                {
                    appearance++;
                }
            }
    
            isValid = appearance >= minAppearance && appearance <= maxAppearance;        
        }

        return isValid;
    }

    /**
     * Método que comprueba desde un archivo de texto alojado en internet qué claves
     * cumplen con su política y cuáles no.
     * 
     * <p>
     *  Para ello se recorre cada línea del archivo con un bucle y se comprueba con 
     *  el método {@link #isValid(String)}.
     * </p>
     * 
     * @param path Ruta web al archivo.
     * @return Un mapa con dos elementos, uno con la llave {@link #VALID_LIST} con la lista
     * de claves validas, y otro con la llave {@link #INVALID_LIST} con la lista de claves
     * inválidas.
     */
    public static Map<String, List<String>> checkKeysFromFile(String path)
    {
        List<String> invalidKeys = new ArrayList<>();
        List<String> validKeys = new ArrayList<>();
        String line = null;
        String currentKey = "";
        HashMap<String, List<String>> chekedKeyList = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(path).openStream())))
        {

            while ((line = bufferedReader.readLine()) != null) 
            {
                currentKey = line.split(" ")[2];

                if (ClassicJava.isValid(line)) 
                {
                    validKeys.add(currentKey);                    
                }
                else
                {
                    invalidKeys.add(currentKey);
                }
            }  
            
            chekedKeyList.put(VALID_LIST, validKeys);
            chekedKeyList.put(INVALID_LIST, invalidKeys);
        }
        catch(IOException ioException)
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }

        return chekedKeyList;
    }

    public static void main(String[] args) 
    {
        final String FILE_PATH = "https://codember.dev/data/encryption_policies.txt";
        final int SEARCHED_KEY = 13;

        Map<String, List<String>> keyLists = ClassicJava.checkKeysFromFile(FILE_PATH);
        List<String> invalidKeys = keyLists.get(ClassicJava.INVALID_LIST);

        if (Objects.nonNull(invalidKeys)) 
        {
            System.out.println(invalidKeys.get(SEARCHED_KEY-1));
        }
    }
}