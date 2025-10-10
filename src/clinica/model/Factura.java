package clinica.model;
import java.util.ArrayList;
import clinica.habitaciones.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Factura {
	private static int contador = 1; // autoincremental
	private final static double costoPorInternacion= 500;

	private int numero;
	private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;
    private Paciente paciente;
    private IHabitacion habitacion;
    private double total; 
    private long cantidadDias;
    private ArrayList<Consulta> consultas;

    public Factura(Paciente paciente, IHabitacion habitacion, ArrayList<Consulta> consultasPaciente) {
        this.numero = contador++;
        this.fechaEgreso = LocalDate.now();
        this.fechaIngreso = paciente.getFechaIngreso();
        this.paciente = paciente;
        this.consultas = consultasPaciente;
        this.habitacion = habitacion;
        this.cantidadDias = calcularDias();
        this.total = calcularTotal();
        
        setFechaConsultas();
    }

    private double calcularTotal() {
    	double suma = 0;
        if (consultas != null) {
            for (Consulta c : consultas)
                suma += c.getImporte();
        }
        
        if (habitacion != null)
        	suma += habitacion.calcularCosto(cantidadDias) + costoPorInternacion;
        
        return suma;
    }
    
    private void setFechaConsultas() {
    	for (Consulta c : consultas)
    		c.setFecha(fechaEgreso);
    }
    
    private long calcularDias() {
    	long dias = ChronoUnit.DAYS.between(fechaIngreso, fechaEgreso);
        return dias;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nº Factura: ").append(numero).append("\n");
        sb.append("Nombre Paciente: ").append(paciente.getNya()).append("\n");
        sb.append("Fecha Ingreso: ").append(fechaIngreso).append("\n");
        sb.append("Fecha Egreso: ").append(fechaEgreso).append("\n");
        sb.append("Cantidad de dias: ").append(cantidadDias).append("\n");
        
        if (habitacion != null) {
            sb.append(String.format("Habitación tipo: %-15s  Costo: $%.2f\n\n",
                    habitacion.getTipo(),
                    habitacion.calcularCosto(cantidadDias)));
        }

        sb.append("Consultas Médicas:\n\n");

        for (Consulta c : consultas) {
            sb.append(String.format("%-60s Subtotal: $%.2f\n",
                    c.getMedico().toString(),
                    c.getImporte()));
        }

        sb.append(String.format("\nTotal: $%.2f\n", total));
        return sb.toString();
    }
}

