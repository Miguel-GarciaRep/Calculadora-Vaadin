package com.example;

import com.vaadin.flow.component.textfield.TextField;

/**
 * ABSTRACCIÓN: define QUÉ se puede hacer (dividir) sin decir CÓMO se hace.
 * La clase Calculadora la implementa y aporta el detalle.
 */
public interface Dividir {
    String dividir(TextField n1 , TextField n2);
}
