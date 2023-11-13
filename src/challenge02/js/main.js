"use strict";

/**
 * Metodo mas sencillo de hacer el challenge
 * usando un switch basico de javascript
 * 
 * @param {string} text texto a decodificar
 * @returns string con el texto decodificado
 */
function simpleMethod(text) 
{
    let number = 0;
    let character = "";
    let result = "";

    for (let i = 0; i < text.length; i++) 
    {
        character = text.charAt(i);

        switch (character) 
        {
            case "#":
                number++;                
                break;

            case "@":
                number--;
                break;

            case "*":
                number*=number;
                break;

            case "&":
                 result += number;                
                break;
        }
    }

    return result;
}

/**
 * Metodo mas complejo y con mejores practicas usando objetos literales
 * 
 * @param {string} text texto a decodificar
 * @returns string con el texto decodificado
 */
let improvedMethod = text =>
{
    let result = "";
    let number = 0;
    const operations = {
        "#" : () => { number++ },
        "@" : () => { number-- },
        "*" : () => { number*=number },
        "&" : () => { result += number }
    }
    
    for (let currentCharacter of text) 
    {
        if (operations.hasOwnProperty(currentCharacter))
        {
            operations[currentCharacter]();
        }   
    }

    return result;
}

console.log(simpleMethod("&##&*&@&"));
console.log(improvedMethod("&##&*&@&"));