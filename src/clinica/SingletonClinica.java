package clinica;

import clinica.exceptions.*;
import clinica.d.dispatch.*;
import clinica.habitaciones.*;
import clinica.factories.*;
import clinica.model.*;
import excepciones.AsociadoDuplicadoException;
import excepciones.AsociadoNotFoundException;
import negocio.Ambulancia;
import negocio.Asociado;
import negocio.Operario;

import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Esta clase almacena los datos de la clinica y la gestiona Implementa tanto
 * Singleton como Facade
 */
public class SingletonClinica {
	/** Datos de la cl�nica **/
	private String nombre, direccion, telefono, ciudad;
	/** Instancia del Singleton */
	private static SingletonClinica instance;
	/** Listado de pacientes registrados **/
	private HashMap<String, Paciente> pacientes;
	/** Listado de m�dicos **/
	private HashMap<String, IMedico> medicos;
	/** Listado de pacientes internados **/
	private HashMap<Paciente, IHabitacion> internados;
	/** Listado de pacientes en espera **/
	private ArrayList<Paciente> listaEspera;
	/** Listado de pacientes siendo atendidos actualmente **/
	private ArrayList<Paciente> listaEnAtencion;
	/** Listado de Asociados **/
	private HashMap<String, Asociado> asociados;

	private HabitacionFactory habitacionFactory;
	private PacienteFactory pacienteFactory;
	private MedicoFactory medicoFactory;
	private AsociadoFactory asociadoFactory;

	/**
	 * Lista de consultas filtradas por paciente y otra por m�dicos para m�s f�cil
	 * acceso
	 **/
	private HashMap<Paciente, ArrayList<Consulta>> consultasPorPaciente;
	private HashMap<IMedico, ArrayList<Consulta>> consultasPorMedico;

	/** Listado de pacientes en espera en el patio */
	private ArrayList<IPrioridad> patio;
	/** Listado de pacientes en espera en la sala */
	private SalaEspera salaEspera;
	
	/** Ambulancia */
	private Ambulancia ambulancia;
	
	/** Operario */
	private Operario operario;
	
	/** Simulacion activa */
	private boolean simulacionActiva;

	private SingletonClinica() {
		ciudad = "Mar del Plata";
		direccion = "Avenida Siempreviva 123";
		telefono = "22300000";
		nombre = "Clinica Colon";
		pacientes = new HashMap<String, Paciente>();
		medicos = new HashMap<String, IMedico>();
		internados = new HashMap<>();
		asociados = new HashMap<String, Asociado>();

		listaEspera = new ArrayList<Paciente>();
		listaEnAtencion = new ArrayList<Paciente>();

		patio = new ArrayList<IPrioridad>();
		salaEspera = new SalaEspera();
		salaEspera.desocupar();

		habitacionFactory = new HabitacionFactory();
		medicoFactory = new MedicoFactory();
		pacienteFactory = new PacienteFactory();
		asociadoFactory = new AsociadoFactory();

		consultasPorPaciente = new HashMap<>();
		consultasPorMedico = new HashMap<>();
		
		ambulancia = new Ambulancia();
		operario = new Operario(this);
		simulacionActiva = true;
	}

	public static SingletonClinica getInstance() {
		if (instance == null) {
			instance = new SingletonClinica();
		}

		return instance;
	}

	/**
	 * Registra un m�dico en la cl�nica. <b>pre: </b>la lista de m�dicos est�
	 * inicializada y m != null<br>
	 * <b>post:</b> se registr� un m�dico con los datos almacenados en el IM�dico
	 * prove�do<br>
	 * 
	 * @param m El m�dico a registrar.
	 */
	public void registrarMedico(IMedico m) {
		assert m != null : "Médico no puede ser null";
		assert m.getDni() != null : "El DNI del médico no puede ser null";
		assert this.medicos != null : "La lista de médicos no está inicializada";

		this.medicos.put(m.getDni(), m);

		assert this.medicos.containsKey(m.getDni()) : "El médico no se registró correctamente";
	}

	/**
	 * Registra un paciente en la cl�nica. <b>pre: </b>la lista de pacientes est�
	 * inicializada y p != null<br>
	 * <b>post:</b> se registr� un paciente con los datos almacenados en el Paciente
	 * prove�do<br>
	 * 
	 * @param p El paciente a registrar.
	 */
	public void registrarPaciente(Paciente p) {
		assert p != null : "Paciente no puede ser null";
		assert p.getDni() != null : "El DNI del paciente no puede ser null";
		assert this.pacientes != null : "La lista de pacientes no está inicializada";

		this.pacientes.put(p.getDni(), p);

		assert pacientes.containsKey(p.getDni()) : "El paciente no se registró correctamente";
	}

