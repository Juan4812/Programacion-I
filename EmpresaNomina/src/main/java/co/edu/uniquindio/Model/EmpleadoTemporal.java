package co.edu.uniquindio.Model;

public class EmpleadoTemporal extends Empleado {

    private int diasTrabajados;
    private float valorDia;

    public EmpleadoTemporal(String nombre,
                            String documento,
                            int edad,
                            float salarioBase,
                            CategoriaEmpleado categoria,
                            float descuentoSalud,
                            float descuentoPension,
                            int diasTrabajados,
                            float valorDia) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);
        setDiasTrabajados(diasTrabajados);
        setValorDia(valorDia);
    }

    @Override
    public float calcularSalarioBruto() {
        return (diasTrabajados * valorDia) + calcularBonificacionCategoria();
    }

    public int calcularHorasTrabajadas() {
        return diasTrabajados * 8;
    }

    public int getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(int diasTrabajados) {
        validarNoNegativo(diasTrabajados, "dias trabajados");
        this.diasTrabajados = diasTrabajados;
    }

    public float getValorDia() {
        return valorDia;
    }

    public void setValorDia(float valorDia) {
        validarNoNegativo(valorDia, "valor dia");
        this.valorDia = valorDia;
    }
}
