package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import db.interfaces.DBManager;
import db.interfaces.UsuariosManager;
import db.jdbc.JDBCManager;
import db.jpa.JPAUsuariosManager;
import defaultValues.DefaultValues;
import logging.MyLogger;
import pojos.Rol;
import pojos.Usuario;
import pojos.Aeropuerto;
import pojos.Billete;
import pojos.Cliente;
import pojos.Compañia;
import pojos.Empleado;
import pojos.Vuelo;
import xml.AeropuertoAleatorio;


public class Menu {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static UsuariosManager userman;
	private final static AeropuertoAleatorio altA = new AeropuertoAleatorio();
	private final static DefaultValues defaultV = new DefaultValues();



	
	private static final String[] MENU_START = {"Salir", "Registrarse", "Iniciar Sesion"};
	private static final String[] MENU_ROL = {"Salir", "Registar Cliente", "menuEmpleado"};

	private static Usuario usuario;

	private static final String[] MENU_USUARIO = {"Salir", "Listar Vuelos", "Buscar Vuelo","Listar aeropuertos","Buscar aeropuerto","Listar todos billetes","Comprar billetes", "Listar mis billetes"};
	private static final String[] MENU_EMPLEADO = {"Salir", "Vuelos", "Compañias", "Aeropuertos", "Empleados", "Clientes","Marshalling","Unmarshalling"};

	
	private static final String[] MENU_AEROPUERTO = {"Salir", "Listar aeropuertos", "Añadir aeropuerto", "Consultar aeropuerto por codigo", "Eliminar aeropuerto"};
	private static final String[] MENU_COMPAÑIAS = {"Salir", "Listar compañias", "Añadir compañia", "Consultar compañia por nombre", "Eliminar compañia","Listar vuelos de una compañia"};
	private static final String[] MENU_EMPLEADOS = {"Salir", "Añadir Empleado", "Eliminar empleado", "Listar Empleados", "Buscar empleado por Id"};
	private static final String[] MENU_CLIENTES = {"Salir", "Listar Clientes", "Buscar Cliente por Id" , "Eliminar Cliente"};
	private static final String[] MENU_BILLETES = {"Salir", "Comprar billete","Listar billetes de un cliente"};
	private static final String[] MENU_VUELOS = {"Salir", "Listar Vuelos", "Buscar vuelon por Id", "Eliminar Vuelo", "Añadir vuelo"};







	public static void main (String[] args) throws IOException, JAXBException{
		MyLogger.setupFromFile();
		userman = new JPAUsuariosManager();
		userman.connect();
		dbman = new JDBCManager();
		dbman.connect();
		System.out.println("\n *** BIENVENIDO A LA BASE DE DATOS DE LOS AEROPUERTOS DE ESPAÑA *** \n");
		int bucle=-1;
		do {
			bucle = showmenu(MENU_START);
			LOGGER.info("USTED HA ELEGIDO " + bucle + "\n");

			switch(bucle) {
			
			case 1 : register();
			case 2 : inicioSesion();
			}
		}while(bucle != 0);
		
		System.out.println("\n *** GRACIAS POR USAR NUESTRA BASE DE DATOS ***");
		dbman.disconnect();
	}
	
	private static void register() {
		// TODO Auto-generated method stub
		try {
			String email = usuarioTexto("Indique su email:");
			String pass = usuarioTexto("Indique su contraseña:");
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			byte[] hash = md.digest();
			String nombre = usuarioTexto("Indique su nombre:");
			String apellido = usuarioTexto("Indique su apellido:");
			String dni = usuarioTexto("Indique su dni:");
			
			System.out.println(userman.getRoles());
			int rolId = Integer.parseInt(usuarioTexto("Indique el id del rol:"));
			//TODO Asegurarse que el id es válido
			Rol rol = userman.getRolById(rolId);
			Usuario usuario = new Usuario(email, hash, rol);
			rol.addUsuario(usuario);
			userman.addUsuario(usuario);
			if(rol.getNombre().equals("cliente")) {
				String telefono = usuarioTexto("Indique su teléfono:");

				Cliente cliente = new Cliente(usuario.getId(), nombre, apellido, dni, email, telefono,pass);
				dbman.addCliente(cliente);
				LOGGER.info("Cliente añadido correctamente");

			} else {
				String puesto = usuarioTexto("Indique su puesto:");
				int sueldo = Integer.parseInt(usuarioTexto("Indique su sueldo:"));
				ArrayList<Aeropuerto> aeropuertos = dbman.getAeropuertos();
				Aeropuerto aeropuerto = seleccionarAeropuerto(aeropuertos);
				Empleado empleado = new Empleado(usuario.getId(), nombre, apellido, email, pass, puesto, sueldo, dni, aeropuerto);
				dbman.addEmpleado(empleado);
				LOGGER.info("Empleado añadido correctamente");
			}
		} catch(NoSuchAlgorithmException e) {
			LOGGER.warning("Error en el registro\n" + e);
		}
	}

