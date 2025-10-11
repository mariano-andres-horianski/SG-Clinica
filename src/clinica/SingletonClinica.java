package clinica;

import clinica.exceptions.*;
import clinica.d.dispatch.*;
import clinica.habitaciones.*;
import clinica.factories.*;
import clinica.model.*;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Esta clase almacena los datos de la clinica y la gestiona
 * Implementa tanto Singleton como Facade
 * */
public class SingletonClinica {
	/**Datos de la clínica**/
	private String nombre, direccion, telefono, ciudad;
	/**Instancia del Singleton*/
	private static SingletonClinica instance;
	/**Listado de pacientes registrados**/
	private HashMap<String, Paciente> pacientes;
	/**Listado de médicos**/
	private HashMap<String, IMedico> medicos;
	/**Listado de pacientes internados**/
	private HashMap<Paciente, IHabitacion> internados;
	/**Listado de pacientes en espera**/
	private ArrayList<Paciente> listaEspera;
	/**Listado de pacientes siendo atendidos actualmente**/
	private ArrayList<Paciente> listaEnAtencion;
	private HabitacionFactory habitacionFactory;
	private PacienteFactory pacienteFactory;
	private MedicoFactory medicoFactory;
	/**Lista de consultas filtradas por paciente y otra por médicos para más fácil acceso**/
	private HashMap<Paciente, ArrayList<Consulta>> consultasPorPaciente;
	private HashMap<IMedico, ArrayList<Consulta>> consultasPorMedico;
	
	/**Listado de pacientes en espera en el patio*/
	private ArrayList<IPrioridad> patio;
	/**Listado de pacientes en espera en la sala*/
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
	/**
     * Registra un médico en la clínica.
     * @param m El médico a registrar.
     */
	public void registrarMedico(IMedico m) {
		this.medicos.put(m.getDni(), m);
	}
	/**
	 * Registra un paciente en la clínica.
	 * @param p El paciente a registrar.
	 * */
	public void registrarPaciente(Paciente p) {
		this.pacientes.put(p.getDni(), p);
	}
	/**
	 * Añade un paciente a la lista de espera
	 * @param p el paciente a ingresar
	 * */
	public void addListaEspera(Paciente p) {
		this.listaEspera.add(p);
	}
	/**
	 * Remueve a un paciente de la lista de espera
	 * @param p el paciente a remover
	 * */
	public void removeListaEspera(Paciente p) {
		this.listaEspera.remove(p);
	}
	/**
	 * Mueve un paciente al patio
	 * @param p el paciente a mover
	 * */
	public void addPatio(IPrioridad p) {
		this.patio.add(p);
	}
	/**
	 * Remueve a un paciente del patio
	 * @param p el paciente a remover
	 * */
	public void removePatio(IPrioridad p) {
		this.patio.remove(p);
	}
	/**
	 * Añade un paciente a la lista de los que están siendo atendidos
	 * @param p el paciente a añadir
	 * */
	public void addListaEnAtencion(Paciente p) {
		this.listaEnAtencion.add(p);
	}
	/**
	 * Remueve un paciente de la lista de los que están siendo atendidos
	 * @param p el paciente a remover
	 * */
	public void removeListaEnAtencion(Paciente p) {
		this.listaEnAtencion.remove(p);
	}
	/**
	 * Registra el ingreso de un paciente a la clínica.
	 * <p>
	 * Este método agrega al paciente a la lista de espera, y gestiona su ubicación
	 * inicial en la sala de espera o en el patio, según la disponibilidad y prioridad.
	 * </p>
	 *
	 * <p>
	 * Si la sala de espera está vacía, el paciente la ocupa. Si ya hay un paciente en
	 * la sala, se compara la prioridad entre ambos:
	 * </p>
	 * 
	 * <ul>
	 *   <li>Si el nuevo paciente tiene mayor prioridad, se mueve al paciente actual al patio
	 *       y el nuevo paciente ocupa la sala.</li>
	 *   <li>Si el nuevo paciente tiene menor prioridad, se lo envía directamente al patio.</li>
	 * </ul>
	 *
	 * @param p el {@link Paciente} que ingresa a la clínica
	 * @throws PacienteNotFoundException si el paciente no está registrado en el sistema
	 */
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
	/**
	 * Atiende a un paciente de la clínica.
	 * <p>
	 * Si está en la lista de espera lo remueve y se fija si está en el patio o en la sala de espera.
	 * </p>
	 *<ul>
	 *   <li>Si está en el patio lo remueve de ahí.</li>
	 *   <li>Si está en la sala de espera lo saca de ahí y mueve a un paciente del patio a la sala.</li>
	 *</ul>
	 *@param m El médico que atenderá al paciente
	 *@param p El paciente a atender.
	 *
	 *@throws PacienteNotFoundExcption si el paciente no está registrado en el sistema
	 * */
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
	/**
	 * Gestiona el egreso de un paciente de la clínica y genera la factura correspondiente.
	 * <p>
	 * Este método obtiene la habitación (si el paciente estuvo internado) y todas las consultas
	 * realizadas durante su estadía, crea una factura con esa información, y remueve al paciente de todas las listas
	 * salvo la de pacientes.
	 * </p>
	 *
	 * <p>
	 * Al finalizar, el paciente es removido de las listas de atención e internación,
	 * y su fecha de ingreso se restablece a null para permitir futuros ingresos.
	 * </p>
	 *
	 * @param p el paciente que egresa de la clínica
	 * @return la factura que se le cobra al paciente
	 **/
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
	
