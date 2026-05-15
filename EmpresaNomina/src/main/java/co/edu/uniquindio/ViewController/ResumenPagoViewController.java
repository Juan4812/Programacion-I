package co.edu.uniquindio.ViewController;

import co.edu.uniquindio.App.App;
import co.edu.uniquindio.Controller.ResumenPagoController;
import co.edu.uniquindio.Model.ResumenPago;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.text.NumberFormat;
import java.util.Locale;

public class ResumenPagoViewController {

    private final ResumenPagoController resumenPagoController = new ResumenPagoController(App.empresa);
    private final NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    private App app;

    @FXML
    private Label lblNombreEmpresa;

    @FXML
    private TextArea txtResumenPagos;

    @FXML
    void initialize() {
        lblNombreEmpresa.setText(App.empresa.getNombre());
        generarResumenPagos();
    }

    @FXML
    void onActualizarResumen() {
        generarResumenPagos();
    }

    @FXML
    void onVolverMenu() {
        app.openMenuPrincipal();
    }

    private void generarResumenPagos() {
        StringBuilder resumen = new StringBuilder();

        for (ResumenPago pago : resumenPagoController.obtenerResumenesPago()) {
            resumen.append("Documento: ").append(pago.documento()).append(System.lineSeparator());
            resumen.append("Nombre: ").append(pago.nombre()).append(System.lineSeparator());
            resumen.append("Tipo: ").append(pago.tipoEmpleado()).append(System.lineSeparator());
            resumen.append("Salario bruto: ").append(formatoMoneda.format(pago.salarioBruto())).append(System.lineSeparator());
            resumen.append("Descuentos: ").append(formatoMoneda.format(pago.descuentos())).append(System.lineSeparator());
            resumen.append("Salario neto: ").append(formatoMoneda.format(pago.salarioNeto())).append(System.lineSeparator());
            resumen.append("--------------------------------").append(System.lineSeparator());
        }

        if (resumen.isEmpty()) {
            resumen.append("No hay empleados registrados.");
        }

        txtResumenPagos.setText(resumen.toString());
    }

    public void setApp(App app) {
        this.app = app;
    }
}
