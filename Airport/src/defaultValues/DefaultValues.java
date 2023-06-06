package defaultValues;

import pojos.Aeropuerto;
import pojos.Billete;
import pojos.Cliente;
import pojos.Compañia;
import pojos.Empleado;
import pojos.Vuelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;

public class DefaultValues {
	private final static Logger TERM = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private String[] nombres;
	private String[] apellidos;
	private int id = 0;
	
	private final String F_NOMBRES = "./db/nombres.csv";
	private final String F_APELLIDOS = "./db/apellidos.csv";
	
	private final static String [] CATEGORIA = {"First class","Business Class", "Tourist Class"};
	private final static String [] PUESTOEMPLEADO = {"Tecnico administrativo", "Auxiliar de vuelo", "Controlador aero", "Piloto", "Operario de logistica"};
	private final static String [] CORREOS = {"@gmail.com", "@hotmail.com", "@usp.ceu.es", "@yahoo.es", "@outlook.com"};
	
	private static DBManager dbman = new JDBCManager();
	private static Random rand = new Random();


	
	
	public DefaultValues() {
		nombres = readFile(F_NOMBRES);
		apellidos = readFile(F_APELLIDOS);
	}
	
	private String[] readFile(String fileName) {
		String fileContent = "";
		File file = new File(fileName);
		try (Scanner scanner = new Scanner(file)){
			while (scanner.hasNext()) {
				fileContent += scanner.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] lineas = fileContent.split("\\r?\\n");
		TERM.info("Lineas leidas:  " + lineas.length + " lineas del fichero " + fileName);
		return lineas;
	}
	public Cliente generarCliente() {
		Cliente cliente = new Cliente();
		String nombre = "" + nombres[randomInt(nombres.length)];
		cliente.setNombre(nombre);
		String apellido = "" + apellidos[randomInt(apellidos.length)]+ " " + apellidos[randomInt(apellidos.length)];
		cliente.setApellido(apellido);
		cliente.setCorreo(getCorreo(nombre, apellido));
		cliente.setNumtelf(generarNumeroTelefono());
		cliente.setDni(generarDNI());
		cliente.setPassword(generarContrasena(10)); //contraseña de 10 caracteres por defecto
		id++;
		
		return cliente;
		
	}
	
	public ArrayList<Aeropuerto> listaAeropuertos(){
		ArrayList<Aeropuerto> listadoDeAeropuertos= dbman.getAeropuertos();
		return listadoDeAeropuertos;
	}
	
	public Aeropuerto aeropuertoAleatorio(ArrayList<Aeropuerto>listadoAeropuertos) {
		int tamano = listadoAeropuertos.size();
		Aeropuerto aeropuerto = new Aeropuerto();
		int numeroAleatorio = rand.nextInt(tamano);
		return listadoAeropuertos.get(numeroAleatorio);

	}
	
	public Billete generarBilleteVuelo(Vuelo vuelo) {
		Billete billete = new Billete();
		String categoria = randomString(CATEGORIA);
		billete.setCategoria(categoria);
		int precio = randomInt(500);
		billete.setPrecio(precio);
		int numeroReserva=randomInt(100000);
		billete.setNumReserva(numeroReserva);
		int numAsiento = randomInt(vuelo.getAsientos());
		billete.setNumAsiento(numAsiento);
		boolean pagado = false;
		billete.setPagado(pagado);
		billete.setVuelo(vuelo);
		return billete;
		
	}
	
	public Empleado generarEmpleado() {
		Empleado empleado = new Empleado();		 
		String nombre = "" + nombres[randomInt(nombres.length)];
		empleado.setNombre(nombre);
		String apellido = "" + apellidos[randomInt(apellidos.length)]+ " " + apellidos[randomInt(apellidos.length)];
		empleado.setApellido(apellido);
		empleado.setCorreo(getCorreo(nombre, apellido) + id);
		id++;
		empleado.setDni(generarDNI());

		empleado.setPassword(generarContrasena(10)); //contraseña de 10 caracteres por defecto
		empleado.setPuesto(randomString(PUESTOEMPLEADO));
		empleado.setSueldo(rand.nextInt(2001) + 1000);
		empleado.setAeropuerto(aeropuertoAleatorio(listaAeropuertos()));
		return empleado;
		
	}
	
	public Vuelo generarVuelo() {
        Vuelo vuelo = new Vuelo();
        int hora = rand.nextInt(24);  // Genera un número aleatorio entre 0 y 23
        int minutos = rand.nextInt(60);  // Genera un número aleatorio entre 0 y 59
        String tiempo = hora + ":" + minutos;
        vuelo.setHora(tiempo);
        int asiento = rand.nextInt(20);
        vuelo.setAsientos(asiento);
        ArrayList<Aeropuerto> aeropuertos = dbman.getAeropuertos();
        Aeropuerto origen = obtenerAeropuertoAleatorio(aeropuertos);
        vuelo.setOrigen(origen);
        Aeropuerto destino = obtenerAeropuertoAleatorio(aeropuertos);
        vuelo.setDestino(destino);
        ArrayList<Compañia> compañias = dbman.getCompañias();
        Compañia compañia = obtenerCompañiaAleatorio(compañias);
        vuelo.setCompañia(compañia);
        
        return vuelo;        
        
	}
	
	public Aeropuerto obtenerAeropuertoAleatorio(ArrayList<Aeropuerto> aeropuertos) {
        int randomIndex = rand.nextInt(aeropuertos.size());
        return aeropuertos.get(randomIndex);
    }
	
	public Compañia obtenerCompañiaAleatorio(ArrayList<Compañia> compañias) {
        int randomIndex = rand.nextInt(compañias.size());
        return compañias.get(randomIndex);
    }
	
	//generarVuelosAleatorios

	
	public static String getCorreo(String nombre, String apellido) {
		String nombreCorreo=nombre.replace(" ", ".");
		String apellidoCorreo=apellido.replace(" ", ".");
		String correo= nombreCorreo+apellidoCorreo + "@gmail.com"; //CAMBIAR @GMAIL.COM A ARRAY
		
		
		return correo;
	}
	  public static String generarContrasena(int longitud) {
		    Random rand = new Random();
		    String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>/?";
		    StringBuilder contraseña = new StringBuilder();
		    
		    for (int i = 0; i < longitud; i++) {
		      int index = rand.nextInt(caracteres.length());
		      contraseña.append(caracteres.charAt(index));
		    }
		    
		    return contraseña.toString();
		  }


	private static String generarDNI() {
	    String dni = "";
	    for (int i = 0; i < 8; i++) {
	      int digito = rand.nextInt(10);
	      dni += Integer.toString(digito);
	    }
	    String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
	    int resto = Integer.parseInt(dni) % 23;
	    String letra = letras[resto];
	    return dni + letra;

	}
	
	private static String generarNumeroTelefono() {
	    Random rand = new Random();
		String telefono = "";
	    for (int i = 0; i < 10; i++) {
	        int digito = rand.nextInt(10);
	        telefono += Integer.toString(digito);
	      }
	    return telefono;
	}

	private static String randomString(String[] array) {
		return array[randomInt(array.length)];
	}


	private static int randomInt(int max) {
		return (int) (Math.random() * max);
	}
}