	/**
	 * Registra un asociado en la cl�nica. <b>pre: </b>la lista de asociados est�
	 * inicializada y a != null<br>
	 * <b>post:</b> se registr� un asociado <br>
	 * 
	 * @param a El asociado a registrar.
	 */
	public void registrarAsociado(Asociado a) throws AsociadoDuplicadoException {
		assert a != null : "Asociado no puede ser null";
		assert a.getDni() != null : "El DNI del asociado no puede ser null";
		assert this.asociados != null : "La lista de asociado no está inicializada";

		if (this.asociados.containsKey(a.getDni()))
			throw new AsociadoDuplicadoException("El asociado ya está registrado en el sistema");

		this.asociados.put(a.getDni(), a);

		assert pacientes.containsKey(a.getDni()) : "El asociado no se registró correctamente";
	}

	/**
	 * Elimina un asociado de la cl�nica. <b>pre: </b>la lista de asociados est�
	 * inicializada, a != null<br>
	 * <b>post:</b> se eliminó el asociado <br>
	 * 
	 * @param a El asociado a eliminar.
	 */
	public void eliminarAsociado(Asociado a) throws AsociadoNotFoundException {
		assert a != null : "Asociado no puede ser null";
		assert a.getDni() != null : "El DNI del asociado no puede ser null";
		assert this.asociados != null : "La lista de asociado no está inicializada";

		if (!this.asociados.containsKey(a.getDni()))
			throw new AsociadoNotFoundException("El asociado no está registrado en el sistema");

		this.asociados.remove(a.getDni(), a);

		assert !pacientes.containsKey(a.getDni()) : "El asociado no se eliminó correctamente";
	}

	/**
	 * A�ade un paciente a la lista de espera <b>pre: </b>la lista de espera est�
	 * inicializada y el paciente registrado<br>
	 * <b>post:</b> el paciente est� en la lista de espera<br>
	 * 
	 * @param p el paciente a ingresar
	 */
	public void addListaEspera(Paciente p) {
		assert pacientes.containsKey(p.getDni()) : "Paciente debe estar registrado";
		assert listaEspera != null : "La lista de espera no está inicializada";

		int tamAntes = listaEspera.size();
		this.listaEspera.add(p);

		assert listaEspera.size() == tamAntes + 1 : "No se añadió paciente a lista de espera";
	}

	/**
	 * Remueve a un paciente de la lista de espera <b>pre: </b>la lista de espera
	 * est� inicializada y el paciente est� en ella<br>
	 * <b>post:</b> el paciente ya no est� en la lista de espera<br>
	 * 
	 * @param p el paciente a remover
	 */
	public void removeListaEspera(Paciente p) {
		assert listaEspera != null : "La lista de espera no está inicializada";
		assert listaEspera.contains(p) : "Paciente no estaba en lista de espera";

		this.listaEspera.remove(p);

		assert !listaEspera.contains(p) : "Paciente no fue removido correctamente";
	}

	/**
	 * Mueve un paciente al patio <b>pre:</b> la lista est� inicializada y el
	 * paciente existe<br>
	 * <b>post:</b> el paciente est� en el patio<br>
	 * 
	 * @param p el paciente a mover
	 */
	public void addPatio(IPrioridad p) {
		assert this.patio != null : "La lista del patio no está inicializada";
		assert p != null : "Paciente no existe";

		this.patio.add(p);

		assert patio.contains(p) : "El paciente no quedó en el patio";
	}

	/**
	 * Remueve a un paciente del patio <b>pre:</b> el paciente existe y est� en el
	 * patio <br>
	 * <b>post:</b> el paciente ya no est� en el patio <br>
	 * 
	 * @param p el paciente a remover
	 */
	public void removePatio(IPrioridad p) {
		assert patio.contains(p) : "El paciente no está en el patio";

		this.patio.remove(p);

		assert !patio.contains(p) : "El paciente no fue removido del patio correctamente";
	}

	/**
	 * A�ade un paciente a la lista de los que est�n siendo atendidos <b>pre:</b> la
	 * lista de atenci�n est� inicializada, el paciente existe y no est� siendo
	 * atendido <br>
	 * <b>post:</b> el paciente est� siendo atendido <br>
	 * 
	 * @param p el paciente a a�adir
	 */
	public void addListaEnAtencion(Paciente p) {
		assert this.listaEnAtencion != null : "La lista \"en atención\" no está inicializada";
		assert p != null : "El paciente no existe";

		this.listaEnAtencion.add(p);

		assert this.listaEnAtencion.contains(p) : "El paciente no fue añadido a la lista de atención correctamente";
	}

