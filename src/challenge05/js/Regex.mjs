"use strict";

/**
 * Módulo Regex que contiene las regulares necesarias para realizar el reto y métodos para validar.
 * 
 * Se usan metodos get para que no se puedan modificar las expresiones ya que javascript no tiene soporte
 * para constantes estaticas.
 * @module Regex
 */
export class Regex
{
    static get ALPHANUMERIC_REGEX()
    {
        return /^[a-zA-Z0-9]+$/;
    }

    static get EMAIL_REGEX()
    {
        return /^(?=.{1,64}@)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(?:\.[A-Za-z]{2,})+$/;
    }

    static get NUMBER_REGEX()
    {
        return /^[0-9]+$/;
    }

    static get TEXT_REGEX()
    {
        return /^[a-zA-Z ]+$/;
    }

    /**
     * Método estático que verifica si un campo requerido cumple con una expresión regular dada.
     *
     * @param {string} field - El campo a validar.
     * @param {RegExp} regex - La expresión regular a comparar.
     * @returns {boolean} - true si el campo no está vacío y cumple con la expresión regular, false de lo contrario.
     * @static
     */
    static checkRequiredField(field, regex)
    {
        if (typeof field !== "string" || !regex instanceof RegExp) 
        {
            return false;            
        }

        return field.length > 0 && regex.test(field);
    }

    /**
     * Método estático que verifica si un campo opcional cumple con una expresión regular dada.
     *
     * @param {string} field - El campo a validar.
     * @param {RegExp} regex - La expresión regular a comparar.
     * @returns {boolean} - true si el campo está vacío o cumple con la expresión regular, false de lo contrario.
     * @static
     */
    static checkOptionalField(field, regex)
    {
        if (typeof field !== "string" || !regex instanceof RegExp) 
        {
            return false;            
        }

        return field.length === 0 || regex.test(field);
    }
}