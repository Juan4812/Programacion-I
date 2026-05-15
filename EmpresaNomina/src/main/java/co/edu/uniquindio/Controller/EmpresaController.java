package co.edu.uniquindio.Controller;

import co.edu.uniquindio.Model.Empleado;
import co.edu.uniquindio.Model.Empresa;

public class EmpresaController {

    private final Empresa empresa;

    public EmpresaController(Empresa empresa) {
        this.empresa = empresa;
    }

    public String obtenerNombreEmpresa() {
        return empresa.getNombre();
    }

    public boolean actualizarNombreEmpresa(String nombre) {
        empresa.setNombre(nombre);
        return true;
    }

    public int obtenerCantidadEmpleados() {
        return empresa.getListaEmpleados().size();
    }

    public float calcularNominaTotal() {
        return empresa.calcularNominaTotal();
    }

    public Empleado obtenerEmpleadoConMayorSalarioNeto() {
        return empresa.obtenerEmpleadoConMayorSalarioNeto();
    }
}