	/**
	 * Remueve un paciente de la lista de los que est�n siendo atendidos <b>pre: la
	 * lista est� inicializada, el paciente existe y est� en la lista</b><br>
	 * <b>post:el paciente ya no est� siento atendido</b><br>
	 * 
	 * @param p el paciente a remover
	 */
	public void removeListaEnAtencion(Paciente p) {
		assert this.listaEnAtencion != null : "La lista \"en atención\" no está inicializada";
		assert p != null : "El paciente no existe";
		assert this.listaEnAtencion.contains(p) : "El paciente no está en la lista de atención";

		this.listaEnAtencion.remove(p);

		assert !listaEnAtencion.contains(p) : "El paciente no fue eliminado de la lista de atención correctamente";
	}

	/**
	 * Registra el ingreso de un paciente a la cl�nica.
	 * <p>
	 * Este m�todo agrega al paciente a la lista de espera, y gestiona su ubicaci�n
	 * inicial en la sala de espera o en el patio, seg�n la disponibilidad y
	 * prioridad.
	 * </p>
	 *
	 * <p>
	 * Si la sala de espera est� vac�a, el paciente la ocupa. Si ya hay un paciente
	 * en la sala, se compara la prioridad entre ambos:
	 * </p>
	 * 
	 * <ul>
	 * <li>Si el nuevo paciente tiene mayor prioridad, se mueve al paciente actual
	 * al patio y el nuevo paciente ocupa la sala.</li>
	 * <li>Si el nuevo paciente tiene menor prioridad, se lo env�a directamente al
	 * patio.</li>
	 * </ul>
	 * <br>
	 * <b>pre:</b> la lista de espera est� inicializada, al igual que la sala de
	 * espera y el patio<br>
	 * <b>post: </b>el paciente est� en la sala de espera o el patio seg�n
	 * corresponda (y otro paciente en la sala/patio si el nuevo tom� prioridad)<br>
	 * 
	 * @param p el {@link Paciente} que ingresa a la cl�nica
	 * @throws PacienteNotFoundException si el paciente no est� registrado en el
	 *                                   sistema
	 */
	public void ingresaPaciente(Paciente p) throws PacienteNotFoundException {
		assert listaEspera != null : "La lista de espera no está inicializada";
		assert salaEspera != null : "La sala de espera no está inicializada";
		assert patio != null : "El patio no está inicializado";

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

		assert listaEspera.contains(p) : "El paciente no quedó en la lista de espera";
		assert salaEspera.getPaciente().equals(p) || patio.contains(p)
				: "El paciente no está en su lugar de espera correspondiente";
	}

	/**
	 * <b>pre: </b><br>
	 * <b>post:</b><br>
	 * Atiende a un paciente de la cl�nica.
	 * <p>
	 * Si est� en la lista de espera lo remueve y se fija si est� en el patio o en
	 * la sala de espera.
	 * </p>
	 * <ul>
	 * <li>Si est� en el patio lo remueve de ah�.</li>
	 * <li>Si est� en la sala de espera lo saca de ah� y mueve a un paciente del
	 * patio a la sala.</li>
	 * </ul>
	 * <br>
	 * <b>pre: </b>lista de espera, patio y sala de espera inicializados, el m�dico
	 * est� registrado, consultarPorMedico y consultarPorPaciente inicializadas<br>
	 * <b>post:</b> El paciente est� en la lista de atenci�n, lo remueve de la sala
	 * o del patio y las listas de consultas registraron ambas registraron su
	 * consulta<br>
	 * 
	 * @param m El m�dico que atender� al paciente
	 * @param p El paciente a atender.
	 *
	 * @throws PacienteNotFoundException si el paciente no est� registrado en el
	 *                                   sistema indicando que el paciente no est�
	 *                                   registrado
	 */
	public void atiendePaciente(IMedico m, Paciente p) throws PacienteNotFoundException {
		assert listaEspera != null : "La lista de espera no está inicializada";
		assert salaEspera != null : "La sala de espera no está inicializada";
		assert patio != null : "El patio no está inicializado";
		assert medicos.containsKey(m.getDni()) : "El médico no está registrado";

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

		assert listaEnAtencion.contains(p) : "El paciente no quedó registrado en la lista \"en atención\"";
		assert !(salaEspera.getPaciente().equals(p) || patio.contains(p))
				: "El paciente no fue eliminado de su lugar de espera correspondiente";
		assert consultasPorPaciente.get(p).contains(c) : "La consulta no fue registrada correctamente en el paciente";
		assert consultasPorMedico.get(m).contains(c) : "La consulta no fue registrada correctamente en el médico";

	}

