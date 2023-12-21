"use strict";

import {Regex} from "./Regex.mjs";

const FILE_PATH = "https://codember.dev/data/database_attacked.txt";

/**
 * Función que valida la entrada de usuario según los criterios especificados en el reto.
 * 
 * Para ello se usa la clase {@link Regex} con expresiones regulares 
 * y metodos para validar cada campo.</p>
 *
 * @param {string} input - La cadena de entrada en formato "id,username,email,age,location".
 * @returns {boolean} - true si la entrada cumple con los criterios, false de lo contrario.
 */
let isValid = input => 
{
    let splitedInput = [];

    if(typeof input !== "string")
    {
        return false;
    }

    splitedInput = input.split(",");

    if (splitedInput.length < 3) 
    {
        return false;
    }

    let [id, username, email] = splitedInput;
    let age = splitedInput.length > 3? splitedInput[3] : "";
    let location = splitedInput.length > 4? splitedInput[4] : "";

    return Regex.checkRequiredField(id, Regex.ALPHANUMERIC_REGEX)
        && Regex.checkRequiredField(username, Regex.ALPHANUMERIC_REGEX)
        && Regex.checkRequiredField(email, Regex.EMAIL_REGEX)
        && Regex.checkOptionalField(age, Regex.NUMBER_REGEX)
        && Regex.checkOptionalField(location, Regex.TEXT_REGEX);        
}

/**
 * Función asíncrona que obtiene la palabra secreta a partir de un archivo usando los usuarios inválidos.
 *
 * @param {string} filePath La URL del archivo que contiene las entradas de usuario.
 * @returns {Promise<string>} La palabra secreta formada por las iniciales de los usuarios inválidos.
 */
let getSecretWordFromInvalidUserFile = async filePath =>
{
    let secretWord = "";
    let response;
    let data;

    if (typeof filePath !== "string") 
    {
        reject("Invalid arguments");        
    }

    try 
    {
        response = await fetch(filePath);
        
        if (!response.ok) 
        {
            throw new Error("File not found");            
        }

        data = await response.text();
        secretWord = data
            .split("\n")
            .filter(line => !isValid(line))
            .map(line => line.split(",")[1][0])
            .join("");
    } 
    catch (error) 
    {
        console.log(error === "File not Found" ? `Se ha producido un error al leer el archivo ${error}` :
            `Se ha producido un error: ${error}`);        
    }

    return secretWord;
}

/**
 * Función anonima autoinvocada para esperar al resultado
 */
(async () =>
{
    let result = await getSecretWordFromInvalidUserFile(FILE_PATH);
    console.log(result);
})();