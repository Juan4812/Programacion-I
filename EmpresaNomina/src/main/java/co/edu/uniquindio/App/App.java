package co.edu.uniquindio.App;

import co.edu.uniquindio.Model.CategoriaEmpleado;
import co.edu.uniquindio.Model.Empleado;
import co.edu.uniquindio.Model.EmpleadoPlanta;
import co.edu.uniquindio.Model.EmpleadoTemporal;
import co.edu.uniquindio.Model.EmpleadoVentas;
import co.edu.uniquindio.Model.Empresa;
import co.edu.uniquindio.Model.ResumenPago;
import javafx.application.Application;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;

public class App extends Application {

    public static final Empresa empresa = new Empresa("Nomina UQ");

    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
    private final ObservableList<Empleado> empleadosPantalla = FXCollections.observableArrayList();

    private final ComboBox<String> tipoEmpleado = new ComboBox<>();
    private final ComboBox<CategoriaEmpleado> categoria = new ComboBox<>();
    private final TextField empresaNombre = new TextField();
    private final TextField documento = new TextField();
    private final TextField nombre = new TextField();
    private final TextField edad = new TextField();
    private final TextField salarioBase = new TextField();
    private final TextField descuentoSalud = new TextField("4");
    private final TextField descuentoPension = new TextField("4");
    private final TextField datoUno = new TextField();
    private final TextField datoDos = new TextField();
    private final TextField datoTres = new TextField();
    private final Label etiquetaUno = new Label();
    private final Label etiquetaDos = new Label();
    private final Label etiquetaTres = new Label();
    private final Label totalNomina = new Label();
    private final TextArea salida = new TextArea();
    private final TableView<Empleado> tabla = new TableView<>();