	/**
	 * Añade al paciente a la lista de internados con la habitación correspondiente
	 * @param paciente el paciente a internar
	 * @param habitacion la habitacion donde será internado
	 * @throws PacienteNotFoundException si el paciente no está registrado en el sistema
	 */
	public void internaPaciente(Paciente paciente, IHabitacion habitacion) throws PacienteNotFoundException {
		if (!pacientes.containsKey(paciente.getDni()))
			throw new PacienteNotFoundException("Paciente no registrado");
		internados.put(paciente, habitacion);
	}
	/**+
	 * Crea a un médico con sus datos esenciales incluyendo posgrado y contratación
	 * @param dni 
	 * @param nya nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle Calle del domicilio
	 * @param altura Altura del domicilio
	 * @param nroMat Número de matrícula
	 * @param especialidad
	 * @param contratacion
	 * @param posgrado
	 * @return una instancia IMedico habiendo añadido su contratacion y posgrado
	 */
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

	/**+
	 * Crea a un médico con sus datos esenciales sin incluir posgrado
	 * @param dni 
	 * @param nya nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle Calle del domicilio
	 * @param altura Altura del domicilio
	 * @param nroMat Número de matrícula
	 * @param especialidad
	 * @param contratacion
	 * @return una instancia IMedico habiendo añadido su contratacion
	 */
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

	// medico base (no posgrado, no no contrataciÃ³n)
	/**+
	 * Crea a un médico con sus datos esenciales sin incluir posgrado ni contratacion
	 * @param dni 
	 * @param nya nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle Calle del domicilio
	 * @param altura Altura del domicilio
	 * @param nroMat Número de matrícula
	 * @param especialidad
	 * @return una instancia IMedico sin contratacion o posgrado
	 */
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
	/**
	 * Crea un paciente con dni, número de historia clínica, nombre y apellido, rango etario, teléfono, domicilio, ciudad
	 * @param dni
	 * @param nya nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle
	 * @param altura
	 * @param nroHC número de historia clínica
	 * @param rangoEtario niño, joven o mayor
	 * @return la instancia del paciente creado
	 */
	public Paciente crearPaciente(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroHC, String rangoEtario) {
		Paciente p = pacienteFactory.crearPaciente(dni, nya, ciudad, telefono, calle, altura, nroHC, rangoEtario);
		return p;
	}
	/**
	 * Crea una nueva habitación
	 * @param tipo tipo de habitación
	 * @return una instancia de IHabitacion del tipo solicitado
	 */
	public IHabitacion crearHabitacion(String tipo) {
		IHabitacion h = habitacionFactory.crearHabitacion(tipo);
		return h;
	}
	/**
	 * Retorna un reporte de consultas del médico por día enumerando los pacientes en el período de tiempo indicado
	 * <p>Muestra de forma cronológica las consultas con el nombre del paciente</p>
	 * @param medico El médico del que se quiere generar un reporte
	 * @param fechaInicio inicio del periodo
	 * @param fechaFin fin del periodo
	 * */
	public Reporte generarReporte(IMedico medico, LocalDate fechaInicio, LocalDate fechaFin) throws MedicoNotRegisteredException {
	    if (!medicos.containsKey(medico.getDni())) {
	        throw new MedicoNotRegisteredException("El mÃ©dico no estÃ¡ registrado en la clÃ­nica.");
	    }

	    ArrayList<Consulta> consultasMedico = consultasPorMedico.get(medico);

	    return new Reporte(medico, fechaInicio, fechaFin, consultasMedico);
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
