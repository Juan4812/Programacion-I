package co.edu.uniquindio.Model;

public class EmpleadoPlanta extends Empleado {

    private String cargo;
    private int horasExtra;
    private float valorHoraExtra;
    private float auxilioTransporte;

    public EmpleadoPlanta(String nombre,
                          String documento,
                          int edad,
                          float salarioBase,
                          CategoriaEmpleado categoria,
                          float descuentoSalud,
                          float descuentoPension,
                          String cargo,
                          int horasExtra,
                          float valorHoraExtra,
                          float auxilioTransporte) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);
        setCargo(cargo);
        setHorasExtra(horasExtra);
        setValorHoraExtra(valorHoraExtra);
        setAuxilioTransporte(auxilioTransporte);
    }

    @Override
    public float calcularSalarioBruto() {
        return getSalarioBase()
                + calcularBonificacionCategoria()
                + (horasExtra * valorHoraExtra)
                + auxilioTransporte;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = textoObligatorio(cargo, "cargo");
    }

    public int getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(int horasExtra) {
        validarNoNegativo(horasExtra, "horas extra");
        this.horasExtra = horasExtra;
    }

    public float getValorHoraExtra() {
        return valorHoraExtra;
    }

    public void setValorHoraExtra(float valorHoraExtra) {
        validarNoNegativo(valorHoraExtra, "valor hora extra");
        this.valorHoraExtra = valorHoraExtra;
    }

    public float getAuxilioTransporte() {
        return auxilioTransporte;
    }

    public void setAuxilioTransporte(float auxilioTransporte) {
        validarNoNegativo(auxilioTransporte, "auxilio transporte");
        this.auxilioTransporte = auxilioTransporte;
    }
}
