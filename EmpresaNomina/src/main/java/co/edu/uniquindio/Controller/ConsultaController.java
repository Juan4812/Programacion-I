package co.edu.uniquindio.Controller;

import co.edu.uniquindio.Model.Empleado;
import co.edu.uniquindio.Model.EmpleadoTemporal;
import co.edu.uniquindio.Model.Empresa;

import java.util.List;

public class ConsultaController {

    private final Empresa empresa;

    public ConsultaController(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empleado buscarEmpleadoPorDocumento(String documento) {
        return empresa.buscarEmpleadoPorDocumento(documento);
    }

    public Empleado obtenerEmpleadoConMayorSalarioNeto() {
        return empresa.obtenerEmpleadoConMayorSalarioNeto();
    }

    public float calcularNominaTotal() {
        return empresa.calcularNominaTotal();
    }

    public List<Empleado> obtenerEmpleadosConSalarioMayorA(float salario) {
        return empresa.empleadosConSalarioMayorA(salario);
    }

    public List<EmpleadoTemporal> obtenerEmpleadosTemporalesConMasDe100Horas() {
        return empresa.empleadosTemporalesConMasDe100Horas();
    }
}