	private static void inicioSesion() throws JAXBException {
		// TODO Auto-generated method stub
		System.out.println("INICIO DE SESION");
		String email = usuarioTexto("Indique su email:");
		String pass = usuarioTexto("Indique su contraseña:");
		usuario = userman.checkLogin(email, pass);
		if (usuario == null) {
			System.out.println("Email o contraseña incorrectos");
		} else {
			switch(usuario.getRol().getNombre()) {
				case "cliente" -> menuCliente();
				case "empleado" -> menuEmpleado();
			}
		}
	}
	
	//"Salir", "Listar Vuelos", "Buscar Vuelo","Listar aeropuertos","Buscar aeropuerto","Listar billetes","Comprar billetes"};

	private static void menuCliente() {
		// TODO Auto-generated method stub
		int bucle=-1;
		do {
			System.out.println("\n***** MENU PRINCIPAL USUARIO *****");
			bucle = showmenu(MENU_USUARIO);
			LOGGER.info("USTED HA ELEGIDO " + bucle + "\n");
			switch(bucle) {
			
			case 1-> listarVuelos();
			case 2-> buscarVueloPorId();
			case 3-> consultarInformacionAeropuerto();
			case 4-> buscarAeropuertoCodigo();
			case 5-> listarBilletes();
			case 6-> comprarBillete();
			case 7 -> listarMisBilletes();

			}
		}while(bucle != 0);	}

	private static void listarMisBilletes() {
		// TODO Auto-generated method stub
		Cliente cliente = dbman.getClientePorEmail(usuario.getEmail());
		ArrayList<Billete> billetes = dbman.getBilletesCliente(cliente.getId());
		int size=billetes.size();
		System.out.println(size);
		for(int i = 0; i < size ; i++) {
			System.out.println("\n" + billetes.get(i) + "\n");
		}
	}

	private static void listarBilletes() {
		ArrayList<Billete> billetes = dbman.getBilletes();
		int size=billetes.size();
		for(int i = 0; i < size ; i++) {
			System.out.println("\n" + billetes.get(i) + "\n");
		}
	}


	private static void menuEmpleado() throws JAXBException {
		System.out.println("\nMENU EMPLEADO\n");
		int bucle;
		do {
			bucle = showmenu(MENU_EMPLEADO);
			switch(bucle) {
			case 1 -> vuelos();
			case 2 -> compañias();
			case 3 -> aeropuertos();
			case 4 -> empleados();
			case 5 -> clientes();
			case 6 -> altA.marshalling();
			case 7 -> altA.unMarshalling();

			}
		}while(bucle != 0);
		
	}
	


	private static void comprarBillete() { //introducir el cliente
		//TODO
		ArrayList<Vuelo> vuelos = dbman.getVuelos();
		Vuelo vuelo = seleccionarVuelo(vuelos);
		ArrayList<Billete> billetes = dbman.getBilletesPorVuelo(vuelo.getIdVuelo());
		Billete billete = seleccionarBillete(billetes);
		Cliente cliente = dbman.getClientePorEmail(usuario.getEmail()); //devuelve NULL
		dbman.addBilleteACliente(cliente.getId(), billete.getId());
	}

	private static void clientes() {
		int bucle =-1;
		do {
			bucle = showmenu(MENU_CLIENTES);
			switch(bucle) {
			case 1 -> listarClientes();
			case 2 -> buscarClientePorId();
			case 3 -> eliminarCliente();

			}
		}while(bucle!=0);
	
	}