	/**
	 * Gestiona el egreso de un paciente de la cl�nica y genera la factura
	 * correspondiente.
	 * <p>
	 * Este m�todo obtiene la habitaci�n (si el paciente estuvo internado) y todas
	 * las consultas realizadas durante su estad�a, crea una factura con esa
	 * informaci�n, y remueve al paciente de todas las listas salvo la de pacientes.
	 * </p>
	 *
	 * <p>
	 * Al finalizar, el paciente es removido de las listas de atenci�n e
	 * internaci�n, y su fecha de ingreso se restablece a null para permitir futuros
	 * ingresos.
	 * </p>
	 * <b>pre: </b>la lista de internados est� inicializada al igual que la lista de
	 * pacientes en atención<br>
	 * <b>post:</b> el paciente ya no est� en la lista de internados ni en la de
	 * atenci�n, su consulta no est� m�s en la lista y la fecha de ingreso es
	 * nula<br>
	 * 
	 * @param p el paciente que egresa de la cl�nica
	 * @return la factura que se le cobra al paciente
	 **/
	public Factura egresaPaciente(Paciente p) {
		assert internados != null : "Lista internados no inicializada";
		assert listaEnAtencion != null : "Lista internados no inicializada";

		IHabitacion h = internados.get(p); // si no fue internado retorna null
		ArrayList<Consulta> consultasPaciente = consultasPorPaciente.get(p);

		Factura f = new Factura(p, h, consultasPaciente);

		consultasPorPaciente.remove(p);
		internados.remove(p);
		removeListaEnAtencion(p);
		p.setFechaIngreso(null); // para que si vuelve a atenderse se renueve la fecha

		assert !internados.containsKey(p) : "El paciente no fue eliminado de la lista de internados correctamente";
		assert !listaEnAtencion.contains(p)
				: "El paciente no fue eliminado de la \"en atención\" de internados correctamente";

		return f;
	}

	/**
	 * A�ade al paciente a la lista de internados con la habitaci�n correspondiente
	 * <b>pre: </b>la lista de internados est� inicializada<br>
	 * <b>post: </b>el paciente est� en la lista de internados<br>
	 * 
	 * @param paciente   el paciente a internar
	 * @param habitacion la habitacion donde ser� internado
	 * @throws PacienteNotFoundException si el paciente no est� registrado en el
	 *                                   sistema indicando que el paciente no est�
	 *                                   registrado
	 */
	public void internaPaciente(Paciente paciente, IHabitacion habitacion) throws PacienteNotFoundException {
		assert habitacion != null : "Habitación inexistente";

		if (!pacientes.containsKey(paciente.getDni()))
			throw new PacienteNotFoundException("Paciente no registrado");
		internados.put(paciente, habitacion);

		assert internados.containsKey(paciente) : "Paciente no internado correctamente";
	}

	/**
	 * + Crea a un m�dico con sus datos esenciales incluyendo posgrado y
	 * contrataci�n <b>pre: </b>la f�brica de m�dicos est� inicializada<br>
	 * <b>post: </b>el m�dico fu� creado con la contrataci�n y el posgrado
	 * correspondientes<br>
	 * 
	 * @param dni
	 * @param nya          nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle        Calle del domicilio
	 * @param altura       Altura del domicilio
	 * @param nroMat       N�mero de matr�cula
	 * @param especialidad
	 * @param contratacion
	 * @param posgrado
	 * @return una instancia IMedico habiendo a�adido su contratacion y posgrado
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

	/**
	 * + Crea a un m�dico con sus datos esenciales sin incluir posgrado <b>pre:
	 * </b>la f�brica de m�dicos est� inicializada<br>
	 * <b>post: </b>el m�dico fu� creado con la contrataci�n correspondiente<br>
	 * 
	 * @param dni
	 * @param nya          nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle        Calle del domicilio
	 * @param altura       Altura del domicilio
	 * @param nroMat       N�mero de matr�cula
	 * @param especialidad
	 * @param contratacion
	 * @return una instancia IMedico habiendo a�adido su contratacion
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

	// medico base (no posgrado, no no contratación)
	/**
	 * + Crea a un m�dico con sus datos esenciales sin incluir posgrado ni
	 * contratacion <b>pre: </b>la f�brica de m�dicos est� inicializada<br>
	 * <b>post: </b>el m�dico fu� creado sin contrataci�n ni posgrado<br>
	 * 
	 * @param dni
	 * @param nya          nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle        Calle del domicilio
	 * @param altura       Altura del domicilio
	 * @param nroMat       N�mero de matr�cula
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
	 * Crea un paciente con dni, n�mero de historia cl�nica, nombre y apellido,
	 * rango etario, tel�fono, domicilio, ciudad <b>pre: </b>la f�brica de pacientes
	 * est� inicializada<br>
	 * <b>post: </b>el paciente fu� creado con sus datos correspondientes<br>
	 * 
	 * @param dni
	 * @param nya         nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle
	 * @param altura
	 * @param nroHC       n�mero de historia cl�nica
	 * @param rangoEtario ni�o, joven o mayor
	 * @return la instancia del paciente creado
	 */
	public Paciente crearPaciente(String dni, String nya, String ciudad, String telefono, String calle, int altura,
			int nroHC, String rangoEtario) {
		Paciente p = pacienteFactory.crearPaciente(dni, nya, ciudad, telefono, calle, altura, nroHC, rangoEtario);
		return p;
	}

