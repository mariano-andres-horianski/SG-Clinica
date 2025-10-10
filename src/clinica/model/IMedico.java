package clinica.model;


public interface IMedico extends IPersona {
	public int getNroMat();

	public Especialidad getEspecialidad();

	public double getHonorario();
}
