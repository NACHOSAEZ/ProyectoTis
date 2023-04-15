package pojos;

public class Cliente {
	
	private int id;
	private String nombre;
	private String apellido;
	private String dni;
	private String correo;
	private String numtelf;
	private String password;

	
	
	public Cliente() {
		super();
	}


	public Cliente(int id, String nombre, String apellido, String dni, String correo, String numtelf, String password) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.correo = correo;
		this.password = password;
		this.numtelf = numtelf;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNumtelf() {
		return numtelf;
	}


	public void setNumtelf(String numtelf) {
		this.numtelf = numtelf;
	}


	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", correo="
				+ correo + ", password=" + password + ", numtelf=" + numtelf + "]";
	}


	
}
