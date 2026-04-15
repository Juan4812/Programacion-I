package Model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private String nombre;
    private List<Empleado> listaEmpleados;

    public Empresa(String nombre) {
        this.nombre = nombre;
        this.listaEmpleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);
        System.out.println("✓ Empleado agregado exitosamente.");
    }

    public void mostrarTodosLosEmpleados() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        listaEmpleados.forEach(Empleado::mostrarInformacion);
    }

    public Empleado buscarEmpleadoPorDocumento(String documento) {
        for (Empleado e : listaEmpleados) {
            if (e.getDocumento().equals(documento)) return e;
        }
        return null;
    }

    public Empleado obtenerEmpleadoConMayorSalarioNeto() {
        if (listaEmpleados.isEmpty()) return null;
        Empleado max = listaEmpleados.get(0);
        float maxNeto = max.calcularSalarioNeto();
        for (Empleado e : listaEmpleados) {
            float neto = e.calcularSalarioNeto();
            if (neto > maxNeto) {
                maxNeto = neto;
                max = e;
            }
        }
        return max;
    }

    public float calcularNominaTotal() {
        return listaEmpleados.stream()
                .map(Empleado::calcularSalarioNeto)
                .reduce(0.0f, Float::sum);
    }

    public void mostrarResumenesDePago() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("No hay resúmenes de pago.");
            return;
        }
        listaEmpleados.forEach(e -> {
            System.out.println(e.generarResumenPago());
            System.out.println("-----------------------------");
        });
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
}
