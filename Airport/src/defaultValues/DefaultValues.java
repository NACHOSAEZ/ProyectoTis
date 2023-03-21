package defaultValues;

import pojos.Cliente;
import java.util.*;

public class DefaultValues {

	
	private final static String [] NOMBRES = {"Nacho","Cristina", "Francisco", "Mercedes", "Luis", "Maria", "Isabel", "Javier", "Jesus", "Laura"};
	private final static String [] APELLIDOS = {"Perez", "Martin", "Martinez", "Lopez", "Calero", "Almenta", "Hernando", "Garcia", "Saenz", "Vega"};
	//PROGRAMAR MAS TARDE COMO UN ENUM!!!! PARA ASOCIAR LOS ASIENTOS PARA CADA CATEGORIA
	private final static String [] CATEGORIA = {"Business Class", "Tourist Class"};
	//PROGRAMAR MAS TARDE COMO UN ENUM, PARA ASOCIAR EL ID DE VUELOS (IB1292)
	private final static String [] PUESTOEMPLEADO = {"Tecnico administrativo", "Auxiliar de vuelo", "Controlador aero", "Piloto", "Operario de logistica"};
	private final static String [] CORREOS = {"@gmail.com", "@hotmail.com", "@usp.ceu.es", "@yahoo.es", "@outlook.com"};
	
	public static Cliente generarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNombre(randomString(NOMBRES));
		cliente.setApellido(randomString(APELLIDOS));
		cliente.setCorreo(randomString(NOMBRES)+ randomString(APELLIDOS) + randomString(CORREOS));
		cliente.setNumtelf(generarNumeroTelefono());
		cliente.setDni(generarDNI());
		cliente.setContraseña(generarContrasena(10)); //contraseña de 10 caracteres por defecto
		
		return cliente;
		
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