	private static void eliminarCliente() {
		ArrayList<Cliente> clientes = dbman.getClientes();
		System.out.println("\nSeleccione el id del cliente que desea eliminar: \n");
		Cliente cliente = seleccionarCliente(clientes);
		int result1 = dbman.eliminarCliente(cliente);
		
		if(result1 == 1) {
			System.out.println("\nLa compañia " + cliente.getNombre() + " se ha eliminado\n");
		}else {
			LOGGER.warning("Error al intentar eliminar la compañia " + cliente);
		} 
	}

	private static void buscarClientePorId() {
		try {
			System.out.println("Introduzca el id del cliente: \n");
			String idCliente = br.readLine();
			Cliente cliente = dbman.getClientePorId(idCliente);
			System.out.println(cliente);

		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}	
	}

	private static void listarClientes() {
		ArrayList<Cliente> clientes = dbman.getClientes();
		int size=clientes.size();
		for(int i = 0; i < size ; i++) {
			System.out.println("\n" + clientes.get(i) + "\n");
		}	
	}

	//"Salir", "Listar empleados", "Añadir empleado", "Eliminar empleado", "Buscar Empleado por Id"
	private static void empleados() {
		int bucle =-1;
		do {
			bucle = showmenu(MENU_EMPLEADOS);
			switch(bucle) {
			case 1 -> añadirEmpleado();
			case 2 -> eliminarEmpleado();
			case 3 -> listarEmpleados();
			case 4 -> buscarEmpleadoPorId();

			}
		}while(bucle!=0);
	}


	private static void buscarEmpleadoPorId() {
		try {
			System.out.println("Introduzca el id del empleado: \n");
			String idCodigo = br.readLine();
			Empleado empleado = dbman.getEmpleadoPorId(idCodigo);
			System.out.println(empleado);

		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}		
	}

	private static void listarEmpleados() {
		ArrayList<Empleado> empleados = dbman.getEmpleados();
		int size=empleados.size();
		for(int i = 0; i < size ; i++) {
			System.out.println("\n" + empleados.get(i) + "\n");
		}
	}
	
	private static Empleado seleccionarEmpleado(ArrayList<Empleado> empleados) {
		String[] opciones = new String[empleados.size() + 1];
		opciones[0] = "Cancelar";
		for(int i=0; i<empleados.size();i++) {
			opciones[i+1] = empleados.get(i).getNombre();
		}
		int resultado = showmenu(opciones);
		if(resultado == 0) {
			return null;
		}
		return empleados.get(resultado-1);
	}

	private static Cliente seleccionarCliente(ArrayList<Cliente> clientes) {
		String[] opciones = new String[clientes.size() + 1];
		opciones[0] = "Cancelar";
		for(int i=0; i<clientes.size();i++) {
			opciones[i+1] = clientes.get(i).getNombre();
		}
		int resultado = showmenu(opciones);
		if(resultado == 0) {
			return null;
		}
		return clientes.get(resultado-1);
	}


	private static void añadirEmpleado() {
		try {
		System.out.println("Indique el nombre del empleado:\n");
		String nombre = br.readLine();
		
		System.out.println("Indique el apellido del empleado:\n");
		String apellido = br.readLine();

		System.out.println("Indique el correo del empleado:\n");
		String correo = br.readLine();

		System.out.println("Indique la contraseña del empleado:\n");
		String password = br.readLine();
		
		System.out.println("Indique el puesto del empleado:\n");
		String puesto = br.readLine();

		System.out.println("Indique el sueldo del empleado:\n");
		int sueldo = Integer.parseInt(br.readLine());
		
		System.out.println("Indique el dni del empleado:\n");
		String dni = br.readLine();
		
		System.out.println("Seleccione el id del aeropuerto del empleado:\n");
		ArrayList<Aeropuerto> aeropuertos = dbman.getAeropuertos();
		Aeropuerto aeropuerto = seleccionarAeropuerto(aeropuertos);

		Empleado empleado = new Empleado(0, nombre, apellido, correo, password, puesto, sueldo, dni, aeropuerto);
		
		dbman.addEmpleado(empleado);
		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}
	}
	
	
	private static void vuelos() {
		int bucle =-1;
		do {
			bucle = showmenu(MENU_VUELOS);
			switch(bucle) {
			case 1 -> listarVuelos();
			case 2 -> buscarVueloPorId();
			case 3 -> eliminarVuelo();
			case 4 -> añadirVuelo();
			}
		}while(bucle!=0);
	}
	
