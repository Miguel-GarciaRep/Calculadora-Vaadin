package com.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.function.BiFunction;

/**
 * Vista principal de la calculadora.
 *
 * Conceptos de POO que se aplican en esta clase:
 *  - HERENCIA:       extends VerticalLayout (hereda add(), setSizeFull(), etc.).
 *  - ABSTRACCIÓN:    implements Sumar, Restar, Multiplicar, Dividir (contratos).
 *  - POLIMORFISMO:   cada interfaz se cumple con @Override, y calcular() recibe
 *                    cualquier operación como BiFunction (this::sumar, this::restar...).
 *  - ENCAPSULAMIENTO: aNumero(), formatear(), calcular() y mostrarError()
 *                    son private: el resto del programa no los ve.
 */
@PageTitle("Calculadora · Vaadin")
@Route("/")
public class Calculadora extends VerticalLayout implements Sumar, Restar, Multiplicar, Dividir {

    public Calculadora() {

        addClassName("pagina-calculadora");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout tarjeta = new VerticalLayout();
        tarjeta.addClassName("tarjeta-calculadora");
        tarjeta.setPadding(false);
        tarjeta.setSpacing(false);

        H1 titulo = new H1("Calculadora");
        titulo.addClassName("titulo-calculadora");

        Paragraph subtitulo = new Paragraph("Suma, resta, multiplica o divide dos números.");
        subtitulo.addClassName("subtitulo-calculadora");

        TextField num1 = new TextField("Número 1");
        num1.addClassName("campo-numero");
        num1.setPlaceholder("Ej. 12");
        num1.setHelperText("Acepta decimales: 3,5 o 3.5");
        num1.setClearButtonVisible(true);

        TextField num2 = new TextField("Número 2");
        num2.addClassName("campo-numero");
        num2.setPlaceholder("Ej. 4");
        num2.setHelperText("También acepta decimales");
        num2.setClearButtonVisible(true);

        HorizontalLayout filaCampos = new HorizontalLayout(num1, num2);
        filaCampos.addClassName("fila-campos");
        filaCampos.setWidthFull();

        TextField resultado = new TextField("Resultado");
        resultado.addClassName("resultado-calculadora");
        resultado.setReadOnly(true);
        resultado.setPlaceholder("0");
        resultado.setWidthFull();

        Button sumar = new Button("Sumar");
        sumar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sumar.addClickShortcut(Key.ENTER);

        Button restar = new Button("Restar");
        Button multiplicar = new Button("Multiplicar");
        Button dividir = new Button("Dividir");

        Button limpiar = new Button("Limpiar", VaadinIcon.ERASER.create());
        limpiar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        limpiar.addClassName("boton-limpiar");
        limpiar.addClickListener(event -> {
            num1.clear();
            num2.clear();
            resultado.clear();
            resultado.setInvalid(false);
            num1.focus();
        });

        Div rejillaBotones = new Div(sumar, restar, multiplicar, dividir, limpiar);
        rejillaBotones.addClassName("rejilla-botones");

        Paragraph pie = new Paragraph("Hecho con Vaadin + Spring Boot");
        pie.addClassName("pie-calculadora");

        tarjeta.add(titulo, subtitulo, filaCampos, resultado, rejillaBotones, pie);
        add(tarjeta);

        sumar.addClickListener(event -> calcular(num1, num2, resultado, this::sumar));
        restar.addClickListener(event -> calcular(num1, num2, resultado, this::restar));
        multiplicar.addClickListener(event -> calcular(num1, num2, resultado, this::multiplicar));
        dividir.addClickListener(event -> calcular(num1, num2, resultado, this::dividir));
    }

    // POLIMORFISMO: "operacion" puede ser sumar, restar, multiplicar o dividir;
    // este método no necesita saber cuál es, solo la ejecuta.
    // ENCAPSULAMIENTO: es private, solo se usa dentro de esta clase.
    private void calcular(TextField num1, TextField num2, TextField resultado,
                          BiFunction<TextField, TextField, String> operacion) {
        try {
            resultado.setValue(operacion.apply(num1, num2));
            resultado.setInvalid(false);
        } catch (NumberFormatException ex) {
            mostrarError(resultado, "Escribe dos números válidos");
        } catch (ArithmeticException ex) {
            mostrarError(resultado, ex.getMessage());
        }
    }

    private void mostrarError(TextField campo, String mensaje) {
        campo.clear();
        campo.setInvalid(true);
        campo.setErrorMessage(mensaje);
        Notification.show(mensaje, 2500, Notification.Position.BOTTOM_CENTER);
    }

    // ENCAPSULAMIENTO: detalle interno (convierte "3,5" en 3.5) oculto al exterior.
    private double aNumero(TextField campo) {
        return Double.parseDouble(campo.getValue().replace(",", "."));
    }

    private String formatear(double valor) {
        if (Double.isFinite(valor) && valor == Math.rint(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }

    // POLIMORFISMO (sobrescritura): implementación concreta del contrato Sumar.
    @Override
    public String sumar(TextField num1, TextField num2) {
        return formatear(aNumero(num1) + aNumero(num2));
    }

    @Override
    public String restar(TextField num1, TextField num2) {
        return formatear(aNumero(num1) - aNumero(num2));
    }

    @Override
    public String multiplicar(TextField num1, TextField num2) {
        return formatear(aNumero(num1) * aNumero(num2));
    }

    @Override
    public String dividir(TextField num1, TextField num2) {
        double divisor = aNumero(num2);
        if (divisor == 0) {
            throw new ArithmeticException("No se puede dividir entre cero");
        }
        return formatear(aNumero(num1) / divisor);
    }
}
