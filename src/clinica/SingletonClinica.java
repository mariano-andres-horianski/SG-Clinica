package clinica;

import clinica.exceptions.*;
import clinica.d.dispatch.*;
import clinica.habitaciones.*;
import clinica.factories.*;
import clinica.model.*;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;

public class SingletonClinica {
	private String nombre, direccion, telefono, ciudad;
	private static SingletonClinica instance;
	private HashMap<String, Paciente> pacientes;
	private HashMap<String, IMedico> medicos;
	private HashMap<Paciente, IHabitacion> internados;
	private ArrayList<Paciente> listaEspera;
	private ArrayList<Paciente> listaEnAtencion;
	private HabitacionFactory habitacionFactory;
	private PacienteFactory pacienteFactory;
	private MedicoFactory medicoFactory;
	private HashMap<Paciente, ArrayList<Consulta>> consultasPorPaciente;
	private HashMap<IMedico, ArrayList<Consulta>> consultasPorMedico;

	private ArrayList<IPrioridad> patio;
	private SalaEspera salaEspera;

	private SingletonClinica() {
		ciudad = "Mar del Plata";
		direccion = "Avenida Siempreviva 123";
		telefono = "22300000";
		nombre = "Clinica Colon";
		pacientes = new HashMap<String, Paciente>();
		medicos = new HashMap<String, IMedico>();
		internados = new HashMap<>();

		listaEspera = new ArrayList<Paciente>();
		listaEnAtencion = new ArrayList<Paciente>();

		patio = new ArrayList<IPrioridad>();
		salaEspera = new SalaEspera();
		salaEspera.desocupar();

		habitacionFactory = new HabitacionFactory();
		medicoFactory = new MedicoFactory();
		pacienteFactory = new PacienteFactory();

		consultasPorPaciente = new HashMap<>();
		consultasPorMedico = new HashMap<>();
	}

	public static SingletonClinica getInstance() {
		if (instance == null) {
			instance = new SingletonClinica();
		}

		return instance;
	}

	public void registrarMedico(IMedico m) {
		this.medicos.put(m.getDni(), m);
	}

	public void registrarPaciente(Paciente p) {
		this.pacientes.put(p.getDni(), p);
	}

	public void addListaEspera(Paciente p) {
		this.listaEspera.add(p);
	}

	public void removeListaEspera(Paciente p) {
		this.listaEspera.remove(p);
	}

	public void addPatio(IPrioridad p) {
		this.patio.add(p);
	}

	public void removePatio(IPrioridad p) {
		this.patio.remove(p);
	}

	public void addListaEnAtencion(Paciente p) {
		this.listaEnAtencion.add(p);
	}

	public void removeListaEnAtencion(Paciente p) {
		this.listaEnAtencion.remove(p);
	}

	public void ingresaPaciente(Paciente p) throws PacienteNotFoundException {
		if (!pacientes.containsKey(p.getDni()))
			throw new PacienteNotFoundException("Paciente no registrado");

		p.setFechaIngreso(LocalDate.now());
		
		addListaEspera(p);

		if (!(salaEspera.isOcupacion()))
			salaEspera.ocuparSala(p);
		else if (!(p.prioridadSala(salaEspera.getPaciente()))) {
			addPatio(salaEspera.getPaciente());
			salaEspera.ocuparSala(p);
		} else
			addPatio(p);
	}

	public void atiendePaciente(IMedico m, Paciente p) throws PacienteNotFoundException {
		if (!pacientes.containsKey(p.getDni()))
			throw new PacienteNotFoundException("Paciente no registrado");

		if (this.listaEspera.contains(p)) {
			removeListaEspera(p);
			if (this.patio.contains(p))
				removePatio(p);
			else {
				salaEspera.desocupar();
				if (!patio.isEmpty()) {
					salaEspera.ocuparSala(patio.get(0));
					removePatio(salaEspera.getPaciente());
				}

			}

			addListaEnAtencion(p);
		}
		
		Consulta c = new Consulta(m, p);
		
	    if (!consultasPorPaciente.containsKey(p)) {
	        consultasPorPaciente.put(p, new ArrayList<>());
	    }
	    consultasPorPaciente.get(p).add(c);

	    if (!consultasPorMedico.containsKey(m)) {
	        consultasPorMedico.put(m, new ArrayList<>());
	    }
	    consultasPorMedico.get(m).add(c);

	}

	public Factura egresaPaciente(Paciente p) {
		IHabitacion h = internados.get(p); // si no fue internado retorna null
		ArrayList<Consulta> consultasPaciente = consultasPorPaciente.get(p);
		
		Factura f = new Factura(p, h, consultasPaciente);
		
		consultasPorPaciente.remove(p);
		internados.remove(p);
		removeListaEnAtencion(p);
		p.setFechaIngreso(null);  // para que si vuelve a atenderse se renueve la fecha
		
		return f;
	}

	public void internaPaciente(Paciente paciente, IHabitacion habitacion) throws PacienteNotFoundException {
		if (!pacientes.containsKey(paciente.getDni()))
			throw new PacienteNotFoundException("Paciente no registrado");
		internados.put(paciente, habitacion);
	}

	// posgrado + contratacion
	public IMedico crearMedico(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroMat, String especialidad, String contratacion, String posgrado) {
		try {
			IMedico m = this.medicoFactory.crearMedico(dni, nya, ciudad, telefono, calle, altura, nroMat, especialidad,
					contratacion, posgrado);
			return m;
		} catch (EspecialidadNotFoundException | ContratacionNotFoundException | PosgradoNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	// sin posgrado + contratacion

	public IMedico crearMedico(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroMat, String especialidad, String contratacion) {
		try {
			IMedico m = this.medicoFactory.crearMedico(dni, nya, ciudad, telefono, calle, altura, nroMat, especialidad,
					contratacion);
			return m;
		} catch (EspecialidadNotFoundException | ContratacionNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	// medico base (no posgrado, no no contratación)

	public IMedico crearMedico(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroMat, String especialidad) {
		try {
			IMedico m = this.medicoFactory.crearMedico(dni, nya, ciudad, telefono, calle, altura, nroMat, especialidad);
			return m;
		} catch (EspecialidadNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Paciente crearPaciente(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroHC, String rangoEtario) {
		Paciente p = pacienteFactory.crearPaciente(dni, nya, ciudad, telefono, calle, altura, nroHC, rangoEtario);
		return p;
	}

	public IHabitacion crearHabitacion(String tipo) {
		IHabitacion h = habitacionFactory.crearHabitacion(tipo);
		return h;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCiudad() {
		return ciudad;
	}

}
