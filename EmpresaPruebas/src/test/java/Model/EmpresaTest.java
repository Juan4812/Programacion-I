package Model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpresaTest {

    @Test
    void calcularSalarioBrutoEmpleadoVentas() {
        EmpleadoVentas emp = new EmpleadoVentas("Juan", "123", 30, 1000000,
                CategoriaEmpleado.JUNIOR, 4, 4, 1000000, 10);
        assertEquals(1150000, emp.calcularSalarioBruto(), 0.001);
    }
    @Test
    void salarioNetoTemporalNoCero() {
        EmpleadoTemporal emp = new EmpleadoTemporal("Ana", "456", 25, 1500,
                CategoriaEmpleado.SEMI_SENIOR, 4, 4, 20, 80);
        assertNotEquals(0, emp.calcularSalarioNeto());
    }

    @Test
    void empleadosAlmacenadosCorrectos() {
        Empresa empresa = new Empresa("Test");
        Empleado e1 = new EmpleadoPlanta("E1", "1", 30, 1000, CategoriaEmpleado.JUNIOR, 4, 4, "C", 0, 0, 0);
        Empleado e2 = new EmpleadoTemporal("E2", "2", 25, 1000, CategoriaEmpleado.JUNIOR, 4, 4, 10, 100);
        Empleado e3 = new EmpleadoVentas("E3", "3", 28, 1000, CategoriaEmpleado.JUNIOR, 4, 4, 1000, 5);

        empresa.agregarEmpleado(e1);
        empresa.agregarEmpleado(e2);
        empresa.agregarEmpleado(e3);

        assertIterableEquals(List.of(e1, e2, e3), empresa.getListaEmpleados());
    }















    //Tarea
    @Test
    void calcularNominaTotal() {
        Empresa empresa = new Empresa("Mi Empresa");

        Empleado empleado1 = new EmpleadoPlanta(
                "Juan", "123", 30, 2000,
                CategoriaEmpleado.JUNIOR, 4, 4,
                "Auxiliar", 2, 50, 100
        );

        Empleado empleado2 = new EmpleadoTemporal(
                "Ana", "456", 25, 1500,
                CategoriaEmpleado.SEMI_SENIOR, 4, 4,
                20, 80
        );

        Empleado empleado3 = new EmpleadoVentas(
                "Luis", "789", 28, 1800,
                CategoriaEmpleado.SENIOR, 4, 4,
                5000, 10
        );

        empresa.agregarEmpleado(empleado1);
        empresa.agregarEmpleado(empleado2);
        empresa.agregarEmpleado(empleado3);

        List<Float> esperados = List.of(
                empleado1.calcularSalarioNeto(),
                empleado2.calcularSalarioNeto(),
                empleado3.calcularSalarioNeto()
        );

        List<Float> obtenidos = empresa.getListaEmpleados()
                .stream()
                .map(Empleado::calcularSalarioNeto)
                .toList();

        assertIterableEquals(esperados, obtenidos);

        float nominaEsperada = empleado1.calcularSalarioNeto()
                + empleado2.calcularSalarioNeto()
                + empleado3.calcularSalarioNeto();

        assertEquals(nominaEsperada, empresa.calcularNominaTotal(), 0.001);
    }
}
