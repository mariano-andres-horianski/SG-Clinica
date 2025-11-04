package clinica.ambulancia;

public interface AmbulanciaState {
	public void atencionADomicilio();
	public void trasladoAClinica();
	public void retornoAClinica();
	public void mantenimiento();

}
