package solicitar.saludo;

import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolicitarSaludoApp extends Application {

    @Override
    public void start(Stage stage) {
        Button solicitarSaludo = new Button("Solicitar saludo");
        solicitarSaludo.setDefaultButton(true);
        solicitarSaludo.setOnAction(event -> solicitarYMostrarSaludo());

        VBox root = new VBox(16, solicitarSaludo);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(24));

        stage.setTitle("SolicitarSaludo");
        stage.setScene(new Scene(root, 360, 180));
        stage.show();
    }

    private void solicitarYMostrarSaludo() {
        Optional<String> nombre = pedirTexto("Nombre", "Ingrese su nombre:");
        if (nombre.isEmpty()) {
            return;
        }

        Optional<Integer> edad = pedirEdad();
        if (edad.isEmpty()) {
            return;
        }

        Optional<String> horario = pedirHorario();
        if (horario.isEmpty()) {
            return;
        }

        String saludoHorario = "AM".equals(horario.get()) ? "Buenos días" : "Buenas tardes";
        String mensaje = saludoHorario + ", " + nombre.get().trim()
                + ". Tienes " + edad.get() + " años.";

        Alert saludo = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        saludo.setTitle("Saludo");
        saludo.setHeaderText("SolicitarSaludo");
        saludo.showAndWait();
    }

    private Optional<String> pedirTexto(String titulo, String mensaje) {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle(titulo);
        dialogo.setHeaderText("Solicitar saludo");
        dialogo.setContentText(mensaje);

        Optional<String> respuesta = dialogo.showAndWait();
        if (respuesta.isEmpty() || respuesta.get().isBlank()) {
            return Optional.empty();
        }
        return Optional.of(respuesta.get().trim());
    }

    private Optional<Integer> pedirEdad() {
        while (true) {
            Optional<String> texto = pedirTexto("Edad", "Ingrese su edad:");
            if (texto.isEmpty()) {
                return Optional.empty();
            }
            try {
                int edad = Integer.parseInt(texto.get());
                if (edad < 0 || edad > 130) {
                    mostrarError("La edad debe estar entre 0 y 130.");
                    continue;
                }
                return Optional.of(edad);
            } catch (NumberFormatException ex) {
                mostrarError("Ingrese un número entero para la edad.");
            }
        }
    }

    private Optional<String> pedirHorario() {
        ChoiceDialog<String> dialogo = new ChoiceDialog<>("AM", "AM", "PM");
        dialogo.setTitle("Hora");
        dialogo.setHeaderText("Solicitar saludo");
        dialogo.setContentText("Seleccione el horario (AM / PM):");
        return dialogo.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert error = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        error.setTitle("Dato inválido");
        error.setHeaderText("No se pudo leer el dato");
        error.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
