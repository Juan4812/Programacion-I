package co.edu.uniquindio.ViewController;

import co.edu.uniquindio.App.App;
import co.edu.uniquindio.Controller.EmpresaController;
import co.edu.uniquindio.Model.Empleado;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.Locale;

public class EmpresaViewController {

    private final EmpresaController empresaController = new EmpresaController(App.empresa);
    private final NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    private App app;

    @FXML
    private TextField txtNombreEmpresa;
    @FXML
    private Label lblCantidadEmpleados;
    @FXML
    private Label lblNominaTotal;
    @FXML
    private Label lblMayorSalario;

    @FXML
    void initialize() {
        cargarDatosEmpresa();
    }

    @FXML
    void onActualizarEmpresa() {
        try {
            String nombre = txtNombreEmpresa.getText().trim();
            empresaController.actualizarNombreEmpresa(nombre);
            cargarDatosEmpresa();
            mostrarMensaje("Empresa actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void onOpenCrudEmpleado() {
        app.openRegistrarEmpleado();
    }

    @FXML
    void onVolverMenu() {
        app.openMenuPrincipal();
    }

    private void cargarDatosEmpresa() {
        txtNombreEmpresa.setText(empresaController.obtenerNombreEmpresa());
        lblCantidadEmpleados.setText(String.valueOf(empresaController.obtenerCantidadEmpleados()));
        lblNominaTotal.setText(formatoMoneda.format(empresaController.calcularNominaTotal()));

        Empleado mayorSalario = empresaController.obtenerEmpleadoConMayorSalarioNeto();
        if (mayorSalario == null) {
            lblMayorSalario.setText("Sin empleados");
        } else {
            lblMayorSalario.setText(mayorSalario.getNombre() + " - " + formatoMoneda.format(mayorSalario.calcularSalarioNeto()));
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
