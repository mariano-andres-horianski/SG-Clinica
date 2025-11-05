package clinica.ambulancia;

public interface IAmbulanciaState {
	public void atencionADomicilio();
	public void trasladoAClinica();
	public void retornoAClinica();
	public void mantenimiento();

}
