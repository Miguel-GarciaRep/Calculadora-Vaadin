package com.example;

import com.vaadin.flow.component.textfield.TextField;

/**
 * ABSTRACCIÓN: define QUÉ se puede hacer (sumar) sin decir CÓMO se hace.
 * La clase Calculadora la implementa y aporta el detalle.
 */
public interface Sumar {
    String sumar(TextField n1, TextField n2);
}