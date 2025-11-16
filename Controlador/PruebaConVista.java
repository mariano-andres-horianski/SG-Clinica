import clinica.SingletonClinica;
import controlador.Asociados.ActionListenerAsociados;
import controlador.Simulacion.ActionListenerSimulacion;
import excepciones.AsociadoDuplicadoException;
import negocio.Asociado;
import negocio.RetornoAutomatico;
import vista.JframePrincipal.VentanaPrincipal;

public class PruebaConVista {
	public static void main(String[] args) {
		VentanaPrincipal vista = new VentanaPrincipal();
		SingletonClinica clinica = SingletonClinica.getInstance();

		ActionListenerAsociados controladorAsociados = new ActionListenerAsociados();
		controladorAsociados.setVentanaPrincipal(vista);

		ActionListenerSimulacion controladorSimulacion = new ActionListenerSimulacion();
		controladorSimulacion.setVentanaPrincipal(vista);

	}
}
