package clinica.ambulancia;

public class Ambulancia {
	private AmbulanciaState estado;
	
	public Ambulancia() {
		this.estado = new AmbulanciaDisponibleState(this);
	}
	
	public void atencionADomicilio() {
		this.estado.atencionADomicilio();
	}
	public void trasladoAClinica() {
		this.estado.trasladoAClinica();
	}
	public void retornoAClinica() {
		this.estado.retornoAClinica();
	}
	public void mantenimiento() {
		this.estado.mantenimiento();
	}

	public void setEstado(AmbulanciaState estado) {
		this.estado = estado;
	}
	
}
