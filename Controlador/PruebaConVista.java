import ControladorAsociados.ActionListenerAsociados;
import vista.JframePrincipal.VentanaPrincipal;

public class PruebaConVista {
	public static void main(String[] args) {
		ActionListenerAsociados controlador = new ActionListenerAsociados();
		controlador.setVentanaPrincipal(new VentanaPrincipal());
	}
}