    private Empleado seleccionado;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Nomina de empleados");
        stage.setScene(new Scene(crearVista(), 980, 620));
        refrescar();
        stage.show();
    }

    private BorderPane crearVista() {
        BorderPane raiz = new BorderPane();
        raiz.setPadding(new Insets(16));
        raiz.setStyle("-fx-background-color: #f6f7f9;");

        Label titulo = new Label("Nomina de empleados");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        empresaNombre.setPrefWidth(220);
        empresaNombre.setText(empresa.getNombre());

        Button guardarEmpresa = new Button("Guardar empresa");
        guardarEmpresa.setOnAction(event -> guardarNombreEmpresa());

        HBox encabezado = new HBox(12, titulo, new Label("Empresa:"), empresaNombre, guardarEmpresa, totalNomina);
        encabezado.setPadding(new Insets(0, 0, 14, 0));
        encabezado.setStyle("-fx-alignment: center-left;");

        raiz.setTop(encabezado);
        raiz.setLeft(crearFormulario());
        raiz.setCenter(crearTabla());
        raiz.setBottom(crearConsultas());
        return raiz;
    }

    private VBox crearFormulario() {
        tipoEmpleado.setItems(FXCollections.observableArrayList("Planta", "Temporal", "Ventas"));
        tipoEmpleado.getSelectionModel().selectFirst();
        tipoEmpleado.setOnAction(event -> ajustarCamposPorTipo());

        categoria.setItems(FXCollections.observableArrayList(CategoriaEmpleado.values()));
        categoria.getSelectionModel().select(CategoriaEmpleado.JUNIOR);

        GridPane campos = new GridPane();
        campos.setHgap(8);
        campos.setVgap(8);
        agregarFila(campos, 0, "Tipo", tipoEmpleado);
        agregarFila(campos, 1, "Documento", documento);
        agregarFila(campos, 2, "Nombre", nombre);
        agregarFila(campos, 3, "Edad", edad);
        agregarFila(campos, 4, "Salario base", salarioBase);
        agregarFila(campos, 5, "Categoria", categoria);
        agregarFila(campos, 6, "Salud %", descuentoSalud);
        agregarFila(campos, 7, "Pension %", descuentoPension);
        agregarFila(campos, 8, etiquetaUno, datoUno);
        agregarFila(campos, 9, etiquetaDos, datoDos);
        agregarFila(campos, 10, etiquetaTres, datoTres);

        Button agregar = new Button("Agregar");
        agregar.setMaxWidth(Double.MAX_VALUE);
        agregar.setOnAction(event -> agregarEmpleado());

        Button actualizar = new Button("Actualizar");
        actualizar.setMaxWidth(Double.MAX_VALUE);
        actualizar.setOnAction(event -> actualizarEmpleado());

        Button eliminar = new Button("Eliminar");
        eliminar.setMaxWidth(Double.MAX_VALUE);
        eliminar.setOnAction(event -> eliminarEmpleado());

        Button limpiar = new Button("Limpiar");
        limpiar.setMaxWidth(Double.MAX_VALUE);
        limpiar.setOnAction(event -> limpiarFormulario());

        GridPane acciones = new GridPane();
        acciones.setHgap(8);
        acciones.setVgap(8);
        acciones.add(agregar, 0, 0);
        acciones.add(actualizar, 1, 0);
        acciones.add(eliminar, 0, 1);
        acciones.add(limpiar, 1, 1);

        VBox panel = new VBox(12, new Label("Datos del empleado"), campos, acciones);
        panel.setPadding(new Insets(14));
        panel.setPrefWidth(330);
        panel.setStyle("-fx-background-color: white; -fx-border-color: #d9dde5; -fx-border-radius: 6; -fx-background-radius: 6;");
        ajustarCamposPorTipo();
        return panel;
    }

    private TableView<Empleado> crearTabla() {
        tabla.setItems(empleadosPantalla);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.getColumns().clear();
        tabla.getColumns().add(columnaTexto("Tipo", empleado -> empleado.nombreTipo()));
        tabla.getColumns().add(columnaTexto("Documento", Empleado::getDocumento));
        tabla.getColumns().add(columnaTexto("Nombre", Empleado::getNombre));
        tabla.getColumns().add(columnaNumero("Edad", empleado -> empleado.getEdad()));
        tabla.getColumns().add(columnaTexto("Categoria", empleado -> empleado.getCategoria().name()));
        tabla.getColumns().add(columnaDinero("Neto"));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> cargarSeleccion(actual));
        BorderPane.setMargin(tabla, new Insets(0, 0, 0, 14));
        return tabla;
    }

    private VBox crearConsultas() {
        TextField buscarDocumento = new TextField();
        buscarDocumento.setPromptText("Documento");
        TextField salarioFiltro = new TextField();
        salarioFiltro.setPromptText("Salario mayor a");

        Button buscar = new Button("Buscar");
        buscar.setOnAction(event -> mostrarEmpleado(buscarDocumento.getText()));
        Button mayor = new Button("Mayor sueldo");
        mayor.setOnAction(event -> mostrarMayorSueldo());
        Button filtro = new Button("Filtrar sueldo");
        filtro.setOnAction(event -> filtrarPorSalario(salarioFiltro.getText()));
        Button temporales = new Button("Temporales +100h");
        temporales.setOnAction(event -> listarTemporales());
        Button resumen = new Button("Resumen pagos");
        resumen.setOnAction(event -> mostrarResumenes());
        Button todos = new Button("Ver todos");
        todos.setOnAction(event -> refrescar());

        HBox comandos = new HBox(8, buscarDocumento, buscar, salarioFiltro, filtro, mayor, temporales, resumen, todos);
        salida.setEditable(false);
        salida.setPrefRowCount(4);
        VBox panel = new VBox(8, comandos, salida);
        panel.setPadding(new Insets(14, 0, 0, 0));
        return panel;
    }

    private void agregarEmpleado() {
        try {
            empresa.agregarEmpleado(crearEmpleadoDesdeFormulario());
            limpiarFormulario();
            refrescar();
        } catch (IllegalArgumentException ex) {
            avisar("Revise los datos", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void actualizarEmpleado() {
        if (seleccionado == null) {
            avisar("Seleccione un empleado", "Use la tabla para escoger el registro a modificar.", Alert.AlertType.WARNING);
            return;
        }
        try {
            empresa.actualizarEmpleado(seleccionado.getDocumento(), crearEmpleadoDesdeFormulario());
            limpiarFormulario();
            refrescar();
        } catch (IllegalArgumentException ex) {
            avisar("Revise los datos", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void eliminarEmpleado() {
        if (seleccionado != null && empresa.eliminarEmpleado(seleccionado.getDocumento())) {
            limpiarFormulario();
            refrescar();
        }
    }

    private Empleado crearEmpleadoDesdeFormulario() {
        String tipo = tipoEmpleado.getValue();
        String nom = texto(nombre, "nombre");
        String doc = texto(documento, "documento");
        int ed = entero(edad, "edad");
        float base = decimal(salarioBase, "salario base");
        CategoriaEmpleado cat = categoria.getValue();
        float salud = decimal(descuentoSalud, "salud");
        float pension = decimal(descuentoPension, "pension");

        if ("Temporal".equals(tipo)) {
            return new EmpleadoTemporal(nom, doc, ed, base, cat, salud, pension,
                    entero(datoUno, "dias trabajados"), decimal(datoDos, "valor dia"));
        }
        if ("Ventas".equals(tipo)) {
            return new EmpleadoVentas(nom, doc, ed, base, cat, salud, pension,
                    decimal(datoUno, "total ventas"), decimal(datoDos, "comision"));
        }
        return new EmpleadoPlanta(nom, doc, ed, base, cat, salud, pension,
                texto(datoUno, "cargo"), entero(datoDos, "horas extra"), decimal(datoTres, "valor hora extra"), 0f);
    }

    private void cargarSeleccion(Empleado empleado) {
        seleccionado = empleado;
        if (empleado == null) {
            return;
        }

        tipoEmpleado.setValue(empleado.nombreTipo());
        documento.setText(empleado.getDocumento());
        nombre.setText(empleado.getNombre());
        edad.setText(String.valueOf(empleado.getEdad()));
        salarioBase.setText(String.valueOf(empleado.getSalarioBase()));
        descuentoSalud.setText(String.valueOf(empleado.getDescuentoSalud()));
        descuentoPension.setText(String.valueOf(empleado.getDescuentoPension()));

        if (empleado instanceof EmpleadoTemporal temporal) {
            datoUno.setText(String.valueOf(temporal.getDiasTrabajados()));
            datoDos.setText(String.valueOf(temporal.getValorDia()));
            datoTres.clear();
        } else if (empleado instanceof EmpleadoVentas ventas) {
            datoUno.setText(String.valueOf(ventas.getTotalVentas()));
            datoDos.setText(String.valueOf(ventas.getPorcentajeComision()));
            datoTres.clear();
        } else if (empleado instanceof EmpleadoPlanta planta) {
            datoUno.setText(planta.getCargo());
            datoDos.setText(String.valueOf(planta.getHorasExtra()));
            datoTres.setText(String.valueOf(planta.getValorHoraExtra()));
        }
    }

    private void ajustarCamposPorTipo() {
        String tipo = tipoEmpleado.getValue();
        boolean planta = "Planta".equals(tipo);
        etiquetaUno.setText(planta ? "Cargo" : "Temporal".equals(tipo) ? "Dias" : "Ventas");
        etiquetaDos.setText(planta ? "Horas extra" : "Temporal".equals(tipo) ? "Valor dia" : "Comision %");
        etiquetaTres.setText("Valor hora extra");
        datoTres.setVisible(planta);
        datoTres.setManaged(planta);
        etiquetaTres.setVisible(planta);
        etiquetaTres.setManaged(planta);
    }

    private void guardarNombreEmpresa() {
        try {
            empresa.setNombre(texto(empresaNombre, "nombre de empresa"));
            refrescar();
        } catch (IllegalArgumentException ex) {
            avisar("Revise la empresa", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarEmpleado(String documentoBuscado) {
        Empleado empleado = empresa.buscarEmpleadoPorDocumento(documentoBuscado.trim());
        if (empleado == null) {
            salida.setText("No se encontro un empleado con ese documento.");
            return;
        }
        empleadosPantalla.setAll(empleado);
        salida.setText(empleado.resumenTexto());
    }

    private void mostrarMayorSueldo() {
        Empleado empleado = empresa.obtenerEmpleadoConMayorSalarioNeto();
        if (empleado == null) {
            salida.setText("Todavia no hay empleados registrados.");
            return;
        }
        empleadosPantalla.setAll(empleado);
        salida.setText("Empleado con mayor salario neto:" + System.lineSeparator() + empleado.resumenTexto());
    }

    private void filtrarPorSalario(String texto) {
        try {
            float minimo = Float.parseFloat(texto.trim());
            empleadosPantalla.setAll(empresa.empleadosConSalarioMayorA(minimo));
            salida.setText("Registros encontrados: " + empleadosPantalla.size());
        } catch (NumberFormatException ex) {
            avisar("Valor invalido", "Ingrese un salario numerico.", Alert.AlertType.ERROR);
        }
    }

    private void listarTemporales() {
        empleadosPantalla.setAll(empresa.empleadosTemporalesConMasDe100Horas());
        salida.setText("Temporales con mas de 100 horas: " + empleadosPantalla.size());
    }

    private void mostrarResumenes() {
        if (empresa.getListaEmpleados().isEmpty()) {
            salida.setText("No hay empleados registrados.");
            return;
        }

        StringBuilder texto = new StringBuilder();
        for (Empleado empleado : empresa.getListaEmpleados()) {
            ResumenPago pago = empleado.generarResumenPago();
            texto.append(pago.nombre())
                    .append(" | ").append(pago.tipoEmpleado())
                    .append(" | Bruto: ").append(moneda.format(pago.salarioBruto()))
                    .append(" | Desc: ").append(moneda.format(pago.descuentos()))
                    .append(" | Neto: ").append(moneda.format(pago.salarioNeto()))
                    .append(System.lineSeparator());
        }
        salida.setText(texto.toString());
    }

    private void refrescar() {
        empleadosPantalla.setAll(empresa.getListaEmpleados());
        totalNomina.setText("Nomina total: " + moneda.format(empresa.calcularNominaTotal()));
        salida.setText("Empleados registrados: " + empleadosPantalla.size());
    }

    private void limpiarFormulario() {
        seleccionado = null;
        tabla.getSelectionModel().clearSelection();
        documento.clear();
        nombre.clear();
        edad.clear();
        salarioBase.clear();
        datoUno.clear();
        datoDos.clear();
        datoTres.clear();
        descuentoSalud.setText("4");
        descuentoPension.setText("4");
        tipoEmpleado.getSelectionModel().selectFirst();
        categoria.getSelectionModel().select(CategoriaEmpleado.JUNIOR);
    }

    private String texto(TextField campo, String nombreCampo) {
        String valor = campo.getText().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio.");
        }
        return valor;
    }

    private int entero(TextField campo, String nombreCampo) {
        try {
            return Integer.parseInt(texto(campo, nombreCampo));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser entero.");
        }
    }

    private float decimal(TextField campo, String nombreCampo) {
        try {
            return Float.parseFloat(texto(campo, nombreCampo));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " debe ser numerico.");
        }
    }

    private void avisar(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void agregarFila(GridPane grilla, int fila, String etiqueta, javafx.scene.Node campo) {
        agregarFila(grilla, fila, new Label(etiqueta), campo);
    }

    private void agregarFila(GridPane grilla, int fila, Label etiqueta, javafx.scene.Node campo) {
        grilla.add(etiqueta, 0, fila);
        grilla.add(campo, 1, fila);
        GridPane.setHgrow(campo, Priority.ALWAYS);
    }

    private TableColumn<Empleado, String> columnaTexto(String titulo, java.util.function.Function<Empleado, String> valor) {
        TableColumn<Empleado, String> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(data -> new SimpleStringProperty(valor.apply(data.getValue())));
        return columna;
    }

    private TableColumn<Empleado, Number> columnaNumero(String titulo, java.util.function.Function<Empleado, Number> valor) {
        TableColumn<Empleado, Number> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(data -> new SimpleIntegerProperty(valor.apply(data.getValue()).intValue()));
        return columna;
    }

    private TableColumn<Empleado, Number> columnaDinero(String titulo) {
        TableColumn<Empleado, Number> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(data -> new SimpleFloatProperty(data.getValue().calcularSalarioNeto()));
        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number valor, boolean empty) {
                super.updateItem(valor, empty);
                setText(empty || valor == null ? null : moneda.format(valor.floatValue()));
            }
        });
        return columna;
    }

    public void openMenuPrincipal() {
        refrescar();
    }

    public void openViewPrincipal() {
        refrescar();
    }

    public void openRegistrarEmpleado() {
        refrescar();
    }

    public void openEmpleadosRegistrados() {
        refrescar();
    }

    public void openResumenPagos() {
        mostrarResumenes();
    }

    public void openConsultas() {
        refrescar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
