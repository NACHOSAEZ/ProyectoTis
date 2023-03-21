package pojos;

public class Empleado {

	
	private int id;
	private String tipo;
	private Aeropuerto aeropuerto;
	
	
	public Empleado() {
		super();

	}

	public Empleado(int id, String tipo, Aeropuerto aeropuerto) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.aeropuerto = aeropuerto;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Aeropuerto getAeropuerto() {
		return aeropuerto;
	}


	public void setAeropuerto(Aeropuerto aeropuerto) {
		this.aeropuerto = aeropuerto;
	}

	
	@Override
	public String toString() {
		return "Empleado [id=" + id + ", tipo=" + tipo + ", aeropuerto=" + aeropuerto + "]";
	}







	
}
