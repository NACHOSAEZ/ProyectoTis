package pojos;

import java.util.ArrayList;

public class Aeropuerto {

	
	private int id;
	private String nombre;
	private String codigo;
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
		return id + "." + nombre + "(" + codigo + ")"+ "\n"+ empleados;
	}
	

}
