
import java.time.LocalTime;

public class Empleado {


    private String nombre, cedula;
    private LocalTime horaLlegada;
    private LocalTime horaSalida;
    private Empresa ownedByEmpresa;


    public Empleado(String nombre, String cedula, LocalTime horaLlegada, LocalTime horaSalida){
        this.nombre=nombre;
        this.cedula=cedula;
        this.horaLlegada=horaLlegada;
        this.horaSalida=horaSalida;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }
    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }




    public boolean llegoTarde(LocalTime horaEntradaEmpresa){
        boolean resultado = false;
        if(horaLlegada.isAfter(horaEntradaEmpresa)){
            resultado = true;
        }
        return resultado;
    }

    //ToString
    @Override
    public String toString() {
        return "Nombre: " + nombre +
                "\nCedula: " + cedula +
                "\nHora entrada: " + horaLlegada +
                "\nHora salida: " + horaSalida;
    }

}