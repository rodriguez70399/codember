"use strict";

const FILE_PATH = "https://codember.dev/data/encryption_policies.txt";
const SEARCHED_KEY = 42;
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
    let appearance = 0;
    let groups = [];

    if (info === null || typeof info !== "string" || !REGEX.test(info)) 
    {
        return false;
    }

    groups = REGEX.exec(info);
    const [ ,minAppearance, maxAppearance, searchedCharacter, key] = groups;

    appearance = Array.from(key).filter(currentValue => currentValue === searchedCharacter).length;   
    

    return appearance >= minAppearance && appearance <= maxAppearance;
}

/**
 * Método que busca la clave inválida indicada en un archivo con claves y sus 
 * políticas en una sola línea.
 * 
 * Ejemplo usando promesas.
 * 
 * Para ello, convierte el string en un array separando elementos por saltos de línea,
 * y filtra usando {@link isValid()} las claves que no cumplen con su política.
 * Finalmente, obtenemos solo la clave separando por partes la línea obtenida.
 * 
 * @param {string} filePath - String con la url del archivo de claves.
 * @param {number} index - Índice de la clave inválida buscada.
 */
let findInvalidPassword = (filePath, index) =>
{
    return new Promise((resolve, reject) => 
    {
        let info;

        if (typeof filePath !== "string" || typeof index !== "number")
        {
            reject("Invalid arguments");
        }

        fetch(filePath)
            .then(response => response.ok ? response.text() : Promise.reject("File not found"))
            .then(data => 
            {
                info = data.split("\n").filter(line => !isValid(line))[index - 1]

                resolve(info !== undefined ? info.split(" ")[2] : "No se encuentra");
            })
            .catch(error =>
            {
                console.log(error === "File not Found" ? `Se ha producido un error al leer el archivo ${error}` :
                    `Se ha producido un error: ${error}`);
                reject(error);
            });
    });
};

/**
 * Método que busca la clave válida indicada en un archivo con claves y sus 
 * políticas en una sola línea.
 * 
 * Ejemplo usando async y await.
 * 
 * Para ello, convierte el string en un array separando elementos por saltos de línea,
 * y filtra usando {@link isValid()} las claves que no cumplen con su política.
 * Finalmente, obtenemos solo la clave separando por partes la línea obtenida.
 * 
 * @param {string} filePath - String con la url del archivo de claves.
 * @param {number} index - Índice de la clave válida buscada.
 * @returns {string} - Devuelve la clave válida indicada.
 */
let findValidPassword = async (filePath, index) =>
{
    let info;
    let response;
    let data;

    if (typeof filePath !== "string" || typeof index !== "number") 
    {
        return "Argumentos invalidos";
    }

    try 
    {
        response = await fetch(filePath);

        if (!response.ok) 
        {
            throw new Error("File not found");
        }

        data = await response.text();
        info = data.split("\n").filter(line => isValid(line))[index - 1];
    }
    catch (error) 
    {
        console.log(error === "File not Found" ? `Se ha producido un error al leer el archivo ${error}` :
            `Se ha producido un error: ${error}`);
    }

    return info !== undefined ? info.split(" ")[2] : "No se encuentra";
}

/**
 * Resolucion de promesa para el resultado del metodo findInvalidPassword
 */
findInvalidPassword(FILE_PATH, SEARCHED_KEY)
.then(result => {console.log("Clave invalida", SEARCHED_KEY, ": ", result);})
.catch(error => {console.log(error);});

/**
 * Funcion anonima autoinvocada para esperar a resultado del metodo asincrono findValidPassword
 */
(async () =>
{
    let result = await findValidPassword(FILE_PATH, 13);
    console.log("Clave valida", 13, ": ", result);
})();
