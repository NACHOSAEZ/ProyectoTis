package pojos;

public class Cliente {
	
	private String nombre;
	private String apellido;
	private String dni;
	private String correo;
	private String password;
	private String numtelf;
	
	
	public Cliente() {
		super();
	}


	public Cliente(String nombre, String apellido, String dni, String correo, String password, String numtelf) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.correo = correo;
		this.password = password;
		this.numtelf = numtelf;
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


	public void setPassword(String contraseña) {
		this.password = contraseña;
	}


	public String getNumtelf() {
		return numtelf;
	}


	public void setNumtelf(String string) {
		this.numtelf = string;
	}


	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", correo=" + correo
				+ ", contraseña=" + password + ", numtelf=" + numtelf + "]";
	}
	
	
	

}