	private static void listarVuelos() {
		ArrayList<Vuelo> vuelos = dbman.getVuelos();
		int size=vuelos.size();
		System.out.println(size);
		for(int i = 0; i < size ; i++) {
			System.out.println("\n" + vuelos.get(i) + "\n");
		}
	}
	
	private static void buscarVueloPorId() {
		try {
			System.out.println("Introduzca el id del vuelo: \n");
			int idVuelo = Integer.parseInt(br.readLine());
			Vuelo vuelo = dbman.getVueloPorId(idVuelo);
			System.out.println(vuelo);

		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}		
	}
	
	private static void eliminarVuelo() {
		ArrayList<Vuelo> vuelos = dbman.getVuelos(); 
		System.out.println("\nSeleccione el id del vuelo que desea eliminar: \n");
		Vuelo vuelo = seleccionarVuelo(vuelos);
		int result1 = dbman.eliminarVuelo(vuelo);
		
		if(result1 == 1) {
			System.out.println("\nEl " + vuelo.getIdVuelo() + " se ha eliminado\n");
		}else {
			LOGGER.warning("Error al intentar eliminar el vuelo " + vuelo);
		}
	}
		
	private static Vuelo seleccionarVuelo(ArrayList<Vuelo> vuelos) {
			String[] opciones = new String[vuelos.size() + 1];
			opciones[0] = "Cancelar";
			for(int i=0; i<vuelos.size();i++) {
				opciones[i+1] = "" + vuelos.get(i);
			}
			int resultado = showmenu(opciones);
			if(resultado == 0) {
				return null;
			}
			return vuelos.get(resultado-1);
			
		}
	private static void añadirVuelo() { 
		try {
		System.out.println("Indique la hora del vuelo:\n");
		String hora = br.readLine();
		
		System.out.println("Indique el numero de asientos del vuelo:\n");
		int asientos = Integer.parseInt(br.readLine());
		
		ArrayList<Aeropuerto> aeropuertos = dbman.getAeropuertos();
		
		//TODO SALEN LAS DOS SELECCIONAR AEROPUERTOS A LA VEZ
		
		System.out.println("\nIndique el origen del vuelo:\n");
		Aeropuerto origen= seleccionarAeropuerto(aeropuertos);		
		System.out.println("\nIndique el destino del vuelo:\n");
		Aeropuerto destino = seleccionarAeropuerto(aeropuertos);


		ArrayList<Compañia> compañias = dbman.getCompañias();
		Compañia compañia = seleccionarCompañia(compañias);

		//TODO
		Vuelo vuelo = new Vuelo(0,hora, asientos, origen, destino, compañia);		
		dbman.addVuelo(vuelo);
		dbman.addVuelo_Compañia(vuelo);  
		for(int i=0;i<asientos;i++) {
			dbman.addBilleteDefault(defaultV.generarBilleteVuelo(vuelo));
		}
		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}
	}
	
	public static int getIdEleccionVuelo(ArrayList<Aeropuerto> aeropuertos) {
		String[] opciones = new String[aeropuertos.size() + 1];
		opciones[0] = "Cancelar";
		for(int i=0; i<aeropuertos.size();i++) {
			opciones[i+1] = aeropuertos.get(i).getNombre();
		}
		int resultado = showmenu(opciones);
		if(resultado == 0) {
			return 0;
		}
		Aeropuerto obj=aeropuertos.get(resultado-1);
		int dev=obj.getId();
		return dev;
	}

