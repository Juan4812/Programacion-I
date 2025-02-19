package org.example;
import java.util.Scanner;

//clase
class DatosEstudiante {

    public int ID;
    public String nombre;
    public String apellido;
    public int edad;
    public int telefono;
    public String correo;

//
    public void mostrarDatos() {
        System.out.println("ID: " + ID);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Edad: " + edad);
        System.out.println("Telefono: " + telefono);
        System.out.println("Correo: " + correo);
    }

}

public class Estudiantes {

    public static void main(String[] args) {
        Scanner teclado;
    //asignacion de datos de estudiante
        DatosEstudiante estudiante1 = new DatosEstudiante();
        //reasignar valores
        teclado = new Scanner(System.in);
        System.out.print("Ingrese el ID de la estudiante : ");
        estudiante1.ID = teclado.nextInt();
        System.out.print("Ingrese el nombre del estudiante : ");
        estudiante1.nombre = teclado.next();
        System.out.print("Ingrese el apellido del estudiante : ");
        estudiante1.apellido = teclado.next();
        System.out.print("Ingrese el edad del estudiante : ");
        estudiante1.edad = teclado.nextInt();
        System.out.print("Ingrese el telefono del estudiante : ");
        estudiante1.telefono = teclado.nextInt();
        System.out.print("Ingrese el correo del estudiante : ");
        estudiante1.correo = teclado.next();

        estudiante1.mostrarDatos();
    }

}