	/**
	 * Crea una nueva habitaci�n <b>pre:</b> la f�brica de habitaciones est�
	 * inicializada<br>
	 * <b>post:</b> la habitaci�n fu� creada con el tipo correspondiente<br>
	 * 
	 * @param tipo tipo de habitaci�n
	 * @return una instancia de IHabitacion del tipo solicitado
	 */
	public IHabitacion crearHabitacion(String tipo) {
		IHabitacion h = habitacionFactory.crearHabitacion(tipo);
		return h;
	}

	/**
	 * Crea un asociado con dni, nombre y apellido, tel�fono, domicilio, ciudad,
	 * solicitudes <b>pre: </b>la f�brica de asociados est� inicializada<br>
	 * <b>post: </b>el asociado fu� creado con sus datos correspondientes<br>
	 * 
	 * @param dni
	 * @param nya         nombre y apellido
	 * @param ciudad
	 * @param telefono
	 * @param calle
	 * @param altura
	 * @param solicitudes Cantidad de solicitudes
	 * @return la instancia del asociado creado
	 */
	public Asociado crearAsociado(String dni, String nya, String ciudad, String telefono, String calle, int altura) {
		Asociado a = asociadoFactory.crearAsociado(dni, nya, ciudad, telefono, calle, altura);
		return a;
	}

	/**
	 * Retorna un reporte de consultas del m�dico por d�a enumerando los pacientes
	 * en el per�odo de tiempo indicado
	 * <p>
	 * Muestra de forma cronol�gica las consultas con el nombre del paciente
	 * </p>
	 * <b>pre: </b>la lista consultarPorMedico fu� inicializada<br>
	 * <b>post: </b>se gener� un reporte en base a la lista de consultas
	 * correspondiente<br>
	 * 
	 * @param medico      El m�dico del que se quiere generar un reporte
	 * @param fechaInicio inicio del periodo
	 * @param fechaFin    fin del periodo
	 * @return un reporte generado en base al periodo indicado
	 */
	public Reporte generarReporte(IMedico medico, LocalDate fechaInicio, LocalDate fechaFin)
			throws MedicoNotRegisteredException {
		assert medico != null : "Médico es null";
		assert fechaInicio != null && fechaFin != null : "Alguna de las fechas inválidas";

		if (!medicos.containsKey(medico.getDni())) {
			throw new MedicoNotRegisteredException("El medico no esta registrado en la clinica.");
		}

		ArrayList<Consulta> consultasMedico = consultasPorMedico.get(medico);

		return new Reporte(medico, fechaInicio, fechaFin, consultasMedico);
	}

	public String mostrarAsociados() {
		StringBuilder sb = new StringBuilder();

		sb.append("  LISTADO DE ASOCIADOS:\n");
		sb.append("---------------------------\n");

		if (asociados.isEmpty()) {
			sb.append("No hay asociados registrados.\n");
		} else
			for (Asociado a : asociados.values()) {
				sb.append("• ").append(a.toString()).append("\n");
			}

		return sb.toString();
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

	public Ambulancia getAmbulancia() {
		return ambulancia;
	}

	public Operario getOperario() {
		return operario;
	}

	public boolean isSimulacionActiva() {
		return simulacionActiva;
	}

	public void setSimulacionActiva(boolean simulacionActiva) {
		this.simulacionActiva = simulacionActiva;
	}
	

}
