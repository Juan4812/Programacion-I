package App;

import Model.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Pedir nombre de la empresa al inicio
        String nombreEmpresa = JOptionPane.showInputDialog(null,
                "Ingrese el nombre de la empresa:",
                "Nueva Empresa",
                JOptionPane.QUESTION_MESSAGE);

        if (nombreEmpresa == null || nombreEmpresa.trim().isEmpty()) {
            nombreEmpresa = "Tech Solutions S.A"; // valor por defecto si cancela
        }

        Empresa empresa = new Empresa(nombreEmpresa.trim());

        boolean continuar = true;

        while (continuar) {
            String opcionStr = JOptionPane.showInputDialog(null,
                    """
                    === MENU PRINCIPAL ===
                    1. Registrar empleado
                    2. Mostrar todos los empleados
                    3. Buscar empleado por documento
                    4. Mostrar empleado con mayor salario neto
                    5. Calcular nómina total
                    6. Mostrar resúmenes de pago
                    7. Salir

                    Seleccione una opción (1-7):""",
                    "Sistema de Nómina - " + empresa.getNombre(), // Necesitas agregar getter en Empresa
                    JOptionPane.PLAIN_MESSAGE);

            if (opcionStr == null) { // Usuario presionó Cancelar o cerró la ventana
                continuar = false;
                continue;
            }

            try {
                int opcion = Integer.parseInt(opcionStr.trim());

                switch (opcion) {
                    case 1:
                        registrarEmpleado(empresa);
                        break;
                    case 2:
                        mostrarTodosLosEmpleados(empresa);
                        break;
                    case 3:
                        buscarEmpleado(empresa);
                        break;
                    case 4:
                        mostrarMayorSalario(empresa);
                        break;
                    case 5:
                        mostrarNominaTotal(empresa);
                        break;
                    case 6:
                        mostrarResumenesDePago(empresa);
                        break;
                    case 7:
                        continuar = false;
                        JOptionPane.showMessageDialog(null,
                                "¡Gracias por usar el Sistema de Nómina!",
                                "Fin del programa",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null,
                                "Opción inválida. Por favor seleccione entre 1 y 7.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Debe ingresar un número válido.",
                        "Error de entrada",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private static void registrarEmpleado(Empresa empresa) {
        String[] tipos = {"1. Empleado de Planta", "2. Empleado Temporal", "3. Empleado de Ventas"};
        String tipoSeleccionado = (String) JOptionPane.showInputDialog(null,
                "Seleccione el tipo de empleado:",
                "Registrar Empleado",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]);

        if (tipoSeleccionado == null) return;

        int tipo = Integer.parseInt(tipoSeleccionado.substring(0, 1));

        try {
            String nombre = leerTexto("Nombre completo:");
            String documento = leerTexto("Documento:");
            int edad = leerEntero("Edad:");
            float salarioBase = leerFloat("Salario Base:");
            CategoriaEmpleado categoria = leerCategoria();
            float descuentoSalud = leerFloat("Descuento Salud (%):");
            float descuentoPension = leerFloat("Descuento Pensión (%):");

            Empleado empleado = null;

            switch (tipo) {
                case 1: // Planta
                    String cargo = leerTexto("Cargo:");
                    int horasExtra = leerEntero("Horas extra:");
                    float valorHoraExtra = leerFloat("Valor hora extra:");
                    float auxilioTransporte = leerFloat("Auxilio de transporte:");
                    empleado = new EmpleadoPlanta(nombre, documento, edad, salarioBase,
                            categoria, descuentoSalud, descuentoPension,
                            cargo, horasExtra, valorHoraExtra, auxilioTransporte);
                    break;

                case 2: // Temporal
                    int diasTrabajados = leerEntero("Días trabajados:");
                    float valorDia = leerFloat("Valor por día:");
                    empleado = new EmpleadoTemporal(nombre, documento, edad, salarioBase,
                            categoria, descuentoSalud, descuentoPension,
                            diasTrabajados, valorDia);
                    break;

                case 3: // Ventas
                    float totalVentas = leerFloat("Total de ventas:");
                    float porcentajeComision = leerFloat("Porcentaje de comisión (%):");
                    empleado = new EmpleadoVentas(nombre, documento, edad, salarioBase,
                            categoria, descuentoSalud, descuentoPension,
                            totalVentas, porcentajeComision);
                    break;
            }

            if (empleado != null) {
                empresa.agregarEmpleado(empleado);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage(),
                    "Datos inválidos",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error inesperado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void mostrarTodosLosEmpleados(Empresa empresa) {
        if (empresa.getListaEmpleados().isEmpty()) {   // Necesitas agregar este getter
            JOptionPane.showMessageDialog(null, "No hay empleados registrados aún.");
            return;
        }
        empresa.mostrarTodosLosEmpleados(); // Este método usa System.out, pero como es consola + GUI es aceptable
    }

    private static void buscarEmpleado(Empresa empresa) {
        String documento = leerTexto("Ingrese el documento a buscar:");
        Empleado emp = empresa.buscarEmpleadoPorDocumento(documento);

        if (emp != null) {
            emp.mostrarInformacion();
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
        }
    }

    private static void mostrarMayorSalario(Empresa empresa) {
        Empleado emp = empresa.obtenerEmpleadoConMayorSalarioNeto();
        if (emp != null) {
            JOptionPane.showMessageDialog(null,
                    "=== EMPLEADO CON MAYOR SALARIO ===\n" +
                            "Nombre: " + emp.getNombre() + "\n" +
                            "Documento: " + emp.getDocumento() + "\n" +
                            "Salario Neto: " + emp.calcularSalarioNeto(),
                    "Mejor Pagado",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados.");
        }
    }

    private static void mostrarNominaTotal(Empresa empresa) {
        JOptionPane.showMessageDialog(null,
                "Nómina Total: " + empresa.calcularNominaTotal(),
                "Cálculo de Nómina",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarResumenesDePago(Empresa empresa) {
        if (empresa.getListaEmpleados().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados.");
            return;
        }
        empresa.mostrarResumenesDePago();
    }

    // ==================== MÉTODOS DE LECTURA ====================

    private static String leerTexto(String mensaje) {
        while (true) {
            String texto = JOptionPane.showInputDialog(null, mensaje, "Entrada de datos", JOptionPane.QUESTION_MESSAGE);
            if (texto == null) throw new IllegalArgumentException("Operación cancelada por el usuario.");
            texto = texto.trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            JOptionPane.showMessageDialog(null, "Este campo no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(null, mensaje, "Entrada numérica", JOptionPane.QUESTION_MESSAGE);
                if (input == null) throw new IllegalArgumentException("Operación cancelada.");
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static float leerFloat(String mensaje) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(null, mensaje, "Entrada numérica", JOptionPane.QUESTION_MESSAGE);
                if (input == null) throw new IllegalArgumentException("Operación cancelada.");
                return Float.parseFloat(input.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número decimal válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static CategoriaEmpleado leerCategoria() {
        String[] opciones = {"1. JUNIOR", "2. SEMI_SENIOR", "3. SENIOR"};
        String seleccion = (String) JOptionPane.showInputDialog(null,
                "Seleccione la categoría del empleado:",
                "Categoría",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion == null) throw new IllegalArgumentException("Operación cancelada.");

        switch (seleccion.charAt(0)) {
            case '1': return CategoriaEmpleado.JUNIOR;
            case '2': return CategoriaEmpleado.SEMI_SENIOR;
            case '3': return CategoriaEmpleado.SENIOR;
            default: throw new IllegalArgumentException("Categoría inválida");
        }
    }
}