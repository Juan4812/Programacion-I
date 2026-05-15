import co.edu.uniquindio.Model.CategoriaEmpleado;
import co.edu.uniquindio.Model.Empleado;
import co.edu.uniquindio.Model.EmpleadoPlanta;
import co.edu.uniquindio.Model.EmpleadoTemporal;
import co.edu.uniquindio.Model.EmpleadoVentas;
import co.edu.uniquindio.Model.Empresa;
import co.edu.uniquindio.Model.ResumenPago;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmpresaTest {

    @Test
    void calculaComisionYBonificacionParaEmpleadoDeVentas() {
        EmpleadoVentas asesor = new EmpleadoVentas(
                "Mariana", "V-1", 29, 2_400f,
                CategoriaEmpleado.SENIOR, 4f, 4f,
                7_000f, 8f
        );

        assertEquals(560f, asesor.calcularComision(), 0.001f);
        assertEquals(3_320f, asesor.calcularSalarioBruto(), 0.001f);
    }

    @Test
    void descuentaSaludYPensionSobreElBruto() {
        EmpleadoTemporal auxiliar = new EmpleadoTemporal(
                "Daniel", "T-1", 22, 1_000f,
                CategoriaEmpleado.JUNIOR, 5f, 3f,
                12, 90f
        );

        float bruto = auxiliar.calcularSalarioBruto();
        assertEquals(bruto * 0.08f, auxiliar.calcularDescuentos(), 0.001f);
        assertEquals(bruto - auxiliar.calcularDescuentos(), auxiliar.calcularSalarioNeto(), 0.001f);
    }

    @Test
    void conservaElOrdenDeRegistroEnLaEmpresa() {
        Empresa empresa = new Empresa("Pecheche SAS");
        Empleado primero = planta("101", 1_800f);
        Empleado segundo = temporal("202", 18);

        empresa.agregarEmpleado(primero);
        empresa.agregarEmpleado(segundo);

        assertIterableEquals(List.of(primero, segundo), empresa.getListaEmpleados());
    }

    @Test
    void noPermiteDocumentoDuplicado() {
        Empresa empresa = new Empresa("Pecheche SAS");
        empresa.agregarEmpleado(planta("777", 2_000f));

        assertThrows(IllegalArgumentException.class, () -> empresa.agregarEmpleado(temporal("777", 10)));
    }

    @Test
    void actualizaUnEmpleadoYLiberaSuDocumentoAnterior() {
        Empresa empresa = new Empresa("Pecheche SAS");
        empresa.agregarEmpleado(planta("A1", 2_000f));

        boolean actualizado = empresa.actualizarEmpleado("A1", temporal("A2", 16));

        assertTrue(actualizado);
        assertNull(empresa.buscarEmpleadoPorDocumento("A1"));
        assertNotNull(empresa.buscarEmpleadoPorDocumento("A2"));
    }

    @Test
    void eliminarRetornaFalsoSiNoExisteElDocumento() {
        Empresa empresa = new Empresa("Pecheche SAS");

        assertFalse(empresa.eliminarEmpleado("sin-registro"));
    }

    @Test
    void ubicaElMayorSalarioNeto() {
        Empresa empresa = new Empresa("Pecheche SAS");
        Empleado planta = planta("P", 1_900f);
        Empleado ventas = new EmpleadoVentas(
                "Lina", "V", 31, 3_100f,
                CategoriaEmpleado.SENIOR, 4f, 4f,
                8_000f, 10f
        );
        empresa.agregarEmpleado(planta);
        empresa.agregarEmpleado(ventas);
        empresa.agregarEmpleado(temporal("T", 11));

        assertEquals(ventas, empresa.obtenerEmpleadoConMayorSalarioNeto());
    }

    @Test
    void sumaNominaConElSalarioNetoDeTodos() {
        Empresa empresa = new Empresa("Pecheche SAS");
        Empleado uno = planta("1", 1_500f);
        Empleado dos = temporal("2", 20);
        empresa.agregarEmpleado(uno);
        empresa.agregarEmpleado(dos);

        assertEquals(uno.calcularSalarioNeto() + dos.calcularSalarioNeto(), empresa.calcularNominaTotal(), 0.001f);
    }

    @Test
    void filtraSalariosSuperioresAlValorDado() {
        Empresa empresa = new Empresa("Pecheche SAS");
        Empleado bajo = planta("10", 900f);
        Empleado alto = planta("11", 3_000f);
        empresa.agregarEmpleado(bajo);
        empresa.agregarEmpleado(alto);

        assertIterableEquals(List.of(alto), empresa.empleadosConSalarioMayorA(2_500f));
    }

    @Test
    void temporalesDeMasDeCienHorasUsanOchoHorasPorDia() {
        Empresa empresa = new Empresa("Pecheche SAS");
        EmpleadoTemporal corto = temporal("C", 12);
        EmpleadoTemporal largo = temporal("L", 13);
        empresa.agregarEmpleado(corto);
        empresa.agregarEmpleado(largo);

        assertIterableEquals(List.of(largo), empresa.empleadosTemporalesConMasDe100Horas());
    }

    @Test
    void resumenDePagoUsaElTipoAmigable() {
        Empleado empleado = planta("R", 2_200f);

        ResumenPago pago = empleado.generarResumenPago();

        assertEquals("Planta", pago.tipoEmpleado());
        assertEquals(empleado.calcularSalarioBruto(), pago.salarioBruto(), 0.001f);
    }

    @Test
    void validaValoresNegativosYPorcentajesFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> planta("N", -1f));
        assertThrows(IllegalArgumentException.class, () -> new EmpleadoVentas(
                "Malo", "X", 20, 1_000f,
                CategoriaEmpleado.JUNIOR, 4f, 4f,
                1_000f, 101f
        ));
    }

    private EmpleadoPlanta planta(String documento, float base) {
        return new EmpleadoPlanta(
                "Planta " + documento, documento, 30, base,
                CategoriaEmpleado.SEMI_SENIOR, 4f, 4f,
                "Analista", 2, 60f, 100f
        );
    }

    private EmpleadoTemporal temporal(String documento, int dias) {
        return new EmpleadoTemporal(
                "Temporal " + documento, documento, 24, 1_200f,
                CategoriaEmpleado.JUNIOR, 4f, 4f,
                dias, 95f
        );
    }
}
