
import java.time.LocalTime;
import java.util.ArrayList;

public class Empresa {


    private String nombre, nit;
    private LocalTime horaEntradaEmpresa;
    private ArrayList<Empleado> listaEmpleados;


    public Empresa(String nombre, String nit, LocalTime horaEntradaEmpresa){
        this. nombre = nombre;
        this.nit = nit;
        this.horaEntradaEmpresa=horaEntradaEmpresa;
        listaEmpleados = new ArrayList<>();
    }


    public String getNombre (){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNit (){
        return nit;
    }

    public LocalTime getHoraEntradaEmpresa() {
        return horaEntradaEmpresa;
    }
    public void setHoraEntradaEmpresa(LocalTime horaEntradaEmpresa) {
        this.horaEntradaEmpresa = horaEntradaEmpresa;
    }

    public ArrayList<Empleado> getListaEmpleados(){
        return listaEmpleados;
    }

    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados){
        this.listaEmpleados=listaEmpleados;
    }

    public ArrayList<Empleado> consultarEmpleadosTarde(LocalTime horaEntradaEmpresa){

        ArrayList<Empleado> resultado = new ArrayList<>();
        for(int i=0; i< listaEmpleados.size();  i++){
            Empleado empleadoAux = listaEmpleados.get(i);

            if(empleadoAux.llegoTarde(horaEntradaEmpresa)){
                resultado.add(empleadoAux);
            }

        }
        return resultado;
    }


    @Override
    public String toString(){
        return "Empresa: " +
                "\nNombre: " + nombre +
                "\nNit: " + nit +
                "\nHora de entrada: " + horaEntradaEmpresa;
    }
}