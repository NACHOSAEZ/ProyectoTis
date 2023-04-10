package db.jdbc;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import pojos.Cliente;
import pojos.Empleado;

public class JDBCManager implements DBManager{

	
	private static Connection c;
	private static Statement stmt;
	final static Logger TERM = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//informacion en la terminal
	private static final String LOCATION = "./db/Aeropuerto.db";
	private static final String ficheroStart = "./db/ddl.sql";
	private static final String ficheroStartAeropuerto = "./db/ficheroStartAeropuertos.sql";
	private static final String ficheroStartVuelo = "./db/ficheroStartVuelos";
	
    private static final String sqlAddCliente = "INSERT INTO Clientes('e-mail',Nombre,Apellido,Contraseña, DNI, NumTelefono) VALUES (?,?,?,?,?,?);";
    private static final String sqlAddEmpleado = "INSERT INTO Empleados('e-mail',Nombre,Apellido,Contraseña, Puesto, Sueldo, Aeropuerto) VALUES (?,?,?,?,?,?,?);";

	/*
	private static final String sqlNumerosElementos = "SELECT COUNT(*) FROM";
	private static final String sqlBuscarVuelosId = "SELECT * FROM Vuelos WHERE IdVuelo = ?;";
	private static final String sqlAnadirVuelo= "INSERT INTO Vuelos(NumVuelo, fecha, hora, asiento) VALUES (?,?,?,?);";
			//fin ficheros sql
	*/
	
	
	//conexion base de datos
	public void connect() {
		try {
			Class.  forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:" + LOCATION);
	        stmt = c.createStatement();
	        createTables();
	        startTables();
	        TERM.info("Se ha inicializado la base de datos");
		}catch (SQLException  | ClassNotFoundException e) {
			TERM.severe("ERROR al iniciar la base de datos de la aplicacion\n" + e.toString());
		}
	}


	public void disconnect() {
		try {
			stmt.close();
			c.close();
			TERM.info("Desconexion de la base de datos");
		}catch (SQLException e) {
			TERM.warning("ERROR al desconectarse de la base de datos\n" + e.toString());
		}
	}

	//metodo para introducir valores a la base de datos
	public void startTables() {
		
	}
	
	//metodo para crear las tablas en la base de datos
	public void createTables() {
		File file = new File(ficheroStart);
		try (Scanner scanner = new Scanner(file)){
			
			String sqlStart = "";
			
			while( scanner.hasNextLine()) {
				sqlStart += scanner.nextLine();
			}
			
			stmt.executeUpdate(sqlStart);
			TERM.info("Tablas iniciales creadas correctamente");
			
		}catch(FileNotFoundException e) {
			TERM.info("Error al leer el fichero para inicializar las tablas\n" + e);
		}catch(SQLException e) {
			TERM.info("Error al leer el codigo SQL del fichero de inicializacion de tablas\n" + e);
		}
	}
	
	
	public void addCliente(Cliente cliente) {
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddCliente);
			prep.setString(1, cliente.getCorreo());
			prep.setString(2, cliente.getNombre());
			prep.setString(3, cliente.getApellido());
			prep.setString(4, cliente.getPassword());
			prep.setString(5, cliente.getDni());
			prep.setString(6, cliente.getNumtelf());
			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un cliente\n" + e.toString());
		}
		}
	//   ('e-mail',Nombre,Apellido,Contraseña, Puesto, Sueldo, Aeropuerto) VALUES (?,?,?,?,?,?,?);";

	public void addEmpleado(Empleado empleado) {
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddEmpleado);
			prep.setString(1, empleado.getCorreo());
			prep.setString(2, empleado.getNombre());
			prep.setString(3, empleado.getApellido());
			prep.setString(4, empleado.getPassword());
			prep.setString(5, empleado.getPuesto());
			prep.setInt(6, empleado.getSueldo());
			//prep.setString(7, empleado.getAeropuerto());
			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un empleado\n" + e.toString());
		}
		}
		
	}

