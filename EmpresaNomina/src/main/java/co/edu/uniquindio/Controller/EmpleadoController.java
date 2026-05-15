package co.edu.uniquindio.Controller;

import co.edu.uniquindio.Model.Empleado;
import co.edu.uniquindio.Model.Empresa;

import java.util.List;

public class EmpleadoController {

    private final Empresa empresa;

    public EmpleadoController(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean crearEmpleado(Empleado empleado) {
        return empresa.agregarEmpleado(empleado);
    }

    public List<Empleado> obtenerListaEmpleados() {
        return empresa.getListaEmpleados();
    }

    public boolean eliminarEmpleado(String documento) {
        return empresa.eliminarEmpleado(documento);
    }

    public boolean actualizarEmpleado(String documento, Empleado empleado) {
        return empresa.actualizarEmpleado(documento, empleado);
    }

}
