package clinica.model;

import java.time.LocalDate;

public class Reporte {
    private Medico medico;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Reporte(Medico medico, LocalDate fechaInicio, LocalDate fechaFin) {
        this.medico = medico;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString(){
        int nroPaciente = 0;
        double total = 0;
        LocalDate fecha = null;
        StringBuilder sb = new StringBuilder();

        sb.append("Reporte de Actividad Medica: ").append("\n");
        sb.append("Periodo: ").append(fechaInicio).append("  -  ").append(fechaFin).append("\n");
        sb.append("Medico: ").append(medico.getNya()).append("\n");

        for (Consulta c : medico.getConsultas()) {
            if(!c.getFecha().isBefore(fechaInicio) && !c.getFecha().isAfter(fechaFin)){
                if(!c.getFecha().equals(fecha)){
                    fecha = c.getFecha();
                    sb.append(fecha).append("\n");
                    sb.append(String.format("%-5s %-20s %-20s\n","Num","Paciente","Importe"));
                    nroPaciente = 0;
                }
                nroPaciente ++;
                sb.append(String.format("%-5s %-20s %-20s\n",nroPaciente,c.getPaciente(),c.getImporte()));
                total += c.getImporte();
            }
        }
        sb.append("Total: $").append(String.format("%.2f",total)).append("\n");
        return sb.toString();

    }
}

