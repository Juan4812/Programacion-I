package co.edu.uniquindio.Model;

import java.util.Objects;

public abstract class Empleado {

    private String documento;
    private String nombre;
    private int edad;
    private float salarioBase;
    private CategoriaEmpleado categoria;
    private float descuentoSalud;
    private float descuentoPension;

    protected Empleado(String nombre,
                       String documento,
                       int edad,
                       float salarioBase,
                       CategoriaEmpleado categoria,
                       float descuentoSalud,
                       float descuentoPension) {
        cambiarDatosBasicos(nombre, documento, edad, salarioBase, categoria);
        setDescuentoSalud(descuentoSalud);
        setDescuentoPension(descuentoPension);
    }

    public final void cambiarDatosBasicos(String nombre,
                                          String documento,
                                          int edad,
                                          float salarioBase,
                                          CategoriaEmpleado categoria) {
        setNombre(nombre);
        setDocumento(documento);
        setEdad(edad);
        setSalarioBase(salarioBase);
        setCategoria(categoria);
    }

    public abstract float calcularSalarioBruto();

    public float calcularBonificacionCategoria() {
        float porcentaje = switch (categoria) {
            case JUNIOR -> 0.05f;
            case SEMI_SENIOR -> 0.10f;
            case SENIOR -> 0.15f;
        };
        return salarioBase * porcentaje;
    }

    public float calcularDescuentos() {
        return calcularSalarioBruto() * ((descuentoSalud + descuentoPension) / 100f);
    }

    public float calcularSalarioNeto() {
        return calcularSalarioBruto() - calcularDescuentos();
    }

    public ResumenPago generarResumenPago() {
        float bruto = calcularSalarioBruto();
        float descuentos = calcularDescuentos();
        return new ResumenPago(documento, nombre, nombreTipo(), bruto, descuentos, bruto - descuentos);
    }

    public String nombreTipo() {
        if (this instanceof EmpleadoTemporal) {
            return "Temporal";
        }
        if (this instanceof EmpleadoVentas) {
            return "Ventas";
        }
        return "Planta";
    }

    public void mostrarInformacion() {
        System.out.println(resumenTexto());
    }

    public String resumenTexto() {
        return "Documento: " + documento + System.lineSeparator()
                + "Nombre: " + nombre + System.lineSeparator()
                + "Edad: " + edad + System.lineSeparator()
                + "Categoria: " + categoria + System.lineSeparator()
                + "Tipo: " + nombreTipo() + System.lineSeparator()
                + "Salario bruto: " + calcularSalarioBruto() + System.lineSeparator()
                + "Salario neto: " + calcularSalarioNeto();
    }

    protected static String textoObligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
        return valor.trim();
    }

    protected static void validarNoNegativo(float valor, String campo) {
        if (valor < 0) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser negativo.");
        }
    }

    protected static void validarNoNegativo(int valor, String campo) {
        if (valor < 0) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser negativo.");
        }
    }

    protected void validarPorcentaje(float valor, String campo) {
        if (valor < 0 || valor > 100) {
            throw new IllegalArgumentException("El porcentaje de " + campo + " debe estar entre 0 y 100.");
        }
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = textoObligatorio(documento, "documento");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = textoObligatorio(nombre, "nombre");
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        validarNoNegativo(edad, "edad");
        this.edad = edad;
    }

    public float getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(float salarioBase) {
        validarNoNegativo(salarioBase, "salario base");
        this.salarioBase = salarioBase;
    }

    public CategoriaEmpleado getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEmpleado categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoria es obligatoria.");
    }

    public float getDescuentoSalud() {
        return descuentoSalud;
    }

    public void setDescuentoSalud(float descuentoSalud) {
        validarPorcentaje(descuentoSalud, "salud");
        this.descuentoSalud = descuentoSalud;
    }

    public float getDescuentoPension() {
        return descuentoPension;
    }

    public void setDescuentoPension(float descuentoPension) {
        validarPorcentaje(descuentoPension, "pension");
        this.descuentoPension = descuentoPension;
    }
}
