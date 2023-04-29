package defaultValues;

import pojos.Aeropuerto;
import pojos.Cliente;
import pojos.Empleado;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

public class DefaultValues {
	private final static Logger TERM = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private String[] nombres;
	private String[] apellidos;
	private int id = 0;
	
	private final String F_NOMBRES = "./db/nombres.csv";
	private final String F_APELLIDOS = "./db/apellidos.csv";
	
	//PROGRAMAR MAS TARDE COMO UN ENUM!!!! PARA ASOCIAR LOS ASIENTOS PARA CADA CATEGORIA
	private final static String [] CATEGORIA = {"Business Class", "Tourist Class"};
	//PROGRAMAR MAS TARDE COMO UN ENUM, PARA ASOCIAR EL ID DE VUELOS (IB1292)
	private final static String [] PUESTOEMPLEADO = {"Tecnico administrativo", "Auxiliar de vuelo", "Controlador aero", "Piloto", "Operario de logistica"};
	private final static String [] AEROPUERTOS = {"Aeropuerto Adolfo Suárez Madrid-Barajas", "Aeropuerto Josep Tarradellas Barcelona-El Prat", "Aeropuerto de Málaga-Costa del Sol"};
	private final static String [] CORREOS = {"@gmail.com", "@hotmail.com", "@usp.ceu.es", "@yahoo.es", "@outlook.com"};
	
	
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
		cliente.setCorreo(getCorreo(nombre, apellido) + id);
		cliente.setNumtelf(generarNumeroTelefono());
		cliente.setDni(generarDNI());
		cliente.setPassword(generarContrasena(10)); //contraseña de 10 caracteres por defecto
		id++;
		
		return cliente;
		
	}

	
	//generarVuelosAleatorios
	/*
	public static Empleado generarEmpleado() {
		Aeropuerto aeropuerto = new Aeropuerto();
		String nombreAeropuerto = randomString(AEROPUERTOS);
		aeropuerto.setNombre(nombreAeropuerto);
		aeropuerto.setId(nombreAeropuerto);
		aeropuerto.setCiudad(nombreAeropuerto);
				
		Empleado empleado = new Empleado();
		empleado.setPuesto(randomString(PUESTOEMPLEADO));
		empleado.setAeropuerto(aeropuerto);
		
		return empleado;
	}

	*/
	
	public static String getCorreo(String nombre, String apellido) {
		String nombreCorreo=nombre.replace(" ", ".");
		String apellidoCorreo=apellido.replace(" ", ".");
		String correo= nombreCorreo+apellidoCorreo + " @gmail.com"; //CAMBIAR @GMAIL.COM A ARRAY
		
		
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
	    Random rand = new Random();
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
