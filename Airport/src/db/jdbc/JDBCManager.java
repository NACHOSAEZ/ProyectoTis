package db.jdbc;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import defaultValues.DefaultValues;
import pojos.Aeropuerto;
import pojos.Billete;
import pojos.Cliente;
import pojos.Compañia;
import pojos.Empleado;
import pojos.Vuelo;

public class JDBCManager implements DBManager{

	
	private static Connection c;
	private static Statement stmt;
	
	private PreparedStatement prepAddCliente;
	private PreparedStatement prepAddEmpleado;
	private PreparedStatement prepAddAeropuerto;
	private PreparedStatement prepAddCompañia;
	private PreparedStatement prepEliminarAeropuerto;
	private PreparedStatement prepEliminarVuelo;
	private PreparedStatement prepEliminarCompañia;
	private PreparedStatement prepEliminarEmpleado;
	private PreparedStatement prepEliminarCliente;

	

	
	
	final static DefaultValues defaultvalues= new DefaultValues();
	final static Logger TERM = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	private static final String LOCATION = "./db/Aeropuerto.db";
	private static final String ficheroStart = "./db/ddl.sql";
	private static final String ficheroStartAeropuerto = "./db/dml_aeropuertos.sql";
	private static final String ficheroStartCompañia = "./db/dml_companias.sql";
	
	
    private static final String sqlAddCliente = "INSERT INTO Clientes(Nombre,Apellido,DNI, Email, NumTelefono, Password) VALUES (?,?,?,?,?,?);";
    private static final String sqlAddEmpleado = "INSERT INTO Empleados(Nombre,Apellido,Correo,Password,Puesto,Sueldo,DNI,IdAeropuerto) VALUES (?,?,?,?,?,?,?,?);";
    private static final String sqlAddAeropuerto = "INSERT INTO Aeropuertos(Nombre,Codigo) VALUES (?,?);";
    private static final String sqlAddCompañia = "INSERT INTO Compañias(Nombre,PaginaWeb,Pais, NumTelefono, Email) VALUES (?,?,?,?,?);";
    private static final String sqlAddBillete = "INSERT INTO Billetes(Categoria,Precio,NumReserva, NumAsiento, Pagado, IdCliente) VALUES (?,?,?,?,?,?);";
    private static final String sqlAddVuelo = "INSERT INTO Vuelos(Hora,Asientos,IdAeropuertoOrigen, IdAeropuertoDestino) VALUES (?,?,?,?);";
    private static final String sqlAddCompañias_Vuelos = "INSERT INTO Compañias_Vuelos(IdCompañia,IdVuelo) VALUES (?,?);";


    
    
    private static final String sqlCountElements = "SELECT count(*) FROM ";
    private static final String sqlBuscarEmailCliente = "SELECT * FROM Clientes WHERE Email='";
    private static final String sqlBuscarEmailEmpleado = "SELECT * FROM Empleados WHERE Correo='";
    
    
    private static final String sqlBuscarIdCompañia = "SELECT * FROM Compañias WHERE Id='";
    private static final String sqlBuscarIdBillete = "SELECT * FROM Billetes WHERE Id='";
    private static final String sqlBuscarIdAeropuerto = "SELECT * FROM Aeropuertos WHERE Id='";
    private static final String sqlBuscarCodigoAeropuerto = "SELECT * FROM Aeropuertos WHERE Codigo='";
    private static final String sqlBuscarNombreCompañia = "SELECT * FROM Compañias WHERE Nombre='";
    private static final String sqlBuscarIdEmpleado = "SELECT * FROM Empleados WHERE Id='";
    private static final String sqlBuscarIdAeropuertoEmpleado = "SELECT * FROM Empleados WHERE IdAeropuerto='";
    private static final String sqlBuscarIdVueloCompañia = "SELECT Vuelos FROM Vuelos JOIN Compañias_Vuelos ON Vuelos.NumVuelo = Compañias_Vuelos.IdVuelo JOIN Compañias ON Compañias.Id = Compañias_Vuelos.IdCompañia WHERE Compañias.Id =";


