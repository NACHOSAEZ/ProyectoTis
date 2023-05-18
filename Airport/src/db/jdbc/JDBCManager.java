package db.jdbc;

import java.io.*;
import java.sql.*;
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
    private static final String sqlAddVuelo = "INSERT INTO Vuelos(Hora,Asientos,Origen, Destino, IdCompañia) VALUES (?,?,?,?,?);";


    
    
    private static final String sqlCountElements = "SELECT count(*) FROM ";
    private static final String sqlBuscarEmailCliente = "SELECT * FROM Clientes WHERE Email='";
    private static final String sqlBuscarEmailEmpleado = "SELECT * FROM Empleados WHERE Correo='";
    
    
    private static final String sqlBuscarIdBillete = "SELECT * FROM Billetes WHERE Id='";
    private static final String sqlBuscarIdAeropuerto = "SELECT * FROM Aeropuertos WHERE Id='";
    private static final String sqlBuscarCodigoAeropuerto = "SELECT * FROM Aeropuertos WHERE Codigo='";
    private static final String sqlBuscarNombreCompañia = "SELECT * FROM Compañias WHERE Nombre='";
    private static final String sqlBuscarIdEmpleado = "SELECT * FROM Empleados WHERE Id='";
    private static final String sqlBuscarIdCliente = "SELECT * FROM Clientes WHERE Id='";
    private static final String sqlBuscarVuelosId = "SELECT * FROM Vuelos WHERE Id='";


    
    private static final String sqlEliminarAeropuerto = "DELETE FROM Aeropuertos WHERE Id= ?;";
    private static final String sqlEliminarCompañia = "DELETE FROM Compañias WHERE Id= ?;";
    private static final String sqlEliminarEmpleado = "DELETE FROM Empleados WHERE Id= ?;";
    private static final String sqlEliminarCliente = "DELETE FROM Clientes WHERE Id= ?;";
    private static final String sqlEliminarVuelo = "DELETE FROM Vuelos WHERE Id= ?;";





    
 
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
			
			if(countElements("Empleados") == 0) {
				for(int i = 0 ; i < 50 ; i++) {
					Empleado empleado = defaultvalues.generarEmpleado();
					addEmpleado(empleado);
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
			prep.setString(4, billete.getNumAsiento());
			prep.setBoolean(5, billete.isPagado());
			prep.setInt(6, billete.getCliente().getId());
			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un billete\n" + e.toString());
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
	public ArrayList<Compañia> getCompañias(){
		String sql = "SELECT * FROM Compañias;";
		ArrayList<Compañia> compañias = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String paginaWeb = rs.getString("PaginaWeb");
				String pais = rs.getString("Pais");
				String numTelefono = rs.getString("NumTelefono");
				String email = rs.getString("Email");
				compañias.add(new Compañia(id, nombre, paginaWeb, pais, numTelefono, email));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return compañias;
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
	public boolean addAeropuerto(Aeropuerto aeropuerto) {
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
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String codigo = rs.getString("Codigo");
				aeropuertos.add(new Aeropuerto(id, nombre, codigo));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return aeropuertos;
	}
	
	@Override
	public ArrayList<Cliente> getClientes(){
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
				clientes.add(new Cliente(id, nombre, apellido, dni, email, numTelefono, password));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}
	
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
				
				billetes.add(new Billete(id, categoria, precio, numReserva, numAsiento, pagado, cliente,vuelo));
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return billetes;
	}
	
	
	@Override
	public boolean addCompañia(Compañia compañia) {
		String nombre = compañia.getNombre().toUpperCase(); //cambia el nombre introducido por el usuario en mayusculas
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarNombreCompañia + nombre + "';");
			while(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddCompañia);
			prep.setString(1, nombre);;
			prep.setString(2, compañia.getPaginaweb());
			prep.setString(3, compañia.getPais());
			prep.setString(4, compañia.getNumtelf());
			prep.setString(5, compañia.getCorreo());
			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir una compañia\n" + e.toString());
		}
		return true;
	}
	
	
	@Override
	
	public Compañia getCompañiaPorNombre(String idNombre) {
		Compañia compañia = null;
		try {
			String nombreM = idNombre.toUpperCase(); //cambia el nombre introducido por el usuario en mayusculas
			ResultSet rs = stmt.executeQuery(sqlBuscarNombreCompañia + nombreM + "';"); 
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String paginaWeb = rs.getString("PaginaWeb");
				String pais = rs.getString("Pais");
				String numtelf = rs.getString("NumTelefono");
				String correo = rs.getString("Email");
				compañia = new Compañia(id, nombre, paginaWeb, pais, numtelf, correo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return compañia;
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
	public Cliente getClientePorId(String idCliente) {
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
				cliente = new Cliente(id, nombre, apellido, dni, email, numTelefono, password);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cliente;
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
	public int eliminarCompañia(Compañia compañia) {
		int result = 0;
		try {
			prepEliminarAeropuerto = c.prepareStatement(sqlEliminarCompañia);

			prepEliminarAeropuerto.setInt(1, compañia.getId());
			result = prepEliminarAeropuerto.executeUpdate();
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
	@Override
	//TODO
	public Vuelo getVueloPorId(int numVuelo) {
		Vuelo vuelo = null;
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarVuelosId + numVuelo + "';"); 
			if(rs.next()) {
				int idVuelo = rs.getInt("NumVuelo");
				String hora = rs.getString("Hora");
				int asientos = rs.getInt("Asientos");
				String origen = rs.getString("Origen");
				String destino = rs.getString("Destino");

				vuelo = new Vuelo(idVuelo, hora, asientos, origen, destino, null);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return vuelo;
	}
	
	@Override
	public ArrayList<Vuelo> getVuelos(){
		String sql = "SELECT * FROM Vuelos;";
		ArrayList<Vuelo> vuelos = new ArrayList<>();
		try {
			
	        Statement stmt1 = c.createStatement();
			ResultSet rs = stmt1.executeQuery(sql);
			while(rs.next()) {
				int idVuelo = rs.getInt("NumVuelo");
				String hora = rs.getString("Hora");
				int asientos = rs.getInt("Asientos");
				String origen = rs.getString("Origen");
				String destino = rs.getString("Destino");
				Compañia compañis = new Compañia();
				/*int idCompañia = rs.getInt("IdAeropuerto");
				aeropuerto = getAeropuertoPorId(idAeropuerto);
				vuelos.add(new Vuelo(idVuelo, hora, asientos));*/
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
	public boolean addVuelo(Vuelo vuelo) {
		try {
			ResultSet rs = stmt.executeQuery(sqlBuscarVuelosId + vuelo.getIdVuelo()+ "';");
			while(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement prep = c.prepareStatement(sqlAddVuelo);
			prep.setInt(1, vuelo.getIdVuelo());
			prep.setString(2, vuelo.getHora());
			prep.setInt(3, vuelo.getAsientos());
			prep.setString(4, vuelo.getOrigen());
			prep.setString(5, vuelo.getDestino());
			//FALTA COMPAÑIA

			prep.executeUpdate();
			prep.close();
		}catch(SQLException e) {
			TERM.warning("Error al añadir un vuelo\n" + e.toString());
		}
		return true;
		}


	
	}

	

