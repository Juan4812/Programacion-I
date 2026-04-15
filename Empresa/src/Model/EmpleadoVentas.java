<<<<<<< HEAD
package Model;

public class EmpleadoVentas extends Empleado {
    private float totalVentas;
    private float porcentajeComision;

    public EmpleadoVentas(String nombre, String documento, int edad, float salarioBase,
                          CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension,
                          float totalVentas, float porcentajeComision) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);

        if (totalVentas < 0) throw new IllegalArgumentException("El total de ventas no puede ser negativo.");
        if (porcentajeComision < 0 || porcentajeComision > 100)
            throw new IllegalArgumentException("El porcentaje de comisión debe estar entre 0 y 100.");

        this.totalVentas = totalVentas;
        this.porcentajeComision = porcentajeComision;
    }

    @Override
    public float calcularSalarioBruto() {
        return salarioBase + calcularBonificacionCategoria() +
                (totalVentas * porcentajeComision / 100.0f);
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Empleado de Ventas");
        System.out.println("Total Ventas: " + totalVentas);
        System.out.println("Porcentaje Comisión: " + porcentajeComision + "%");
        System.out.println("Salario Bruto: " + calcularSalarioBruto());
        System.out.println("Salario Neto: " + calcularSalarioNeto());
        System.out.println("-----------------------------");
    }
}
=======
package Model;

public class EmpleadoVentas extends Empleado {
    private float totalVentas;
    private float porcentajeComision;

    public EmpleadoVentas(String nombre, String documento, int edad, float salarioBase,
                          CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension,
                          float totalVentas, float porcentajeComision) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);

        if (totalVentas < 0) throw new IllegalArgumentException("El total de ventas no puede ser negativo.");
        if (porcentajeComision < 0 || porcentajeComision > 100)
            throw new IllegalArgumentException("El porcentaje de comisión debe estar entre 0 y 100.");

        this.totalVentas = totalVentas;
        this.porcentajeComision = porcentajeComision;
    }

    @Override
    public float calcularSalarioBruto() {
        return salarioBase + calcularBonificacionCategoria() +
                (totalVentas * porcentajeComision / 100.0f);
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Empleado de Ventas");
        System.out.println("Total Ventas: " + totalVentas);
        System.out.println("Porcentaje Comisión: " + porcentajeComision + "%");
        System.out.println("Salario Bruto: " + calcularSalarioBruto());
        System.out.println("Salario Neto: " + calcularSalarioNeto());
        System.out.println("-----------------------------");
    }
}
>>>>>>> 60caa67ad999fdb62699486ba6dac2d857f7fdc1
