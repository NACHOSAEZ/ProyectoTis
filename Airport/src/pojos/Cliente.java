package pojos;

import java.util.ArrayList;

public class Cliente {
	
	private int id;
	private String nombre;
	private String apellido;
	private String dni;
	private String correo;
	private String numtelf;
	private String password;
	private ArrayList<Billete> billetes;

	
	
	public Cliente() {
		super();
		billetes = new ArrayList<>();
	}



	public Cliente(int id, String nombre, String apellido, String dni, String correo, String numtelf, String password) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.correo = correo;
		this.numtelf = numtelf;
		this.password = password;
	}



	public Cliente(int id, String nombre, String apellido, String dni, String correo, String numtelf, String password,
			ArrayList<Billete> billetes) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.correo = correo;
		this.numtelf = numtelf;
		this.password = password;
		this.billetes = billetes;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getApellido() {
		return apellido;
	}



	public void setApellido(String apellido) {
		this.apellido = apellido;
	}



	public String getDni() {
		return dni;
	}



	public void setDni(String dni) {
		this.dni = dni;
	}



	public String getCorreo() {
		return correo;
	}



	public void setCorreo(String correo) {
		this.correo = correo;
	}



	public String getNumtelf() {
		return numtelf;
	}



	public void setNumtelf(String numtelf) {
		this.numtelf = numtelf;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public ArrayList<Billete> getBilletes() {
		return billetes;
	}



	public void setBilletes(ArrayList<Billete> billetes) {
		this.billetes = billetes;
	}



	@Override
	public String toString() {
		return id + ". Cliente [" + nombre + " " + apellido +
				"\nDNI = " + dni +
				"\nCorreo=" + correo + 
				"\nNumero de telefono: " + numtelf + 
				"\nPassword=" + password + 
				"\nBilletes: " + billetes + "]";
	}


	
	
}
