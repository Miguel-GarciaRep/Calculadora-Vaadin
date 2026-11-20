package com.example;

import com.vaadin.flow.component.textfield.TextField;

/**
 * ABSTRACCIÓN: define QUÉ se puede hacer (multiplicar) sin decir CÓMO se hace.
 * La clase Calculadora la implementa y aporta el detalle.
 */
public interface Multiplicar {
    String multiplicar(TextField n1 , TextField n2);
}
