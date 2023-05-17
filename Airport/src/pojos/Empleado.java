package pojos;

public class Empleado {

	private int id;
	private String nombre;
	private String apellido;
	private String correo;
	private String password;
	private String puesto;
	private int sueldo;
	private String dni;
	private Aeropuerto aeropuerto;

	
	public Empleado() {
		super();

	}


	public Empleado(int id,String nombre, String apellido, String correo, String password, String puesto, int sueldo,
			String dni, Aeropuerto aeropuerto) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.correo = correo;
		this.password = password;
		this.puesto = puesto;
		this.sueldo = sueldo;
		this.dni = dni;
		this.aeropuerto = aeropuerto;
	}


	public String getNombre() {
		return nombre;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public String getPuesto() {
		return puesto;
	}


	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}


	public int getSueldo() {
		return sueldo;
	}


	public void setSueldo(int sueldo) {
		this.sueldo = sueldo;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public Aeropuerto getAeropuerto() {
		return aeropuerto;
	}


	public void setAeropuerto(Aeropuerto aeropuerto) {
		this.aeropuerto = aeropuerto;
	}


	@Override
	public String toString() {
		return "Empleado [id=" + id + " " +
				nombre + apellido + "\n"+ 
				" Correo: " + correo + "\n"
				+ " Password: " + password + "\n" + 
				"  Puesto: " + puesto + "\n"+ 
				" Sueldo: " + sueldo + "\n" + 
				" Dni: " + dni + "\n" ;
				//+ aeropuerto.getNombre() + "]";
	}





	
	
}
