package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import logging.MyLogger;
import pojos.Aeropuerto;
import pojos.Cliente;
import pojos.Compañia;
import pojos.Empleado;


public class Menu {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	
	private static final String[] MENU_START = {"Salir", "Registrarse", "Iniciar Sesion"};
	private static final String[] MENU_ROL = {"Salir", "Empleado", "Cliente", "menuEmpleado"};
	private static final String[] MENU_EMPLEADO = {"Salir", "Aeropuertos", "Compañias", "Consular vuelo por codigo", "Añadir vuelo",
			"Retrasar vuelo", "Añadir compañia", "Listar empleado", "Añadir empleado"};
	private static final String[] MENU_AEROPUERTO = {"Salir", "Listar aeropuertos", "Añadir aeropuerto", "Consultar aeropuerto por codigo", "Eliminar aeropuerto"};
	private static final String[] MENU_COMPAÑIAS = {"Salir", "Listar compañias", "Añadir compañia", "Consultar compañia por nombre", "Eliminar compañia"};




	public static void main (String[] args) throws IOException{
		MyLogger.setupFromFile();
		dbman.connect();
		
		System.out.println("\n *** BIENVENIDO A LA BASE DE DATOS DE LOS AEROPUERTOS DE ESPAÑA *** \n");
		int bucle=-1;
		do {
			bucle = showmenu(MENU_START);
			LOGGER.info("USTED HA ELEGIDO " + bucle + "\n");

			switch(bucle) {
			
			case 1 : registrarse();
			case 2 : inicioSesion();
			}
		}while(bucle != 0);
		
		System.out.println("\n *** GRACIAS POR USAR NUESTRA BASE DE DATOS ***");
		dbman.disconnect();
	}
	
	private static void inicioSesion() {
		// TODO Auto-generated method stub
		
	}

	private static void registrarse() {

		int bucle=-1;
		do {
			System.out.println("Seleccione uno de los siguientes roles para registrarse\n");
			bucle = showmenu(MENU_ROL);
			LOGGER.info("USTED HA ELEGIDO " + bucle + "\n");

			switch(bucle) {
			
			case 1: registroEmpleado();
			case 2: registroCliente();
			case 3: menuEmpleado();
			}
		}while(bucle != 0);
	}

	private static void menuEmpleado() {
		// TODO Auto-generated method stub
		System.out.println("\nMENU EMPLEADO\n");
		int bucle;
		do {
			bucle = showmenu(MENU_EMPLEADO);
			switch(bucle) {
			case 1 -> aeropuertos();
			case 2 -> compañias();
			}
		}while(bucle != 0);
		
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
			}
		}while(bucle!=0);
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

	/*
	 * 
*/
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
			String idNombre = br.readLine();
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
		String nombre = br.readLine();
		
		System.out.println("Indique la pagina web de la compañia:\n");
		String paginaWeb = br.readLine();
		
		System.out.println("Indique el pais de la compañia:\n");
		String pais = br.readLine();
		
		System.out.println("Indique el numero de telefono de la compañia:\n");
		String numTelefono = br.readLine();
		
		System.out.println("Indique el correo de contacto de la compañia:\n");
		String email = br.readLine();
		
		Compañia compañia = new Compañia(0, nombre, paginaWeb, pais, numTelefono, email);
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
		ArrayList<Aeropuerto> pedidos = dbman.getAeropuertos();
		int size = pedidos.size();
		for (int i = 0; i < size; i++) {
			System.out.println("\n" + pedidos.get(i) + "\n");
		}
	}

	private static void buscarAeropuertoCodigo() {
		try {
			System.out.println("Introduzca el codigo del aeropuerto: \n");
			String idCodigo = br.readLine();
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
		String codigo = br.readLine();
		
		Aeropuerto aeropuerto = new Aeropuerto(0, nombre, codigo);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}




	private static void registroEmpleado() {
		// TODO Auto-generated method stub
		
	}
/*
	private static void registroEmpleado(){
		try {
		System.out.println("Indique su nombre:\n");
		String nombre = br.readLine();
		
		System.out.println("Indique su apellido:\n");
		String apellido = br.readLine();
		
		System.out.println("Indique su correo:\n");
		String correo = br.readLine();

		System.out.println("Indique su contraseña:\n");
		String password = br.readLine(); //poner metodo para ocultar la contraseña

		
		System.out.println("Indique su sueldo:\n");
		int sueldo = Integer.parseInt(br.readLine());
		
		System.out.println("Indique su aeropuerto:\n");
		String nombreAeropuerto = br.readLine();
		String ciudad = br.readLine();
		String pais = br.readLine();

		//mirar como añadir aeropuerto con sus id
		//metodo elegir aeropuerto
		Aeropuerto aeropuerto = new Aeropuerto(nombreAeropuerto, ciudad, pais);
		
		String puesto = br.readLine();
		
		Empleado empleado = new Empleado(nombre, apellido, correo, password, puesto, sueldo, aeropuerto);

		//añadir empleado al dbmanager
		}catch(IOException e) {
			LOGGER.warning("ERROR" + e);
		}



	}
	
	*/
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
			
			System.out.println("\nELIGA UNA OPCION:\n");
			
			for(int i=1; i<opciones.length;i++) {
				System.out.println(i + "." + opciones[i]);
			}
			
			System.out.println("0." + opciones[0]);
			
			try {
				bucle = Integer.parseInt(br.readLine());
			}catch(IOException e) {
			}catch(NumberFormatException e){
			}
			
		}while(bucle >= opciones.length || bucle < 0);
		
		return bucle;
	}

	

}
