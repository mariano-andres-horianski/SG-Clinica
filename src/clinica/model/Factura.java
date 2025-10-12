package clinica.model;
import java.util.ArrayList;
import clinica.habitaciones.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 * Representa la factura generada al egresar un paciente de la clínica.
 * 
 * La factura incluye:
 * <ul>
 *   <li>Número de factura autoincrementable</li>
 *   <li>Fecha de ingreso y egreso del paciente</li>
 *   <li>Paciente y habitación (si corresponde)</li>
 *   <li>Listado de consultas médicas recibidas con su subtotal</li>
 *   <li>Total a pagar, incluyendo honorarios médicos y costos de internación</li>
 * </ul>
 * 
 */
public class Factura {
	/** Contador autoincremental de número de factura. */
    private static int contador = 1;

    /** Costo fijo por internación. */
    private final static double costoPorInternacion = 500;

    /** Número de la factura. */
    private int numero;

    /** Fecha de ingreso del paciente. */
    private LocalDate fechaIngreso;

    /** Fecha de egreso del paciente. */
    private LocalDate fechaEgreso;

    /** Paciente al que se emite la factura. */
    private Paciente paciente;

    /** Habitación ocupada por el paciente, si corresponde. */
    private IHabitacion habitacion;

    /** Total a pagar de la factura. */
    private double total;

    /** Cantidad de días de internación del paciente. */
    private long cantidadDias;

    /** Lista de consultas médicas recibidas por el paciente. */
    private ArrayList<Consulta> consultas;

    /**
     * Crea una nueva factura para un paciente egresado.
     * <p>
     * Calcula automáticamente la cantidad de días, el total a pagar y
     * asigna la fecha de egreso actual. También establece la fecha
     * de las consultas según la fecha de egreso.
     * </p>
     *
     * @param paciente paciente egresado
     * @param habitacion habitación ocupada por el paciente (puede ser null)
     * @param consultasPaciente lista de consultas recibidas por el paciente
     */

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
    /**
     * Calcula el total a pagar sumando:
     * <ul>
     *   <li>Subtotal de todas las consultas médicas</li>
     *   <li>Costo de la habitación según los días de internación</li>
     *   <li>Costo fijo de internación</li>
     * </ul>
     *
     * @return total a pagar
     */
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
    /**
     * Asigna la fecha de egreso a todas las consultas de la factura.
     */
    private void setFechaConsultas() {
    	for (Consulta c : consultas)
    		c.setFecha(fechaEgreso);
    }
    /**
     * Calcula la cantidad de días transcurridos entre la fecha de ingreso
     * y la fecha de egreso del paciente.
     *
     * @return cantidad de días de internación
     */
    private long calcularDias() {
    	long dias = ChronoUnit.DAYS.between(fechaIngreso, fechaEgreso);
        return dias;
    }
    /**
     * Genera una representación en texto de la factura.
     * <p>
     * Incluye número de factura, nombre del paciente, fechas de ingreso y egreso,
     * cantidad de días, habitación (si corresponde), listado de consultas médicas
     * con subtotales y total final.
     * </p>
     *
     * @return String con la información completa de la factura
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NÂº Factura: ").append(numero).append("\n");
        sb.append("Nombre Paciente: ").append(paciente.getNya()).append("\n");
        sb.append("Fecha Ingreso: ").append(fechaIngreso).append("\n");
        sb.append("Fecha Egreso: ").append(fechaEgreso).append("\n");
        sb.append("Cantidad de dias: ").append(cantidadDias).append("\n");
        
        if (habitacion != null) {
            sb.append(String.format("HabitaciÃ³n tipo: %-15s  Costo: $%.2f\n\n",
                    habitacion.getTipo(),
                    habitacion.calcularCosto(cantidadDias)));
        }

        sb.append("Consultas MÃ©dicas:\n\n");

        for (Consulta c : consultas) {
            sb.append(String.format("%-60s Subtotal: $%.2f\n",
                    c.getMedico().toString(),
                    c.getImporte()));
        }

        sb.append(String.format("\nTotal: $%.2f\n", total));
        return sb.toString();
    }
}

