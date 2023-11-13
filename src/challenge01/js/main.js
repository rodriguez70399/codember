"use strict";

/**
 * Metodo auxilar necesario para {@link oldMethod} que crea un map con las palabras
 * como clave y sus apariciones como valor.
 * 
 * @param {string} text El texto de entrada a analizar.
 * @returns {Map} Un objeto Map donde las claves son palabras únicas y los valores son sus ocurrencias.
 */
function getWordCount(text) 
{
    let wordCount = new Map();
    let words = text.split(" ");
    let currentKey = "";
    let currentValue = 0;

    for (let i = 0; i < words.length; i++) 
    {
        currentKey = words[i];
        currentValue = wordCount.get(currentKey) ?? 0;
        wordCount.set(currentKey, ++currentValue);
    }

    return wordCount;
}

/**
 * Metodo mas simple y antiguo para resolver el challenge01,
 * une las palabras y sus apariciones en un String ayudandose de
 * {@link getWordCount}
 * 
 * @param {string} text El texto de entrada a analizar.
 * @returns {Map} Una cadena que representa las ocurrencias de palabras sin espacios entre palabras y conteos.
 */
function oldMethod(text) 
{
    let wordCount = getWordCount(text);
    let result = [];

    for (let [key, value] of wordCount) 
    {
        result.push(key, value);
    }

    return result.join("");
}

/**
 * Genera una representación de cadena de las ocurrencias de palabras utilizando un método mejorado.
 *
 * @param {string} text El texto de entrada a analizar.
 * @returns {string} Una cadena que representa las ocurrencias de palabras sin espacios entre palabras y conteos.
 */
let improvedMethod = (text) => 
{
    let result = [];
    let wordCount = Array.from(text.split(" "))
        .map(word => word.replace("[^a-zA-Z]", "")
        .toLowerCase()).filter(word => word.length > 0)
        .reduce((count, word) => 
        {
            count[word] = (count[word] || 0) + 1;
            return count;
        }, {});

    for (let [word, count] of Object.entries(wordCount)) 
    {
        result.push(word + count);
    }

    return result.join("");
}

let text = "murcielago leon jirafa cebra elefante rinoceronte hipopotamo ardilla mapache zorro lobo oso puma jaguar tigre leopardo gato perro caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco tigre jaguar leopardo oso lobo zorro mapache ardilla elefante rinoceronte hipopotamo cebra jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago colibri buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago tucan loro canario colibri buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago flamenco pinguino tucan loro canario colibri buho aguila halcon paloma pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago jaguar oso lobo zorro mapache ardilla cebra elefante rinoceronte hipopotamo leon jirafa murcielago caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco jaguar oso lobo zorro mapache ardilla cebra elefante rinoceronte hipopotamo leon jirafa murcielago caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco murcielago leon jirafa cebra elefante rinoceronte hipopotamo ardilla mapache zorro lobo oso puma jaguar tigre leopardo gato perro caballo vaca toro cerdo oveja cabra gallina pato ganso pavo paloma halcon aguila buho colibri canario loro tucan pinguino flamenco oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato leopardo tigre jaguar oso lobo zorro mapache ardilla cebra elefante rinoceronte hipopotamo jirafa leon murcielago pavo ganso pato gallina cabra oveja cerdo toro vaca caballo perro gato buho aguila halcon paloma colibri canario loro tucan pinguino flamenco jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato buho aguila halcon paloma colibri canario loro tucan pinguino flamenco jaguar oso lobo zorro mapache ardilla hipopotamo rinoceronte elefante jirafa leon murcielago cabra oveja cerdo toro vaca caballo perro gato buho aguila halcon";
console.log(oldMethod(text));
console.log(improvedMethod(text));