package co.edu.uniquindio.ViewController;

import co.edu.uniquindio.App.App;
import co.edu.uniquindio.Controller.ConsultaController;
import co.edu.uniquindio.Controller.EmpleadoController;
import co.edu.uniquindio.Model.CategoriaEmpleado;
import co.edu.uniquindio.Model.Empleado;
import co.edu.uniquindio.Model.EmpleadoPlanta;
import co.edu.uniquindio.Model.EmpleadoTemporal;
import co.edu.uniquindio.Model.EmpleadoVentas;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EmpleadoViewController {

    private final EmpleadoController empleadoController = new EmpleadoController(App.empresa);
    private final ConsultaController consultaController = new ConsultaController(App.empresa);
    private final ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
    private final ObservableList<Empleado> listaConsulta = FXCollections.observableArrayList();
    private final NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    private Empleado empleadoSeleccionado;
    private App app;

    @FXML
    private AnchorPane panelRegistro;
    @FXML
    private AnchorPane panelListado;
    @FXML
    private AnchorPane panelConsultas;
    @FXML
    private Label lblNombreEmpresa;

    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtSalarioBase;
    @FXML
    private TextField txtDescuentoSalud;
    @FXML
    private TextField txtDescuentoPension;
    @FXML
    private ComboBox<CategoriaEmpleado> cbCategoria;
    @FXML
    private ComboBox<String> cbTipoEmpleado;

    @FXML
    private GridPane panelPlanta;
    @FXML
    private TextField txtCargo;
    @FXML
    private TextField txtHorasExtra;
    @FXML
    private TextField txtValorHoraExtra;
    @FXML
    private TextField txtAuxilioTransporte;

    @FXML
    private GridPane panelTemporal;
    @FXML
    private TextField txtDiasTrabajados;
    @FXML
    private TextField txtValorDia;

    @FXML
    private GridPane panelVentas;
    @FXML
    private TextField txtTotalVentas;
    @FXML
    private TextField txtPorcentajeComision;

    @FXML
    private TableView<Empleado> tblListEmpleado;
    @FXML
    private TableColumn<Empleado, String> tbcTipo;
    @FXML
    private TableColumn<Empleado, String> tbcDocumento;
    @FXML
    private TableColumn<Empleado, String> tbcNombre;
    @FXML
    private TableColumn<Empleado, Number> tbcEdad;
    @FXML
    private TableColumn<Empleado, String> tbcCategoria;
    @FXML
    private TableColumn<Empleado, Number> tbcSalarioNeto;

    @FXML
    private TextField txtBuscarDocumento;
    @FXML
    private TextField txtSalarioMayor;
    @FXML
    private Label lblNominaTotalConsulta;
    @FXML
    private TextArea txtResultadoConsulta;
    @FXML
    private TableView<Empleado> tblConsulta;
    @FXML
    private TableColumn<Empleado, String> tbcConsultaTipo;
    @FXML
    private TableColumn<Empleado, String> tbcConsultaDocumento;
    @FXML
    private TableColumn<Empleado, String> tbcConsultaNombre;
    @FXML
    private TableColumn<Empleado, Number> tbcConsultaEdad;
    @FXML
    private TableColumn<Empleado, String> tbcConsultaCategoria;
    @FXML
    private TableColumn<Empleado, Number> tbcConsultaSalarioNeto;

    @FXML
    void initialize() {
        cbTipoEmpleado.setItems(FXCollections.observableArrayList("Planta", "Temporal", "Ventas"));
        cbCategoria.setItems(FXCollections.observableArrayList(CategoriaEmpleado.values()));
        lblNombreEmpresa.setText(App.empresa.getNombre());
        cbTipoEmpleado.getSelectionModel().select("Planta");
        cbCategoria.getSelectionModel().select(CategoriaEmpleado.JUNIOR);

        inicializarTablaEmpleados();
        inicializarTablaConsultas();
        escucharSeleccionTabla();
        escucharTipoEmpleado();
        cargarEmpleados();
        mostrarPanel(panelRegistro);
    }

    @FXML
    void onMostrarRegistro() {
        mostrarPanel(panelRegistro);
    }

    @FXML
    void onMostrarListado() {
        cargarEmpleados();
        mostrarPanel(panelListado);
    }

    @FXML
    void onMostrarConsultas() {
        actualizarNominaConsulta();
        txtResultadoConsulta.clear();
        listaConsulta.clear();
        mostrarPanel(panelConsultas);
    }

    @FXML
    void onAgregarEmpleado() {
        try {
            Empleado empleado = construirEmpleado();
            if (empleadoController.crearEmpleado(empleado)) {
                cargarEmpleados();
                limpiarCampos();
                mostrarMensaje("Empleado agregado correctamente.");
            }
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void onActualizarEmpleado() {
        if (empleadoSeleccionado == null) {
            mostrarError("Seleccione un empleado en la tabla de empleados registrados.");
            return;
        }

        try {
            Empleado empleadoActualizado = construirEmpleado();
            if (empleadoController.actualizarEmpleado(empleadoSeleccionado.getDocumento(), empleadoActualizado)) {
                cargarEmpleados();
                limpiarSeleccion();
                mostrarMensaje("Empleado actualizado correctamente.");
            }
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    void onEliminar() {
        if (empleadoSeleccionado == null) {
            mostrarError("Seleccione un empleado en la tabla de empleados registrados.");
            return;
        }

        if (empleadoController.eliminarEmpleado(empleadoSeleccionado.getDocumento())) {
            cargarEmpleados();
            limpiarSeleccion();
            mostrarMensaje("Empleado eliminado correctamente.");
        }
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onBuscarEmpleado() {
        String documento = txtBuscarDocumento.getText().trim();
        if (documento.isEmpty()) {
            mostrarError("Ingrese el documento a buscar.");
            return;
        }

            Empleado empleado = consultaController.buscarEmpleadoPorDocumento(documento);
        if (empleado == null) {
            listaConsulta.clear();
            txtResultadoConsulta.setText("Empleado no encontrado.");
            return;
        }

        listaConsulta.setAll(empleado);
        txtResultadoConsulta.setText(describirEmpleado(empleado));
    }

    @FXML
    void onEmpleadoMayorSueldo() {
        Empleado empleado = consultaController.obtenerEmpleadoConMayorSalarioNeto();
        if (empleado == null) {
            listaConsulta.clear();
            txtResultadoConsulta.setText("No hay empleados registrados.");
            return;
        }

        listaConsulta.setAll(empleado);
        txtResultadoConsulta.setText("Empleado con mayor salario neto:\n\n" + describirEmpleado(empleado));
    }

    @FXML
    void onNominaTotal() {
        float nomina = consultaController.calcularNominaTotal();
        lblNominaTotalConsulta.setText("Nomina total: " + formatoMoneda.format(nomina));
        txtResultadoConsulta.setText("Nomina total: " + formatoMoneda.format(nomina));
    }

    @FXML
    void onBuscarPorSalario() {
        try {
            float salario = Float.parseFloat(txtSalarioMayor.getText().trim());
            List<Empleado> empleados = consultaController.obtenerEmpleadosConSalarioMayorA(salario);
            listaConsulta.setAll(empleados);
            txtResultadoConsulta.setText("Empleados con salario neto mayor a " + formatoMoneda.format(salario) + ": " + empleados.size());
        } catch (NumberFormatException e) {
            mostrarError("Ingrese un salario valido.");
        }
    }

    @FXML
    void onTemporalesMas100Horas() {
        List<EmpleadoTemporal> temporales = consultaController.obtenerEmpleadosTemporalesConMasDe100Horas();
        listaConsulta.setAll(temporales);
        txtResultadoConsulta.setText("Empleados temporales con mas de 100 horas: " + temporales.size());
    }

    @FXML
    void onVolverMenu() {
        app.openMenuPrincipal();
    }

    public void mostrarSeccion(String seccion) {
        if ("listado".equals(seccion)) {
            cargarEmpleados();
            mostrarPanel(panelListado);
        } else if ("consultas".equals(seccion)) {
            actualizarNominaConsulta();
            txtResultadoConsulta.clear();
            listaConsulta.clear();
            mostrarPanel(panelConsultas);
        } else {
            mostrarPanel(panelRegistro);
        }
    }

    private void inicializarTablaEmpleados() {
        configurarColumnas(tbcTipo, tbcDocumento, tbcNombre, tbcEdad, tbcCategoria, tbcSalarioNeto);
        tblListEmpleado.setItems(listaEmpleados);
    }

    private void inicializarTablaConsultas() {
        configurarColumnas(tbcConsultaTipo, tbcConsultaDocumento, tbcConsultaNombre, tbcConsultaEdad, tbcConsultaCategoria, tbcConsultaSalarioNeto);
        tblConsulta.setItems(listaConsulta);
    }

    private void configurarColumnas(TableColumn<Empleado, String> colTipo,
                                    TableColumn<Empleado, String> colDocumento,
                                    TableColumn<Empleado, String> colNombre,
                                    TableColumn<Empleado, Number> colEdad,
                                    TableColumn<Empleado, String> colCategoria,
                                    TableColumn<Empleado, Number> colSalarioNeto) {
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(obtenerTipoEmpleado(cellData.getValue())));
        colDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocumento()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colEdad.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().name()));
        colSalarioNeto.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().calcularSalarioNeto()));
        colSalarioNeto.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Number salario, boolean empty) {
                super.updateItem(salario, empty);
                if (empty || salario == null) {
                    setText(null);
                } else {
                    setText(formatoMoneda.format(salario.floatValue()));
                }
            }
        });
    }

    private void escucharSeleccionTabla() {
        tblListEmpleado.getSelectionModel().selectedItemProperty().addListener((observable, anterior, seleccionado) -> {
            empleadoSeleccionado = seleccionado;
            mostrarEmpleadoSeleccionado(seleccionado);
        });
    }

    private void escucharTipoEmpleado() {
        cbTipoEmpleado.valueProperty().addListener((observable, anterior, tipo) -> mostrarCamposPorTipo(tipo));
        mostrarCamposPorTipo(cbTipoEmpleado.getValue());
    }

    private void cargarEmpleados() {
        listaEmpleados.setAll(empleadoController.obtenerListaEmpleados());
        actualizarNominaConsulta();
    }

    private void mostrarPanel(AnchorPane panelVisible) {
        panelRegistro.setVisible(panelVisible == panelRegistro);
        panelListado.setVisible(panelVisible == panelListado);
        panelConsultas.setVisible(panelVisible == panelConsultas);
    }

    private void mostrarEmpleadoSeleccionado(Empleado empleado) {
        if (empleado == null) {
            return;
        }

        txtDocumento.setText(empleado.getDocumento());
        txtNombre.setText(empleado.getNombre());
        txtEdad.setText(String.valueOf(empleado.getEdad()));
        txtSalarioBase.setText(String.valueOf(empleado.getSalarioBase()));
        txtDescuentoSalud.setText(String.valueOf(empleado.getDescuentoSalud()));
        txtDescuentoPension.setText(String.valueOf(empleado.getDescuentoPension()));
        cbCategoria.setValue(empleado.getCategoria());

        limpiarCamposEspecificos();

        if (empleado instanceof EmpleadoPlanta empleadoPlanta) {
            cbTipoEmpleado.setValue("Planta");
            txtCargo.setText(empleadoPlanta.getCargo());
            txtHorasExtra.setText(String.valueOf(empleadoPlanta.getHorasExtra()));
            txtValorHoraExtra.setText(String.valueOf(empleadoPlanta.getValorHoraExtra()));
            txtAuxilioTransporte.setText(String.valueOf(empleadoPlanta.getAuxilioTransporte()));
        } else if (empleado instanceof EmpleadoTemporal empleadoTemporal) {
            cbTipoEmpleado.setValue("Temporal");
            txtDiasTrabajados.setText(String.valueOf(empleadoTemporal.getDiasTrabajados()));
            txtValorDia.setText(String.valueOf(empleadoTemporal.getValorDia()));
        } else if (empleado instanceof EmpleadoVentas empleadoVentas) {
            cbTipoEmpleado.setValue("Ventas");
            txtTotalVentas.setText(String.valueOf(empleadoVentas.getTotalVentas()));
            txtPorcentajeComision.setText(String.valueOf(empleadoVentas.getPorcentajeComision()));
        }
    }

    private Empleado construirEmpleado() {
        String documento = leerTexto(txtDocumento, "documento");
        String nombre = leerTexto(txtNombre, "nombre");
        int edad = leerEntero(txtEdad, "edad");
        float salarioBase = leerFloat(txtSalarioBase, "salario base");
        CategoriaEmpleado categoria = cbCategoria.getValue();
        float descuentoSalud = leerFloat(txtDescuentoSalud, "descuento salud");
        float descuentoPension = leerFloat(txtDescuentoPension, "descuento pension");

        String tipo = cbTipoEmpleado.getValue();
        if ("Temporal".equals(tipo)) {
            return new EmpleadoTemporal(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension,
                    leerEntero(txtDiasTrabajados, "dias trabajados"),
                    leerFloat(txtValorDia, "valor dia"));
        }

        if ("Ventas".equals(tipo)) {
            return new EmpleadoVentas(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension,
                    leerFloat(txtTotalVentas, "total ventas"),
                    leerFloat(txtPorcentajeComision, "porcentaje comision"));
        }

        return new EmpleadoPlanta(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension,
                leerTexto(txtCargo, "cargo"),
                leerEntero(txtHorasExtra, "horas extra"),
                leerFloat(txtValorHoraExtra, "valor hora extra"),
                leerFloat(txtAuxilioTransporte, "auxilio transporte"));
    }

    private void mostrarCamposPorTipo(String tipo) {
        boolean planta = "Planta".equals(tipo);
        boolean temporal = "Temporal".equals(tipo);
        boolean ventas = "Ventas".equals(tipo);

        panelPlanta.setVisible(planta);
        panelPlanta.setManaged(planta);
        panelTemporal.setVisible(temporal);
        panelTemporal.setManaged(temporal);
        panelVentas.setVisible(ventas);
        panelVentas.setManaged(ventas);
    }

    private String describirEmpleado(Empleado empleado) {
        return "Tipo: " + obtenerTipoEmpleado(empleado) + System.lineSeparator()
                + "Documento: " + empleado.getDocumento() + System.lineSeparator()
                + "Nombre: " + empleado.getNombre() + System.lineSeparator()
                + "Edad: " + empleado.getEdad() + System.lineSeparator()
                + "Categoria: " + empleado.getCategoria() + System.lineSeparator()
                + "Salario neto: " + formatoMoneda.format(empleado.calcularSalarioNeto());
    }

    private String obtenerTipoEmpleado(Empleado empleado) {
        if (empleado instanceof EmpleadoTemporal) return "Temporal";
        if (empleado instanceof EmpleadoVentas) return "Ventas";
        return "Planta";
    }

    private void actualizarNominaConsulta() {
        lblNominaTotalConsulta.setText("Nomina total: " + formatoMoneda.format(consultaController.calcularNominaTotal()));
    }

    private void limpiarSeleccion() {
        tblListEmpleado.getSelectionModel().clearSelection();
        empleadoSeleccionado = null;
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        txtEdad.clear();
        txtSalarioBase.clear();
        txtDescuentoSalud.clear();
        txtDescuentoPension.clear();
        limpiarCamposEspecificos();
        cbTipoEmpleado.getSelectionModel().select("Planta");
        cbCategoria.getSelectionModel().select(CategoriaEmpleado.JUNIOR);
    }

    private void limpiarCamposEspecificos() {
        txtCargo.clear();
        txtHorasExtra.clear();
        txtValorHoraExtra.clear();
        txtAuxilioTransporte.clear();
        txtDiasTrabajados.clear();
        txtValorDia.clear();
        txtTotalVentas.clear();
        txtPorcentajeComision.clear();
    }

    private String leerTexto(TextField campo, String nombreCampo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
        return texto;
    }

    private int leerEntero(TextField campo, String nombreCampo) {
        try {
            return Integer.parseInt(leerTexto(campo, nombreCampo));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser entero.");
        }
    }

    private float leerFloat(TextField campo, String nombreCampo) {
        try {
            return Float.parseFloat(leerTexto(campo, nombreCampo));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser numerico.");
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
