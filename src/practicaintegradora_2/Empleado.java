
package practicaintegradora_2;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
    private String nombreEmpleado;
    private long cuil;
    private int anioIngreso;
    private double montoAntiguedad;
    private double sueldoBasico;
    private List<BonoSueldo> bonos;

    public Empleado(String nombreEmpleado, long cuil, int anioIngreso, double sueldoBasico) {
        this.nombreEmpleado = nombreEmpleado;
        this.cuil = cuil;
        this.anioIngreso = anioIngreso;
        this.sueldoBasico = sueldoBasico;
        this.bonos = new ArrayList<>();
    }

    // Método para calcular antigüedad (2% del sueldo básico por cada año)
    public void calcularAntiguedad(int anioActual) {
        int aniosAntiguedad = anioActual - anioIngreso;
        this.montoAntiguedad = sueldoBasico * 0.02 * aniosAntiguedad;
    }

    // Getters y Setters
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public long getCuil() {
        return cuil;
    }

    public void setCuil(long cuil) {
        this.cuil = cuil;
    }

    public int getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(int anioIngreso) {
        this.anioIngreso = anioIngreso;
    }

    public double getMontoAntiguedad() {
        return montoAntiguedad;
    }

    public double getSueldoBasico() {
        return sueldoBasico;
    }

    public void setSueldoBasico(double sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    public List<BonoSueldo> getBonos() {
        return bonos;
    }

    public void addBono(BonoSueldo bono) {
        this.bonos.add(bono);
    }
}

