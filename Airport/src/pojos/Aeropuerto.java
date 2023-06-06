package pojos;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Aeropuerto")
@XmlAccessorType(XmlAccessType.FIELD)


public class Aeropuerto implements Serializable{

	private static final long serialVersionUID = -3871659483164277914L;
	
	@XmlElement
	private int id;
	@XmlElement
	private String nombre;
	@XmlElement
	private String codigo;
	@XmlElement(name = "Empleados")
	@XmlElementWrapper(name = "Empleados")
	private ArrayList<Empleado> empleados;
	
	public Aeropuerto() {
		super();
		empleados = new ArrayList<>();

	}
	
	

	public Aeropuerto(int id, String nombre, String codigo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
	}



	public Aeropuerto(int id, String nombre, String codigo, ArrayList<Empleado> empleados) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
		this.empleados = empleados;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ArrayList<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(ArrayList<Empleado> empleados) {
		this.empleados = empleados;
	}

	@Override
	public String toString() {
		return "\n"+id + "." + nombre + "(" + codigo + ")"+ "\n"+ empleados + "\n";
	}
	

}
