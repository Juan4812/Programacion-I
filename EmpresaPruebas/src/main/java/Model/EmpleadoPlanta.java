package Model;

public class EmpleadoPlanta extends Empleado {
    private String cargo;
    private int horasExtra;
    private float valorHoraExtra;
    private float auxilioTransporte;

    public EmpleadoPlanta(String nombre, String documento, int edad, float salarioBase,
                          CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension,
                          String cargo, int horasExtra, float valorHoraExtra, float auxilioTransporte) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);

        if (horasExtra < 0) throw new IllegalArgumentException("Las horas extra no pueden ser negativas.");
        if (valorHoraExtra < 0) throw new IllegalArgumentException("El valor de la hora extra no puede ser negativo.");
        if (auxilioTransporte < 0) throw new IllegalArgumentException("El auxilio de transporte no puede ser negativo.");

        this.cargo = cargo;
        this.horasExtra = horasExtra;
        this.valorHoraExtra = valorHoraExtra;
        this.auxilioTransporte = auxilioTransporte;
    }

    @Override
    public float calcularSalarioBruto() {
        return salarioBase + calcularBonificacionCategoria() +
                (horasExtra * valorHoraExtra) + auxilioTransporte;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Empleado de Planta");
        System.out.println("Cargo: " + cargo);
        System.out.println("Horas Extra: " + horasExtra);
        System.out.println("Valor Hora Extra: " + valorHoraExtra);
        System.out.println("Auxilio Transporte: " + auxilioTransporte);
        System.out.println("Salario Bruto: " + calcularSalarioBruto());
        System.out.println("Salario Neto: " + calcularSalarioNeto());
        System.out.println("-----------------------------");
    }
}