    private static final String sqlBuscarIdCliente = "SELECT * FROM Clientes WHERE Id='";
    private static final String sqlBuscarVuelosId = "SELECT * FROM Vuelos WHERE NumVuelo='";



    
    private static final String sqlEliminarAeropuerto = "DELETE FROM Aeropuertos WHERE Id= ?;";
    private static final String sqlEliminarCompañia = "DELETE FROM Compañias WHERE Id= ?;";
    private static final String sqlEliminarEmpleado = "DELETE FROM Empleados WHERE Id= ?;";
    private static final String sqlEliminarCliente = "DELETE FROM Clientes WHERE Id= ?;";
    private static final String sqlEliminarVuelo = "DELETE FROM Vuelos WHERE NumVuelo= ?;";





    
 
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
			
			if(countElements("Empleados") == 0) {
				for(int i = 0 ; i < 50 ; i++) {
					Empleado empleado = defaultvalues.generarEmpleado();
					addEmpleado(empleado);
				}
			}	
			if(countElements("Vuelos") == 0) {
				for(int i = 0 ; i < 5 ; i++) {
					Vuelo vuelo = defaultvalues.generarVuelo();
					addVuelo(vuelo);
					addVuelo_Compañia(vuelo);
				}
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		TERM.info("Tablas inicializadas");
	}
	
