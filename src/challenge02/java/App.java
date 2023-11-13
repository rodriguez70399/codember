package challenge02.java;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class App 
{
    /**
     * Metodo no tan moderno y sencillo de hacer el challenge 
     * usando un switch basico de java
     * 
     * @param text texto a decodificar
     * @return String con el texto decodificado
     */
    public static String oldMethod(String text)
    {
        int number = 0;
        char character = ' ';
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) 
        {
            character = text.charAt(i);

            switch (character) 
            {
                case '#':
                    number++;
                    break;
                case '@':
                    number--;
                    break;
                case '*':
                    number*=number;
                    break;
                case '&': 
                    result.append(number);
            }           
        }

        return result.toString();
    }

    /**
     * Metodo modernizado usando lambdas para realizar el challenge02
     * 
     * <p>Se usa AtomicInteger para poder usar paso por referencia, 
     * ya que el objeto Integer es inmutable y cada vez que se realiza
     * una operacion sobre el se crea uno nuevo, y un primitivo no se puede
     * usar porque se pasa por valor y no se le aplicarian las operaciones</p>
     * 
     * @param text texto a decodificar
     * @return String con el texto decodificado
     */
    public static String improvedMethod(String text)
    {
        StringBuilder result = new StringBuilder();
        AtomicInteger number = new AtomicInteger(0);
        Map<Character, Consumer<AtomicInteger>> operations = new HashMap<>();
        operations.put('#', x -> x.incrementAndGet());
        operations.put('@', x -> x.decrementAndGet());
        operations.put('*', x -> x.set(x.get() * x.get()));
        operations.put('&', x -> result.append(x.get()));

        for (char currentCharacter : text.toCharArray()) 
        {
            if (operations.containsKey(currentCharacter)) 
            {
                operations.get(currentCharacter).accept(number);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) 
    {
        String text = "";

        System.out.println("\n** Mini Compiler Challenge **");

        try (Scanner scanner = new Scanner(System.in)) 
        {
            System.out.print("\nIntroduzca la cadena de texto: ");
            text = scanner.nextLine();
            System.out.println("\nResultado: " + improvedMethod(text));            
        } 
        catch (NoSuchElementException e) 
        {
            System.out.println("\nEntrada no valida!");
        }
    }
}
