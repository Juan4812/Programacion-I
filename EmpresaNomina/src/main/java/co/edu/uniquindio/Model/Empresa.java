package co.edu.uniquindio.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Empresa {

    private String nombre;
    private final List<Empleado> empleados = new ArrayList<>();

    public Empresa(String nombre) {
        setNombre(nombre);
    }

    public boolean agregarEmpleado(Empleado empleado) {
        validarEmpleado(empleado);
        exigirDocumentoLibre(empleado.getDocumento(), null);
        empleados.add(empleado);
        return true;
    }

    public boolean actualizarEmpleado(String documentoActual, Empleado reemplazo) {
        validarEmpleado(reemplazo);

        for (int posicion = 0; posicion < empleados.size(); posicion++) {
            Empleado empleado = empleados.get(posicion);
            if (empleado.getDocumento().equals(documentoActual)) {
                exigirDocumentoLibre(reemplazo.getDocumento(), empleado);
                empleados.set(posicion, reemplazo);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEmpleado(String documento) {
        return empleados.removeIf(empleado -> empleado.getDocumento().equals(documento));
    }

    public Empleado buscarEmpleadoPorDocumento(String documento) {
        return empleados.stream()
                .filter(empleado -> empleado.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    public Empleado buscarPorDocumento(String documento) {
        return buscarEmpleadoPorDocumento(documento);
    }

    public boolean verificarEmpleado(String documento) {
        return buscarEmpleadoPorDocumento(documento) != null;
    }

    public Empleado obtenerEmpleadoConMayorSalarioNeto() {
        Optional<Empleado> mayor = empleados.stream()
                .max(Comparator.comparing(Empleado::calcularSalarioNeto));
        return mayor.orElse(null);
    }

    public float calcularNominaTotal() {
        float total = 0f;
        for (Empleado empleado : empleados) {
            total += empleado.calcularSalarioNeto();
        }
        return total;
    }

    public List<Empleado> empleadosConSalarioMayorA(float valor) {
        return empleados.stream()
                .filter(empleado -> empleado.calcularSalarioNeto() > valor)
                .toList();
    }

    public List<EmpleadoTemporal> empleadosTemporalesConMasDe100Horas() {
        return empleados.stream()
                .filter(EmpleadoTemporal.class::isInstance)
                .map(EmpleadoTemporal.class::cast)
                .filter(temporal -> temporal.calcularHorasTrabajadas() > 100)
                .toList();
    }

    public void mostrarTodosLosEmpleados() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        empleados.forEach(Empleado::mostrarInformacion);
    }

    public void mostrarResumenesDePago() {
        if (empleados.isEmpty()) {
            System.out.println("No hay resumenes de pago.");
            return;
        }
        empleados.stream()
                .map(Empleado::generarResumenPago)
                .forEach(System.out::println);
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return new ArrayList<>(empleados);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Empleado.textoObligatorio(nombre, "nombre de la empresa");
    }

    private void validarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado es obligatorio.");
        }
    }

    private void exigirDocumentoLibre(String documento, Empleado ignorar) {
        Empleado encontrado = buscarEmpleadoPorDocumento(documento);
        if (encontrado != null && encontrado != ignorar) {
            throw new IllegalArgumentException("Ya existe un empleado con ese documento.");
        }
    }
}
