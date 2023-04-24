package db.jdbc;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import defaultValues.DefaultValues;
import pojos.Aeropuerto;
import pojos.Cliente;
import pojos.Empleado;

public class JDBCManager implements DBManager{

	
	private static Connection c;
	private static Statement stmt;
	
	private PreparedStatement prepAddCliente;
	private PreparedStatement prepAddEmpleado;
	private PreparedStatement prepAddAeropuerto;
	private PreparedStatement prepAddCompañia;
	
	
	final static DefaultValues defaultvalues= new DefaultValues();
	final static Logger TERM = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	private static final String LOCATION = "./db/Aeropuerto.db";
	private static final String ficheroStart = "./db/ddl.sql";
	private static final String ficheroStartAeropuerto = "./db/dml_aeropuertos.sql";
	private static final String ficheroStartCompañia = "./db/dml_companias.sql";
	
	
    private static final String sqlAddCliente = "INSERT INTO Clientes(Nombre,Apellido,DNI, Email, NumTelefono, Password) VALUES (?,?,?,?,?,?);";
    private static final String sqlAddEmpleado = "INSERT INTO Empleado(Nombre,Apellido,Puesto,Sueldo,DNI) VALUES (?,?,?,?,?);";
    private static final String sqlAddAeropuerto = "INSERT INTO Aeropuertos(Nombre,Codigo) VALUES (?,?);";
    private static final String sqlAddCompañia = "INSERT INTO Compañias(Nombre,PaginaWeb,Pais, NumTelefono, Email) VALUES (?,?,?,?,?);";
    
    
    private static final String sqlCountElements = "SELECT count(*) FROM ";
    private static final String sqlBuscarEmailCliente = "SELECT * FROM Clientes WHERE Email='";
    private static final String sqlBuscarCodigoAeropuerto = "SELECT * FROM Aeropuertos WHERE Codigo=";
    
    
	/*
	private static final String sqlBuscarVuelosId = "SELECT * FROM Vuelos WHERE IdVuelo = ?;";
	private static final String sqlAnadirVuelo= "INSERT INTO Vuelos(NumVuelo, fecha, hora, asiento) VALUES (?,?,?,?);";
	*/
	
    
    
	
	//conexion base de datos
	public void connect() {
		try {
			Class.  forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:" + LOCATION);
	        stmt = c.createStatement();

	        TERM.info("Se ha inicializado la base de datos");
		}catch (SQLException  | ClassNotFoundException e) {
			TERM.severe("ERROR al iniciar la base de datos de la aplicacion\n" + e.toString());
		}
        createTables();
        startTables();
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

	public int countElements(String nombreTabla) {
		int numElementos = 0;
		try {
			ResultSet rs = stmt.executeQuery(sqlCountElements + nombreTabla + ";");
			numElementos = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numElementos;
	}
	//metodo para introducir valores predeterminados  a la base de datos
	public void startTables() {
		try {
			
			prepAddAeropuerto = c.prepareStatement(sqlAddAeropuerto);
			prepAddCompañia = c.prepareStatement(sqlAddCompañia);
			prepAddCliente = c.prepareStatement(sqlAddCliente);
			
			if(countElements("Compañias") == 0) {
				stmt.executeUpdate(archivo(ficheroStartCompañia));
				TERM.info("Se ha introducido los datos predeterminado a la tabla de compañias");
			}else {
				TERM.info("La tabla de compañias ya esta iniciada");
			}
			
			if(countElements("Aeropuertos") == 0) {
				stmt.executeUpdate(archivo(ficheroStartAeropuerto));
				TERM.info("Se ha introducido los datos predeterminado a la tabla de aeropuertos");
			}else {
				TERM.info("La tabla de aeropuertos ya esta iniciada");
			}
			
			if(countElements("Clientes") == 0) {
				for(int i = 0 ; i < 50 ; i++) {
					Cliente cliente = defaultvalues.generarCliente();
					addCliente(cliente);
				}
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		TERM.info("Tablas inicializadas");
	}
	
	//metodo para crear las tablas en la base de datos
	public void createTables() {
		try{
			stmt.executeUpdate(archivo(ficheroStart));
			TERM.info("Tablas inicializadas correctamente");
			
		}catch(SQLException e) {
			TERM.info("Error al leer el codigo SQL del fichero de inicializacion de tablas\n" + e);
		}
	}
	
	public String archivo(String nombreArchivo) {
		String contenido ="";
		File archivo= new File(nombreArchivo);
		
		try(Scanner scanner = new Scanner(archivo)){
			
			while(scanner.hasNext()) {
				
				contenido += scanner.nextLine();
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return contenido;
	}
	
	@Override
	public boolean addCliente(Cliente cliente) {
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarEmailCliente + cliente.getCorreo() + "';");
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddCliente);
			prep.setString(1, cliente.getNombre());
			prep.setString(2, cliente.getApellido());
			prep.setString(3, cliente.getDni());
			prep.setString(4, cliente.getCorreo());
			prep.setString(5, cliente.getNumtelf());
			prep.setString(6, cliente.getPassword());
			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un cliente\n" + e.toString());
		}
		return true;
		}
	
	@Override
	public boolean addAeropuerto(Aeropuerto aeropuerto) {
		ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarCodigoAeropuerto + aeropuerto.getCodigo()+ "';");
			while(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddAeropuerto);
			prep.setString(1, aeropuerto.getNombre());;
			prep.setString(2, aeropuerto.getCodigo());
			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un aeropuerto\n" + e.toString());
		}
		return true;
		}
	
	
	}

	

