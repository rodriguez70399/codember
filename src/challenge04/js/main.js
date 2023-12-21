"use strict";

const FILE_PATH = "https://codember.dev/data/files_quarantine.txt";
const SEARCHED_FILE = 33;

/**
 * Función que verifica si el checksum indicado cumple con su verdadero checksum.
 * 
 * Se hace copia del string a un array, se recorre cada letra y se crea un objeto Map con
 * las apariciones de cada caracter. Se hace copia de las llaves del Map en array y se filtra
 * por las letras que solo aparezcan una vez para finalmente agruparlas todas en una cadena.
 *
 * @param {string} fileName Nombre de archivo en formato "leftPart-checksum".
 * @returns {boolean} true si la cadena cumple con el checksum esperado, false de lo contrario.
 */
let isValid = fileName => 
{
    let leftPath = "";
    let checksum = "";
    let letterCount = new Map();
    let correctChecksum = "";
    
    if (fileName === null || typeof fileName !== "string" || fileName.split("-").length !== 2)
    {
        return false;
    }

    [leftPath, checksum] = fileName.split("-");

    [...leftPath].forEach(letter =>
    {
        letterCount.set(letter, (letterCount.get(letter) || 0) + 1);
    });

    correctChecksum = [...letterCount.keys()]
        .filter(letter => letterCount.get(letter) === 1)
        .join("");

    return checksum === correctChecksum;
};

/**
 * Función asincrónica que obtiene el archivo real con el indice indicado, comprobando con
 * la función {@link isValid}.
 *
 * @async
 * @param {string} listPath URL del archivo que contiene la lista de nombres de archivo.
 * @param {number} index Índice del archivo buscado.
 * @returns {Promise<string>} Nombre del archivo encontrado o una cadena vacía si no cumple con las condiciones.
 */ 
let getRealFile = async (listPath, index) => 
{
    let searchedFile = "";
    let response;
    let data;

    if (typeof listPath !== "string" || typeof index !== "number") 
    {
        reject("Invalid arguments");
    }

    try 
    {
        response = await fetch(listPath);

        if (!response.ok) 
        {
            throw new Error("File not found");
        }

        data = await response.text();
        searchedFile = data.split("\n").filter(line => isValid(line))[index - 1];
    }
    catch (error) 
    {
        console.log(error === "File not Found" ? `Se ha producido un error al leer el archivo ${error}` :
            `Se ha producido un error: ${error}`);
    }

    return searchedFile;
};

/**
 * Función anonima autoinvocada para esperar al resultado
 */
(async () =>
{
    let result = await getRealFile(FILE_PATH, SEARCHED_FILE);
    console.log(result);
})();