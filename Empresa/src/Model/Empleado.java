package Model;

public abstract class Empleado {
    public String nombre;
    public String documento;
    public int edad;
    public float salarioBase;
    public CategoriaEmpleado categoria;
    public float descuentoSalud;
    public float descuentoPension;

    public Empleado(String nombre, String documento, int edad, float salarioBase,
                    CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("El salario base no puede ser negativo.");
        }
        if (descuentoSalud < 0 || descuentoSalud > 100 || descuentoPension < 0 || descuentoPension > 100) {
            throw new IllegalArgumentException("Los descuentos de salud y pensión deben estar entre 0 y 100.");
        }

        this.nombre = nombre;
        this.documento = documento;
        this.edad = edad;
        this.salarioBase = salarioBase;
        this.categoria = categoria;
        this.descuentoSalud = descuentoSalud;
        this.descuentoPension = descuentoPension;
    }

    // Getters (útiles para Empresa y Main)
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public int getEdad() { return edad; }
    public float getSalarioBase() { return salarioBase; }
    public CategoriaEmpleado getCategoria() { return categoria; }
    public float getDescuentoSalud() { return descuentoSalud; }
    public float getDescuentoPension() { return descuentoPension; }


    public abstract float calcularSalarioBruto();

    public float calcularBonificacionCategoria() {
        return switch (categoria) {
            case JUNIOR -> salarioBase * 0.05f;
            case SEMI_SENIOR -> salarioBase * 0.10f;
            case SENIOR -> salarioBase * 0.15f;
        };
    }

    public float calcularDescuentos() {
        float bruto = calcularSalarioBruto();
        return bruto * (descuentoSalud + descuentoPension) / 100.0f;
    }

    public float calcularSalarioNeto() {
        return calcularSalarioBruto() - calcularDescuentos();
    }

    public void mostrarInformacion() {
        System.out.println("=== Información del Empleado ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Documento: " + documento);
        System.out.println("Edad: " + edad);
        System.out.println("Categoría: " + categoria);
        System.out.println("Salario Base: " + salarioBase);
    }

    public ResumenPago generarResumenPago() {
        float bruto = calcularSalarioBruto();
        float desc = calcularDescuentos();
        float neto = calcularSalarioNeto();
        String tipo = this.getClass().getSimpleName();
        return new ResumenPago(documento, nombre, tipo, bruto, desc, neto);
    }
}