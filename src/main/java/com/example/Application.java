package com.example;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.aura.Aura;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Un @StyleSheet aquí se carga para TODA la aplicación (no solo una vista).
// Aura.STYLESHEET es el tema por defecto de Vaadin 25: de él salen los
// colores, tipografías y las variables --aura-* que se usan en styles.css.
@StyleSheet(Aura.STYLESHEET)
// El archivo está en src/main/resources/META-INF/resources/styles.css
// y se sirve como /styles.css. Aquí vive toda la decoración de la calculadora.
@StyleSheet("styles.css")
// @Push permite que el servidor actualice la pantalla sin recargar
// (útil, por ejemplo, para las notificaciones).
@Push
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
