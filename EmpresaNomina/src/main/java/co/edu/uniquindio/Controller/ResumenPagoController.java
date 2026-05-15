package co.edu.uniquindio.Controller;

import co.edu.uniquindio.Model.Empleado;
import co.edu.uniquindio.Model.Empresa;
import co.edu.uniquindio.Model.ResumenPago;

import java.util.List;

public class ResumenPagoController {

    private final Empresa empresa;

    public ResumenPagoController(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<ResumenPago> obtenerResumenesPago() {
        return empresa.getListaEmpleados()
                .stream()
                .map(Empleado::generarResumenPago)
                .toList();
    }
}
