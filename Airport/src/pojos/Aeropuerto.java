package pojos;

public class Aeropuerto {

	
	private String id;
	private String nombre;
	private String codigo;
	
	public Aeropuerto() {
		super();

	}

	public Aeropuerto(String id, String nombre, String codigo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codigo = codigo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	@Override
	public String toString() {
		return "Aeropuerto [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + "]";
	}
	
	
	
	
}
