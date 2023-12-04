package challenge04.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que implementa la solucion para el Challenge 04 usando metodos simples de java.
 */
public class ClassicJava 
{
    /**
     * Método que verifica si el checksum indicado cumple con su verdadero checksum.
     * Versión 1 del algoritmo de validación.
     * 
     * @param fileName Nombre de archivo en formato "leftPart-checksum".
     * @return true si la cadena cumple con el checksum esperado, false de lo contrario.
     */
    private static boolean isValidV1(String fileName)
    {
        boolean isValid = false;
        boolean isRepeated;
        int appearance;
        String leftPart = "";
        String checksum = "";
        StringBuilder correctChecksum = new StringBuilder();

        if (Objects.nonNull(fileName) && fileName.split("-").length == 2) 
        {
            leftPart = fileName.split("-")[0];
            checksum = fileName.split("-")[1];

            for (int i = 0; i < leftPart.length(); i++) 
            {
                isRepeated = false;
                appearance = 0;

                for (int j = 0; j < leftPart.length() && !isRepeated; j++) 
                {
                    if (leftPart.charAt(i) == leftPart.charAt(j))
                    {
                        appearance++;                        
                    }    

                    if (appearance > 1) 
                    {
                        isRepeated = true;                    
                    }
                }

                if (appearance == 1) 
                {
                    correctChecksum.append(leftPart.charAt(i));
                }
            }

            isValid = checksum.equals(correctChecksum.toString());
        }

        return isValid;
    }

    /**
     * Método que verifica si el checksum indicado cumple con su verdadero checksum.
     * Versión 2 del algoritmo de validación.
     * 
     * @param fileName Nombre de archivo en formato "leftPart-checksum".
     * @return true si la cadena cumple con el checksum esperado, false de lo contrario.
     */
    private static boolean isValidV2(String fileName)
    {
        boolean isValid = false;
        String leftPart = "";
        String checksum = "";
        char currentCharacter = ' ';
        StringBuilder correctChecksum = new StringBuilder();
        List<Character> repeatedCharacters = new ArrayList<>();

        if (Objects.nonNull(fileName) && fileName.split("-").length == 2) 
        {
            leftPart = fileName.split("-")[0];
            checksum = fileName.split("-")[1];

            for (int i = 0; i < leftPart.length(); i++) 
            {
                currentCharacter = leftPart.charAt(i);

                if (repeatedCharacters.indexOf(currentCharacter) == -1 && leftPart.indexOf(currentCharacter, i+1) == -1) 
                {
                    correctChecksum.append(currentCharacter);                    
                }
                else
                {
                    repeatedCharacters.add(currentCharacter);
                }   
            }

            isValid = checksum.equals(correctChecksum.toString());
        }

        return isValid;
    }

    /**
     * Método que obtiene el archivo real con el indice indicado, comprobado con {@link #isValidV2(String)}.
     * 
     * @param listPath URL del archivo que contiene la lista de nombres de archivo.
     * @param index Indice del archivo buscado.
     * @return Nombre del archivo encontrado o "No encontrado" si no cumple con las condiciones.
     */
    public static String getRealFile(String listPath, int index)
    {
        List<String> realFiles = new ArrayList<>();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(listPath).openStream())))
        {
            while ((line = bufferedReader.readLine()) != null) 
            {
                if (ClassicJava.isValidV2(line)) 
                {
                    realFiles.add(line);                    
                }
            }  
        }
        catch(IOException ioException)
        {
            System.out.println("\nError de entrada/salida: " + ioException.getMessage());
        }

        return realFiles.size() >= index? realFiles.get(index-1) : "No encontrado";
    }

    public static void main(String[] args) 
    {
        final String FILE_LIST_PATH = "https://codember.dev/data/files_quarantine.txt";
        final int SEARCHED_FILE = 33;

        System.out.println("Resultado: " + ClassicJava.getRealFile(FILE_LIST_PATH, SEARCHED_FILE));
    }
}