package challenge01.java;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App 
{
    /**
     * Metodo auxiliar para {@link App#oldMethod(String)} que crea un mapa
     * con las palabras como clave y sus apariciones como valor
     * 
     * <p>Se usa LinkedHashMap para que se mantega el orden de añadido</p>
     * 
     * @param text El texto de entrada a analizar.
     * @return Un objeto Map donde las claves son palabras únicas y los valores son sus ocurrencias.
     */
    private static Map<String, Integer> getWordCount(String text)
    {
        Map<String, Integer> wordCount = new LinkedHashMap<>();
        String[] words = text.toLowerCase().split(" ");
        String currentKey = "";
        int currentValue = 0;

        for (int i = 0; i < words.length; i++) 
        {
            currentKey = words[i];

            if (!wordCount.containsKey(currentKey))
            {
                wordCount.put(words[i], 1);
            }
            else
            {
                currentValue = wordCount.get(currentKey);
                wordCount.put(currentKey, ++currentValue);
            }            
        }

        return wordCount;
    }

    /**
     * Metodo mas simple y antiguo para resolver el challenge01,
     * une las palabras y sus apariciones en un String ayudandose de
     * {@link App#getWordCount(String)}
     * 
     * @param text El texto de entrada a analizar.
     * @return String con todas las palabras y su numero de apariciones sin espacios
     */
    public static String oldMethod(String text)
    {
        Map<String, Integer> wordCount = getWordCount(text);
        StringBuilder result = new StringBuilder();

        for (Entry<String, Integer> word : wordCount.entrySet()) 
        {
            result.append(word.getKey() + word.getValue());
        }

        return result.toString();
    }

    /**
     * Metodo mas moderno usando lambdas para resolver el challenge01
     * 
     * <p>Se convierte a stream el array de palabras, con map eliminamos todo lo que no sean letras,
     * eso lo convertimos a minusculas y con filter nos quedamos con las palabras que no esten vacias.
     * Finalmente con collect agrupamos las palabras resultantes en un map, usando la palabra en si como
     * clave y sus apariciones como valor usando counting.
     * Terminamos recorriendo el Map y uniendo la clave y el valor en un String(StringBuilder porque String
     * es inmutable)</p>
     * 
     * @param text El texto de entrada a analizar.
     * @return String con todas las palabras y su numero de apariciones sin espacios
     */
    public static String goodMethod(String text)
    {
        StringBuilder result = new StringBuilder();
        List<String> words = Arrays.asList(text.split(" "));
        Map<String, Long> wordCount = words.stream()
            .map(word -> word.replaceAll("[^a-zA-Z]", "")
            .toLowerCase()).filter(word -> !word.isEmpty())
            .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        
        wordCount.forEach((key, value) -> result.append(key).append(value));
        
        return result.toString();
    }

    public static void main(String[] args) 
    {
        String text = "murcielago leon jirafa cebra elefante rinoceronte hipopotamo ardilla mapache zorro lobo oso puma jaguar tigre leopardo gato perro caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco tigre jaguar leopardo oso lobo zorro mapache ardilla elefante rinoceronte hipopotamo cebra jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago colibri buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago tucan loro canario colibri buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago flamenco pinguino tucan loro canario colibri buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago jaguar oso lobo zorro mapache ardilla cebra elefante rinoceronte hipopotamo leon jirafa murcielago caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco jaguar oso lobo zorro mapache ardilla cebra elefante rinoceronte hipopotamo leon jirafa murcielago caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco murcielago leon jirafa cebra elefante rinoceronte hipopotamo ardilla mapache zorro lobo oso puma jaguar tigre leopardo gato perro caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla cebra elefante rinoceronte hipopotamo jirafa leon murcielago pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato buho aguila halcon paloma colibri canario loro tucan pinguino flamenco jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato buho aguila halcon paloma colibri canario loro tucan pinguino flamenco jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato buho aguila halcon";
        System.out.println(App.oldMethod(text));
        System.out.println(App.goodMethod(text));
    }
}