package pojos;

public class Aeropuerto {

	
	private String id;
	private String nombre;
	private String ciudad;
	
	public Aeropuerto() {
		super();

	}
	
	
	public Aeropuerto(String id, String nombre, String ciudad) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;

	}


	public String getId() {
		return id;
	}
//metodo para seleccionar el id del aeropuerto (MAD,AGP, BCN)
	public void setId(String id) {
		this.id=id;
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
		if(id == "Aeropuerto Josep Tarradellas Barcelona-El Prat") {
			this.id = "BCN";
		}
		if(id == "Aeropuerto de Málaga-Costa del Sol") {
			this.id = "Malaga";
		}
		if(id == "Aeropuerto Adolfo Suárez Madrid-Barajas") {
			this.id = "MAD";
		}	}


	@Override
	public String toString() {
		return "Aeropuerto [id=" + id + ", nombre=" + nombre + ", ciudad=" + ciudad  + "]";
	}
	
	
}
