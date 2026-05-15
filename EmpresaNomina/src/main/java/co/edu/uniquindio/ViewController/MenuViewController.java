package co.edu.uniquindio.ViewController;

import co.edu.uniquindio.App.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MenuViewController {

    private App app;

    @FXML
    private Label lblNombreEmpresa;

    @FXML
    void initialize() {
        lblNombreEmpresa.setText(App.empresa.getNombre());
    }

    @FXML
    void onAbrirEmpresa() {
        app.openViewPrincipal();
    }

    @FXML
    void onRegistrarEmpleado() {
        app.openRegistrarEmpleado();
    }

    @FXML
    void onEmpleadosRegistrados() {
        app.openEmpleadosRegistrados();
    }

    @FXML
    void onResumenPagos() {
        app.openResumenPagos();
    }

    @FXML
    void onConsultas() {
        app.openConsultas();
    }

    public void setApp(App app) {
        this.app = app;
        lblNombreEmpresa.setText(App.empresa.getNombre());
    }
}
