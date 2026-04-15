package Model;

public class EmpleadoTemporal extends Empleado {
    private int diasTrabajados;
    private float valorDia;

    public EmpleadoTemporal(String nombre, String documento, int edad, float salarioBase,
                            CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension,
                            int diasTrabajados, float valorDia) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);

        if (diasTrabajados < 0) throw new IllegalArgumentException("Los días trabajados no pueden ser negativos.");
        if (valorDia < 0) throw new IllegalArgumentException("El valor por día no puede ser negativo.");

        this.diasTrabajados = diasTrabajados;
        this.valorDia = valorDia;
    }

    @Override
    public float calcularSalarioBruto() {
        return (diasTrabajados * valorDia) + calcularBonificacionCategoria();
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Empleado Temporal");
        System.out.println("Días Trabajados: " + diasTrabajados);
        System.out.println("Valor por Día: " + valorDia);
        System.out.println("Salario Bruto: " + calcularSalarioBruto());
        System.out.println("Salario Neto: " + calcularSalarioNeto());
        System.out.println("-----------------------------");
    }
}