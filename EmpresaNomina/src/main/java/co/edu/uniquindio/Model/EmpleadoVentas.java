package co.edu.uniquindio.Model;

public class EmpleadoVentas extends Empleado {

    private float totalVentas;
    private float porcentajeComision;

    public EmpleadoVentas(String nombre,
                          String documento,
                          int edad,
                          float salarioBase,
                          CategoriaEmpleado categoria,
                          float descuentoSalud,
                          float descuentoPension,
                          float totalVentas,
                          float porcentajeComision) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);
        setTotalVentas(totalVentas);
        setPorcentajeComision(porcentajeComision);
    }

    @Override
    public float calcularSalarioBruto() {
        return getSalarioBase() + calcularBonificacionCategoria() + calcularComision();
    }

    public float calcularComision() {
        return totalVentas * (porcentajeComision / 100f);
    }

    public float getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(float totalVentas) {
        validarNoNegativo(totalVentas, "total ventas");
        this.totalVentas = totalVentas;
    }

    public float getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(float porcentajeComision) {
        validarPorcentaje(porcentajeComision, "comision");
        this.porcentajeComision = porcentajeComision;
    }
}