	@Override
	public boolean addEmpleado(Empleado empleado) {
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarEmailEmpleado + empleado.getCorreo() + "';");
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddEmpleado);
			prep.setString(1, empleado.getNombre());
			prep.setString(2, empleado.getApellido());
			prep.setString(3, empleado.getCorreo());
			prep.setString(4, empleado.getPassword());
			prep.setString(5, empleado.getPuesto());
			prep.setInt(6, empleado.getSueldo());
			prep.setString(7, empleado.getDni());
			prep.setInt(8,empleado.getAeropuerto().getId());
			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un empleado\n" + e.toString());
		}
		return true;
		}
	
	@Override
	public ArrayList<Empleado> getEmpleados(){
		String sql = "SELECT * FROM Empleados;";

		ArrayList<Empleado> empleados = new ArrayList<>();
		try {
	        Statement stmt1 = c.createStatement();
			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()) {
				
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String apellido = rs.getString("Apellido");
				String correo = rs.getString("Correo");
				String password = rs.getString("Password");
				String puesto = rs.getString("Puesto");
				int sueldo = rs.getInt("Sueldo");
				String dni = rs.getString("DNI");
				Aeropuerto aeropuerto = new Aeropuerto();
				int idAeropuerto = rs.getInt("IdAeropuerto");
				aeropuerto = getAeropuertoPorId(idAeropuerto);
				
				empleados.add(new Empleado(id, nombre, apellido, correo, password, puesto, sueldo, dni, aeropuerto));
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return empleados;
	}
	
	@Override
	public Empleado getEmpleadoPorId(String idEmpleado) {
		Empleado empleado = null;
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarIdEmpleado + idEmpleado + "';"); 
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String apellido = rs.getString("Apellido");
				String correo = rs.getString("Correo");
				String password = rs.getString("Password");
				String puesto = rs.getString("Puesto");
				int sueldo = rs.getInt("Sueldo");
				String dni = rs.getString("DNI");
				Aeropuerto aeropuerto = new Aeropuerto();
				int idAeropuerto = rs.getInt("IdAeropuerto");
				aeropuerto = getAeropuertoPorId(idAeropuerto);
				
				empleado = new Empleado(id, nombre, apellido, correo, password, puesto, sueldo, dni, aeropuerto);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return empleado;
	}
	
	@Override
	public int eliminarEmpleado(Empleado empleado) {
		int result = 0;
		try {
			prepEliminarEmpleado = c.prepareStatement(sqlEliminarEmpleado);

			prepEliminarEmpleado.setInt(1, empleado.getId());
			result = prepEliminarEmpleado.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 1) {
			TERM.info(empleado + " eliminado con éxito");
		} else {
			TERM.info("No existe el " + empleado);
		}
		return result;
	}
	
	
	@Override
	public boolean addAeropuerto(Aeropuerto aeropuerto) {
		//TODO
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarCodigoAeropuerto + aeropuerto.getCodigo()+ "';");
			while(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
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
	
	
	
	
	@Override
	public ArrayList<Aeropuerto> getAeropuertos(){
		String sql = "SELECT * FROM Aeropuertos;";
		ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
		ArrayList<Empleado> empleadosAeropuerto = new ArrayList<>();
		try {
			Statement stmt1 = c.createStatement();
			ResultSet rs = stmt1.executeQuery(sql);
			
			while(rs.next()) {
				
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String codigo = rs.getString("Codigo");
				empleadosAeropuerto = getEmpleadoPorAeropuerto(id);
				aeropuertos.add(new Aeropuerto(id, nombre, codigo,empleadosAeropuerto));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return aeropuertos;
	}
	
	@Override
	public ArrayList<Empleado> getEmpleadoPorAeropuerto(int idAeropuerto){
		ArrayList<Empleado> empleadosAeropuerto = new ArrayList<>();
		try {
			Statement stm1 = c.createStatement();
			ResultSet rs = stm1.executeQuery(sqlBuscarIdAeropuertoEmpleado + idAeropuerto + "';");
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String apellido = rs.getString("Apellido");
				String correo = rs.getString("Correo");
				String password = rs.getString("Password");
				String puesto = rs.getString("Puesto");
				int sueldo = rs.getInt("Sueldo");
				String dni = rs.getString("DNI");
				int idAerp = rs.getInt("IdAeropuerto");
				Aeropuerto aeropuerto = new Aeropuerto();
				aeropuerto = getAeropuertoPorId(idAerp);
				empleadosAeropuerto.add(new Empleado(id, nombre, apellido, correo, password, puesto, sueldo, dni, aeropuerto));
			}
			stm1.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return empleadosAeropuerto;
	}
	@Override
	
	public Aeropuerto getAeropuertoPorCodigo(String idCodigo) {
		Aeropuerto aeropuerto = null;
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarCodigoAeropuerto + idCodigo + "';"); 
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String codigo = rs.getString("Codigo");
				aeropuerto = new Aeropuerto(id, nombre, codigo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return aeropuerto;
	}
	
	@Override
	public Aeropuerto getAeropuertoPorId(int idAeropuerto) {
		Aeropuerto aeropuerto = null;
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarIdAeropuerto + idAeropuerto + "';"); 
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String codigo = rs.getString("Codigo");
				aeropuerto = new Aeropuerto(id, nombre, codigo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return aeropuerto;
	}
	
	@Override
	public int eliminarAeropuerto(Aeropuerto aeropuerto) {
		int result = 0;
		try {
			prepEliminarAeropuerto = c.prepareStatement(sqlEliminarAeropuerto);

			prepEliminarAeropuerto.setInt(1, aeropuerto.getId());
			result = prepEliminarAeropuerto.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 1) {
			TERM.info(aeropuerto + " eliminado con éxito");
		} else {
			TERM.info("No existe el " + aeropuerto);
		}
		return result;
	}
	
	
	
	@Override
	public ArrayList<Compañia> getCompañias(){
		String sql = "SELECT * FROM Compañias;";
		ArrayList<Compañia> compañias = new ArrayList<>();
		ArrayList<Vuelo> vuelos = new ArrayList<>();
		try {
			Statement stmt1 = c.createStatement();
			ResultSet rs = stmt1.executeQuery(sql);
			while(rs.next()) {
				
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String paginaWeb = rs.getString("PaginaWeb");
				String pais = rs.getString("Pais");
				String numTelefono = rs.getString("NumTelefono");
				String email = rs.getString("Email");
				compañias.add(new Compañia(id, email, numTelefono, nombre, paginaWeb, pais));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return compañias;
	}
	
	//TODO OVERRIDE
	private ArrayList<Vuelo> getVuelosPorCompañia(int idVuelo) {
		ArrayList<Vuelo> vuelosCompañia = new ArrayList<>();
		try {
			Statement stm1 = c.createStatement();
			ResultSet rs = stm1.executeQuery(sqlBuscarIdVueloCompañia + idVuelo + "';");
			while(rs.next()) {
				int numVuelo = rs.getInt("NumVuelo");
				String hora = rs.getString("Hora");
				int asientos = rs.getInt("Asientos");
				Aeropuerto origen = new Aeropuerto();
				int idOrigen = rs.getInt("IdAeropuertoOrigen");
				origen = getAeropuertoPorId(idOrigen);
				Aeropuerto destino = new Aeropuerto();
				int idDestino = rs.getInt("IdAeropuertoDestino");
				destino = getAeropuertoPorId(idDestino);
				vuelosCompañia.add(new Vuelo(numVuelo, hora, asientos, origen, destino));
			}
			stm1.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return vuelosCompañia;
	}


	@Override
	public boolean addVuelo(Vuelo vuelo) {
	//TODO AÑADIR VALORES COMPAÑIAS-VUELOS
		try {
			String query = sqlBuscarVuelosId + vuelo.getIdVuelo() + "';";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddVuelo);
			prep.setString(1, vuelo.getHora());
			prep.setInt(2, vuelo.getAsientos());
			prep.setInt(3, vuelo.getOrigen().getId());
			prep.setInt(4, vuelo.getDestino().getId());

			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un vuelo\n" + e.toString());
		}
		return true;
		}
	
	@Override
	public Vuelo getVueloPorId(int numVuelo) {
		Vuelo vuelo = null;
		Aeropuerto aeropuertoOrigen = null;
		Aeropuerto aeropuertoDestino = null;		
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarVuelosId + numVuelo + "';"); 
			if(rs.next()) {
				int numeroVuelo = rs.getInt("NumVuelo");
				String hora = rs.getString("Hora");
				int asientos = rs.getInt("Asientos");
				int idOrigen = rs.getInt("IdAeropuertoOrigen");
				int idDestino = rs.getInt("IdAeropuertoDestino");
				aeropuertoOrigen = getAeropuertoPorId(idOrigen);
				aeropuertoDestino = getAeropuertoPorId(idDestino);
				vuelo = new Vuelo(numeroVuelo, hora, asientos, aeropuertoOrigen, aeropuertoDestino);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return vuelo;
	}

	@Override
	public ArrayList<Vuelo> getVuelos() {
		String sql = "SELECT * FROM Vuelos;";
		ArrayList<Vuelo> vuelos = new ArrayList<>();
		Aeropuerto aeropuertoOrigen = null;
		Aeropuerto aeropuertoDestino = null;
		try {
			Statement stmt1 = c.createStatement();
			ResultSet rs = stmt1.executeQuery(sql);
			while(rs.next()) {
				
				int id = rs.getInt("NumVuelo");
				String hora = rs.getString("Hora");
				int asientos = rs.getInt("Asientos");
				int origen = rs.getInt("IdAeropuertoOrigen");
				int destino = rs.getInt("IdAeropuertoDestino");
				aeropuertoOrigen = getAeropuertoPorId(origen);
				aeropuertoDestino = getAeropuertoPorId(destino);
				vuelos.add(new Vuelo(id, hora, asientos, aeropuertoOrigen, aeropuertoDestino));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return vuelos;
	}

	@Override
	public int eliminarVuelo(Vuelo vuelo) {
		int result = 0;
		try {
			prepEliminarVuelo = c.prepareStatement(sqlEliminarVuelo);

			prepEliminarVuelo.setInt(1, vuelo.getIdVuelo());
			result = prepEliminarVuelo.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 1) {
			TERM.info(vuelo + " eliminado con éxito");
		} else {
			TERM.info("No existe el " + vuelo);
		}
		return result;
	}
	
	
	
	@Override
	public boolean addCliente(Cliente cliente) {
		try {
			String query = sqlBuscarEmailCliente + cliente.getCorreo() + "';";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
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
	public int eliminarCliente(Cliente cliente) {
		int result = 0;
		try {
			prepEliminarCliente = c.prepareStatement(sqlEliminarCliente);

			prepEliminarCliente.setInt(1, cliente.getId());
			result = prepEliminarCliente.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 1) {
			TERM.info(cliente + " eliminado con éxito");
		} else {
			TERM.info("No existe el " + cliente);
		}
		return result;
	}
	
	@Override
	public ArrayList<Cliente> getClientes(){
		//TODO
		String sql = "SELECT * FROM Clientes;";
		ArrayList<Cliente> clientes = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String apellido = rs.getString("Apellido");
				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String numTelefono = rs.getString("NumTelefono");
				String password = rs.getString("Password");
				clientes.add(new Cliente(id, nombre, apellido, dni, email, numTelefono, password, getBilletes())); //ARRAY DE BILLETES
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}
	
		

	@Override
	public Cliente getClientePorId(String idCliente) {
		//TODO
		Cliente cliente = null;
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarIdCliente + idCliente + "';"); 
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String apellido = rs.getString("Apellido");
				String dni = rs.getString("Dni");
				String email = rs.getString("Email");
				String numTelefono = rs.getString("NumTelefono");
				String password = rs.getString("Password");
				cliente = new Cliente(id, nombre, apellido, dni, email, numTelefono, password,getBilletes());//LEER TODOS LOS BILLETES DEL CLIENTE
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}

	@Override
	public boolean addCompañia(Compañia compañia) {
		try {
			String query = sqlBuscarIdCompañia + compañia.getNombre() + "';";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddCompañia);
			prep.setString(1, compañia.getNombre());
			prep.setString(2, compañia.getPaginaweb());
			prep.setString(3, compañia.getPais());
			prep.setString(4, compañia.getNumtelf());
			prep.setString(5, compañia.getCorreo());

			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un vuelo\n" + e.toString());
		}
		return true;
	}

	@Override
	public Compañia getCompañiaPorId(int idAeropuerto) {
		Compañia compañia = null;
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarIdCompañia + idAeropuerto + "';"); 
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String paginaWeb = rs.getString("PaginaWeb");
				String pais = rs.getString("Pais");
				String numTelefono = rs.getString("NumTelefono");
				String email = rs.getString("Email");
				compañia = new Compañia(id, email, numTelefono, nombre, paginaWeb, pais);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return compañia;
	}
	@Override
	public Compañia getCompañiaPorNombre(String nombreCompañia) {
		Compañia compañia = null;
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarNombreCompañia + nombreCompañia + "';"); 
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String paginaWeb = rs.getString("PaginaWeb");
				String pais = rs.getString("Pais");
				String numTelefono = rs.getString("NumTelefono");
				String email = rs.getString("Email");
				compañia = new Compañia(id, email, numTelefono, nombre, paginaWeb, pais);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return compañia;
	}

	@Override
	public int eliminarCompañia(Compañia compañia) {
		int result = 0;
		try {
			prepEliminarCompañia = c.prepareStatement(sqlEliminarCompañia);

			prepEliminarCompañia.setInt(1, compañia.getId());
			result = prepEliminarCompañia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result == 1) {
			TERM.info(compañia + " eliminado con éxito");
		} else {
			TERM.info("No existe el " + compañia);
		}
		return result;
	}
	

    public static int getIdUltimoVueloAñadido() {
        int idVuelo = -1;

        try {
            String query = "SELECT NumVuelo FROM vuelos ORDER BY NumVuelo DESC LIMIT 1";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                idVuelo = resultSet.getInt("NumVuelo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idVuelo;
    }
	
	
	@Override
	public boolean addVuelo_Compañia(Vuelo vuelo) {
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddCompañias_Vuelos);
			prep.setInt(1, vuelo.getCompañia().getId());
			int idVuelo = getIdUltimoVueloAñadido();
			prep.setInt(2, idVuelo);

			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un vuelo-compañia\n" + e.toString());
		}
		return true;
	}

	@Override
	public ArrayList<Billete> getBilletes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addBillete(Billete billete) {
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarIdBillete + billete.getId() + "';");
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddBillete);
			prep.setString(1, billete.getCategoria());
			prep.setInt(2, billete.getPrecio());
			prep.setInt(3, billete.getNumReserva());
			prep.setInt(4, billete.getNumAsiento());
			prep.setBoolean(5, billete.isPagado());
			prep.setInt(6, billete.getCliente().getId());
			prep.setInt(7, billete.getVuelo().getIdVuelo());

			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un billete\n" + e.toString());
		}
		return true;
		}
	
/*
	@Override
	public ArrayList<Billete> getBilletes(){
		//TODO
		String sql = "SELECT * FROM Billetes;";

		ArrayList<Billete> billetes = new ArrayList<>();
		try {
	        Statement stmt1 = c.createStatement();
			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()) {
				
				int id = rs.getInt("Id");
				String categoria = rs.getString("Categoria");
				int precio = rs.getInt("Precio");
				int numReserva = rs.getInt("NumReserva");
				String numAsiento = rs.getString("NumAsiento");
				Boolean pagado = rs.getBoolean("Pagado");
				Cliente cliente = new Cliente();
				String idCliente = rs.getString("IdCliente");
				cliente = getClientePorId(idCliente);
				Vuelo vuelo = new Vuelo();
				String idVuelo = rs.getString("IdVuelo");
				//vuelo = getVueloPorId(idVuelo);
				
				//billetes.add(new Billete(id, categoria, precio, numReserva, numAsiento, pagado, cliente,vuelo));
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return billetes;
	}
	



	*/
	}

	

