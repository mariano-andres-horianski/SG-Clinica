package persistencia.BasedeDatos;

import java.sql.*;

/**
 * Singleton que provee conexiones JDBC a la base de datos MySQL del sistema.
 * 
 */
public class BDConexion {
    private static BDConexion instance;
    private Connection conn;
    private Statement sentencia;

    private String baseDatos = "jdbc:mariadb://localhost:3308/Grupo_8";
    private String usuario = "progra_c";
    private String password = "progra_c";
    
    /**
     * Constructor privado que inicializa la conexi�n y la sentencia.
     * 
     * <p><b>Precondici�n:</b> El driver JDBC de MySQL debe estar disponible en el classpath. </p>
     * <p><b>Postcondici�n:</b> Si no ocurre una excepci�n, conn y sentencia se inicializan correctamente 
     * y la conexi�n queda en modo auto-commit. </p>
     * 
     */
    
    private BDConexion() {
        try {
        	Class.forName("org.mariadb.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("No se pudo cargar el puente JDBC-ODBC");
            return;
        }
        try {
        	this.conn = DriverManager.getConnection(baseDatos, usuario, password);
        	this.sentencia=conn.createStatement();
        	this.conn.setAutoCommit(true);
        	
        	assert conn != null : "La conexion no deberia ser nula tras inicializar.";
            assert sentencia != null : "La sentencia no deberia ser nula tras inicializar.";
            assert conn.getAutoCommit() : "La conexi�n deberia estar en auto-commit.";
        }
        catch (Exception e) {
            System.out.println("No se pudo iniciar la conexion");
            e.printStackTrace();
            return;
        }
        
    }

    /**
     * Devuelve la instancia �nica del singleton, garantizando que la conexi�n sea v�lida.
     *
     * <p><b>Precondici�n:</b> Ninguna.</p>
     * <p><b>Postcondici�n:</b> Se devuelve una instancia (no-nula) de BDConexion. Si exist�a una instancia previa con conexi�n cerrada,
     * se intenta crear una nueva instancia con conexi�n abierta.</p>
     *
     * @return instancia �nica de BDConexion
     * @throws SQLException si ocurre un error al verificar o crear la conexi�n
     */
    public static synchronized BDConexion getInstance() throws SQLException {
        if (instance == null || instance.getConnection() == null || instance.getConnection().isClosed()) {
            instance = new BDConexion();
        }
        
        assert instance != null : "La instancia BDConexion no deber�a ser nula.";
        assert instance.getConnection() != null : "La conexi�n no deber�a ser nula en una instancia v�lida.";

        return instance;
    }

    /**
     * Devuelve la conexi�n JDBC actual.
     *
     * <p><b>Precondici�n:</b> La instancia deber�a haber sido inicializada mediante getInstance() para garantizar
     * que la conexi�n est� disponible.</p>
     *
     * <p><b>Postcondici�n:</b> Se devuelve la referencia a conn (puede ser null si la inicializaci�n fall�).</p>
     */
    public Connection getConnection() {
        return conn;
    }
    
    /**
     * Devuelve el Statement asociado a la conexi�n.
     *
     * <p><b>Precondici�n:</b> La conexi�n debe estar activa para que la sentencia sea usable.</p>
     *
     * <p><b>Postcondici�n:</b> Se devuelve sentencia o null si no se cre�.</p>
     */
    public Statement getSentencia() {
    	try {
			assert conn != null && !conn.isClosed() : "No se puede obtener la sentencia si la conexi�n est� cerrada.";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sentencia;
	}

    /**
     * Cierra la conexi�n si est� abierta.
     *
     * <p><b>Precondici�n:</b> La instancia debe haber sido inicializada</p>
     *
     * <p><b>Postcondici�n:</b>
     * <ul>
     *   <li>Si conn no era nula ni estaba cerrada, queda cerrada tras ejecutar este m�todo.</li>
     *   <li>Tras cerrar la conexi�n, la invariante de estado que exige que sentencia sea usable ya no se cumple
     *       (es decir, sentencia deja de ser utilizable mientras la conexi�n est� cerrada).</li>
     * </ul>
     * </p>
     */
	public void close() {
        try {
            if (conn != null && !conn.isClosed()) 
            	conn.close();
            assert conn == null || conn.isClosed() : "La conexi�n deber�a estar cerrada tras close().";
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
