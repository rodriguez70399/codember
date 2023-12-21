package challenge05.java;

import java.util.regex.Pattern;

/**
 * Clase Regex que contiene expresiones las regulares necesarias para realizar el reto y métodos para validar.
 */
public class Regex 
{
    private final static String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9]+$";
    public final static Pattern ALPHANUMERIC_PATTERN = Pattern.compile(ALPHANUMERIC_REGEX);
    private final static String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private final static String NUMBER_REGEX = "^[0-9]+$";
    public final static Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
    private final static String TEXT_REGEX = "^[a-zA-Z ]+$";
    public final static Pattern TEXT_PATTERN = Pattern.compile(TEXT_REGEX);

    /**
     * Método que verifica si un campo requerido cumple con un patrón dado.
     *
     * @param field   El campo a validar.
     * @param pattern El patrón de expresión regular a comparar.
     * @return true si el campo no está vacío y cumple con el patrón, false de lo contrario.
     */
    public static boolean checkRequiredField(String field, Pattern pattern)
    {
        return !field.isBlank() && pattern.matcher(field).matches();
    }

    /**
     * Método que verifica si un campo opcional cumple con un patrón dado.
     *
     * @param field   El campo a validar.
     * @param pattern El patrón de expresión regular a comparar.
     * @return true si el campo está vacío o cumple con el patrón, false de lo contrario.
     */
    public static boolean checkOptionalField(String field, Pattern pattern)
    {
        return field.isBlank() || pattern.matcher(field).matches();
    }
}
