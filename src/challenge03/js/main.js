"use strict";

const FILE_PATH = "https://codember.dev/data/encryption_policies.txt";
const SEARCHED_CHARACTER = 13;
const REGEX = /^(\d+)-(\d+) ([A-Za-z]): ([A-Za-z]+)$/;

/**
 * Método que verifica si una clave cumple con su política
 * al leer la línea que incluye ambos.
 * 
 * Para ello, después de obtener cada parte de la línea usando los grupos de las
 * expresiones regulares, convertimos la clave en un array de letras y utilizamos los
 * métodos de array para filtrar por el caracter buscado, contando sus apariciones
 * con el tamaño del array.
 * 
 * @param {string} info - Línea con clave y su política.
 * @returns {boolean} - Devuelve true si la clave cumple con su política.
 */
let isValid = info => 
{
    let minAppearance = 0;
    let maxAppearance = 0;
    let searchedCharacter;
    let key = "";
    let isValid = false;
    let appearance = 0;
    let groups = [];

    if (info !== null && typeof info === "string" && REGEX.test(info)) 
    {
        groups = REGEX.exec(info);
        minAppearance = groups[1];
        maxAppearance = groups[2];
        searchedCharacter = groups[3];
        key = groups[4];

        appearance = Array.from(key).filter(currentValue => currentValue === searchedCharacter).length;

        isValid = appearance >= minAppearance && appearance <= maxAppearance;        
    }

    return isValid;
}

/**
 * Método que busca la clave inválida indicada en un string con claves y sus 
 * políticas en una sola línea.
 * 
 * Para ello, convierte el string en un array separando elementos por saltos de línea,
 * y filtra usando {@link isValid()} las claves que no cumplen con su política.
 * Finalmente, obtenemos solo la clave separando por partes la línea obtenida.
 * 
 * @param {string} lines - String con claves y sus políticas en una sola línea.
 * @param {number} index - Índice de la clave inválida buscada.
 * @returns {string} - Devuelve la clave inválida indicada.
 */
let findInvalidPassword = (lines, index) =>
{
    let info = (typeof lines === "string" && typeof index === "number")?
    lines.split("\n").filter(line => !isValid(line))[index-1] 
    : null;

    return info !== null ? info.split(" ")[2] : "";
}

/**
 * Método que busca la clave válida indicada en un string con claves y sus 
 * políticas en una sola línea.
 * 
 * Para ello, convierte el string en un array separando elementos por saltos de línea,
 * y filtra usando {@link isValid()} las claves que no cumplen con su política.
 * Finalmente, obtenemos solo la clave separando por partes la línea obtenida.
 * 
 * @param {string} lines - String con claves y sus políticas en una sola línea.
 * @param {number} index - Índice de la clave válida buscada.
 * @returns {string} - Devuelve la clave válida indicada.
 */
let findValidPassword = (lines, index) =>
{
    let info = (typeof lines === "string" && typeof index === "number")?
    lines.split("\n").filter(line => isValid(line))[index-1] 
    : null;

    return info !== null ? info.split(" ")[2] : "";
}

/**
 * Fetch para leer el archivo desde la URL.
 */
fetch(FILE_PATH)
.then(response => response.ok? response.text() : Promise.reject("File not found"))
.then(data => {console.log(findInvalidPassword(data, SEARCHED_CHARACTER))})
.catch(error =>
{
    console.log(error === "File not Found" ? `Se ha producido un error al leer el archivo ${error}` :
        `Se ha producido un error: ${error}`);
});
