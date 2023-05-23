package pojos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Empleado")
@XmlAccessorType(XmlAccessType.FIELD)

public class Empleado implements Serializable{
	private static final long serialVersionUID = -1324200261531760390L;
	
	@XmlElement
	private int id;
	@XmlElement
	private String nombre;
	@XmlElement
	private String apellido;
	@XmlElement
	private String correo;
	@XmlElement
	private String password;
	@XmlElement
	private String puesto;
	@XmlElement
	private int sueldo;
	@XmlElement
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
