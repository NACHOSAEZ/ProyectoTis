package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import logging.MyLogger;
import pojos.Aeropuerto;
import pojos.Cliente;
import pojos.Empleado;


public class Menu {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	
	private static final String[] MENU_START = {"Salir", "Registrarse", "Iniciar Sesion"};
	private static final String[] MENU_ROL = {"Salir", "Empleado", "Cliente"};
	private static final String[] MENU_EMPLEADO = {"Salir", "Consultar informacion aeropuerto", "Listar vuelo", "Listar compañias", "Consular vuelo por codigo", "Añadir vuelo",
			"Retrasar vuelo", "Añadir compañia", "Listar empleado", "Añadir empleado"};


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
			case 1 -> consultarInformacionAeropuerto();
			case 2 -> listarVuelos();
			case 3 -> listarCompañias();
			
			}
		}while(bucle != 0);
		
	}

	private static String listarCompañias() {
		// TODO Auto-generated method stub
		return null;
	}

	private static String listarVuelos() {
		// TODO Auto-generated method stub
		return null;
	}

	private static String consultarInformacionAeropuerto() {
		// TODO Auto-generated method stub
		return null;
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
			
			System.out.println("ELIGA UNA OPCION:\n");
			
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