    public static void metodoConRetardo() {
        try {
            // Pausa durante 3 segundos (3000 milisegundos)
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Código que se ejecuta después de la pausa
        System.out.println("Método reanudado después del retardo");
    }
	private static void compañias() {
		int bucle =-1;
		do {
			bucle = showmenu(MENU_COMPAÑIAS);
			switch(bucle) {
			case 1 -> listarCompañias();
			case 2 -> añadirCompañia();
			case 3 -> buscarCompañiaPorNombre();
			case 4 -> eliminarCompañia();
			case 5 -> listarVuelosCompañia();
			}
		}while(bucle!=0);
	}
	
	
	private static Billete seleccionarBillete(ArrayList<Billete> billetes) {
		String[] opciones = new String[billetes.size() + 1];
		opciones[0] = "Cancelar";
		for(int i=0; i<billetes.size();i++) {
			opciones[i+1] = billetes.get(i).toString();
		}
		int resultado = showmenu(opciones);
		if(resultado == 0) {
			return null;
		}
		return billetes.get(resultado-1);
		
	}
	
	private static void listarVuelosCompañia() {
		// TODO Auto-generated method stub
		ArrayList<Compañia>	compañias = dbman.getCompañias();
		Compañia compañia = seleccionarCompañia(compañias);
		ArrayList<Vuelo> vuelosCompañia = dbman.getVuelosPorCompañia(compañia.getId());
		int size=vuelosCompañia.size();
		for(int i = 0; i < size ; i++) {
			System.out.println("\n" + vuelosCompañia.get(i) + "\n");
		}
		
	}

	private static void eliminarCompañia() {
		ArrayList<Compañia> compañias = dbman.getCompañias();
		System.out.println("\nSeleccione el id de la compañia que desea eliminar: \n");
		Compañia compañia = seleccionarCompañia(compañias);
		int result1 = dbman.eliminarCompañia(compañia);
		
		if(result1 == 1) {
			System.out.println("\nLa compañia " + compañia.getNombre() + " se ha eliminado\n");
		}else {
			LOGGER.warning("Error al intentar eliminar la compañia " + compañia);
		}	
	}
	
	private static void eliminarEmpleado() {
		ArrayList<Empleado> empleados = dbman.getEmpleados();
		System.out.println("\nSeleccione el id del aeropuerto que desea eliminar: \n");
		Empleado empleado = seleccionarEmpleado(empleados);
		int result1 = dbman.eliminarEmpleado(empleado);
		
		if(result1 == 1) {
			System.out.println("\nLa compañia " + empleado.getNombre() + " se ha eliminado\n");
		}else {
			LOGGER.warning("Error al intentar eliminar la compañia " + empleado);
		} 

	}
	
	private static Compañia seleccionarCompañia(ArrayList<Compañia> compañias) {
		String[] opciones = new String[compañias.size() + 1];
		opciones[0] = "Cancelar";
		for(int i=0; i<compañias.size();i++) {
			opciones[i+1] = compañias.get(i).getNombre();
		}
		int resultado = showmenu(opciones);
		if(resultado == 0) {
			return null;
		}
		return compañias.get(resultado-1);
	}


	
	private static Aeropuerto seleccionarAeropuerto(ArrayList<Aeropuerto> aeropuertos) {
		String[] opciones = new String[aeropuertos.size() + 1];
		opciones[0] = "Cancelar";
		for(int i=0; i<aeropuertos.size();i++) {
			opciones[i+1] = aeropuertos.get(i).getNombre();
		}
		int resultado = showmenu(opciones);
		if(resultado == 0) {
			return null;
		}
		return aeropuertos.get(resultado-1);
		
	}

	private static void buscarCompañiaPorNombre() {
		try {
			System.out.println("Introduzca el nombre de la compañia: \n");
			String idNombre = br.readLine().toUpperCase();
			Compañia compañia = dbman.getCompañiaPorNombre(idNombre);
			System.out.println(compañia);

		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}
	}

	private static void listarCompañias() {
		ArrayList<Compañia> compañias = dbman.getCompañias();
		int size=compañias.size();
		for(int i = 0; i < size ; i++) {
			System.out.println("\n" + compañias.get(i) + "\n");
		}
	}

	private static void añadirCompañia() {
		try {
		System.out.println("Indique el nombre de la compañia:\n");
		String nombre = br.readLine().toUpperCase();
		
		System.out.println("Indique la pagina web de la compañia:\n");
		String paginaWeb = br.readLine();
		
		System.out.println("Indique el pais de la compañia:\n");
		String pais = br.readLine();
		
		System.out.println("Indique el numero de telefono de la compañia:\n");
		String numTelefono = br.readLine();
		
		System.out.println("Indique el correo de contacto de la compañia:\n");
		String email = br.readLine();
		
		Compañia compañia = new Compañia(0, email, numTelefono, nombre, paginaWeb, pais);
		dbman.addCompañia(compañia);

		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}
	}

	private static void aeropuertos() {
		int bucle =-1;
		do {
			bucle = showmenu(MENU_AEROPUERTO);
			switch(bucle) {
			case 1 -> consultarInformacionAeropuerto();
			case 2 -> añadirAeropuerto();
			case 3 -> buscarAeropuertoCodigo();
			case 4 -> eliminarAeropuerto();

			}
		}while(bucle!=0);
	}
	
	private static void eliminarAeropuerto() {
		ArrayList<Aeropuerto> aeropuertos = dbman.getAeropuertos(); 
		System.out.println("\nSeleccione el id del aeropuerto que desea eliminar: \n");
		Aeropuerto aeropuerto = seleccionarAeropuerto(aeropuertos);
		int result1 = dbman.eliminarAeropuerto(aeropuerto);
		
		if(result1 == 1) {
			System.out.println("\nEl " + aeropuerto.getNombre() + " se ha eliminado\n");
		}else {
			LOGGER.warning("Error al intentar eliminar el aeropuerto " + aeropuerto);
		}	
		
	}

	private static void consultarInformacionAeropuerto() {
		ArrayList<Aeropuerto> aeropuertos = dbman.getAeropuertos();
		int size = aeropuertos.size();
		System.out.println(size);
		for (int i = 0; i < size; i++) {
			System.out.println("\n" + aeropuertos.get(i) + "\n");
		}
	}

	private static void buscarAeropuertoCodigo() {
		try {
			System.out.println("Introduzca el codigo del aeropuerto: \n");
			String idCodigo = br.readLine().toUpperCase();
			Aeropuerto aeropuerto = dbman.getAeropuertoPorCodigo(idCodigo);
			System.out.println(aeropuerto);

		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}
	}

	private static void añadirAeropuerto() {
		try {
		System.out.println("Indique el nombre del aeropuerto:\n");
		String nombre = br.readLine();
		
		System.out.println("Indique el codigo del aeropuerto:\n");
		String codigo = br.readLine().toUpperCase();
		
		Aeropuerto aeropuerto = new Aeropuerto(0, nombre, codigo, null);
		dbman.addAeropuerto(aeropuerto);
		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}
	}


	private static String usuarioTexto(String string) {
		System.out.println(string);
		String resultado = "";
		try {
			resultado = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultado;
	}



	private static void registroCliente(){
		try {
		System.out.println("Indique su nombre:\n");
		String nombre = br.readLine();
		
		System.out.println("Indique su apellido:\n");
		String apellido = br.readLine();
		
		System.out.println("Indique su dni:\n");
		String dni = br.readLine();
		
		System.out.println("Indique su correo:\n");
		String correo = br.readLine();

		System.out.println("Indique su contraseña:\n");
		String password = br.readLine(); //poner metodo para ocultar la contraseña

		System.out.println("Indique su telefono:\n");
		String telefono = br.readLine();
		
		Cliente cliente = new Cliente(0,nombre, apellido, dni, correo, password, telefono);
		dbman.addCliente(cliente);
		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}
	}
	


	private static int showmenu(String[] opciones) {
		int bucle=-1;
		
		do {
			
			System.out.println("\nELIJA UNA OPCION:\n");
			
			for(int i=1; i<opciones.length;i++) {
				System.out.println(i + "." + opciones[i]);
			}
			
			System.out.println("0." + opciones[0]);
			
			try {
				bucle = Integer.parseInt(br.readLine());
			}catch(IOException e) {
			}catch(NumberFormatException e){
			
				e.printStackTrace();
			}
			
		}while(bucle >= opciones.length || bucle < 0);
		
		return bucle;
	}

	

}
