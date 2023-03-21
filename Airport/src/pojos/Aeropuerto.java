package pojos;

public class Aeropuerto {

	
	private int id;
	private String nombre;
	private String ciudad;
	
	public Aeropuerto() {
		super();

	}
	
	
	public Aeropuerto(int id, String nombre, String ciudad, String pais) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ciudad = ciudad;
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

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	@Override
	public String toString() {
		return "Aeropuerto [id=" + id + ", nombre=" + nombre + ", ciudad=" + ciudad  + "]";
	}
	
	
}
