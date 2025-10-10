package clinica.factories;
import clinica.model.*;
import clinica.d.dispatch.*;

public class PacienteFactory {

    public Paciente crearPaciente(String dni, String nya, String ciudad, String telefono, String calle, int altura, int nroHC, String rangoEtario) {
    	
    	Domicilio domicilio = new Domicilio(calle, altura);
    	
        if (rangoEtario.equalsIgnoreCase("Nino")) {
            return new Nino(dni, nya, ciudad, telefono, domicilio, nroHC, rangoEtario);
        }
        if (rangoEtario.equalsIgnoreCase("Joven")) {
            return new Joven(dni, nya, ciudad, telefono, domicilio, nroHC, rangoEtario);
        }
        if (rangoEtario.equalsIgnoreCase("Mayor")) {
            return new Mayor(dni, nya, ciudad, telefono, domicilio, nroHC, rangoEtario);
        }
           return null;

    }
}
