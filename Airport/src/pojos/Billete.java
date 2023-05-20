package pojos;

public class Billete {
	
	private int id;
	private String Categoria;
	private int precio;
	private int numReserva;
	private int numAsiento;
	private boolean pagado;
	private Cliente cliente;
	private Vuelo vuelo;
	
	public Billete() {
		super();
	}
	

	public Billete(int id, String categoria, int precio, int numReserva, int numAsiento, boolean pagado,
			Cliente cliente, Vuelo vuelo) {
		super();
		this.id = id;
		Categoria = categoria;
		this.precio = precio;
		this.numReserva = numReserva;
		this.numAsiento = numAsiento;
		this.pagado = pagado;
		this.cliente = cliente;
		this.vuelo = vuelo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getNumReserva() {
		return numReserva;
	}

	public void setNumReserva(int numReserva) {
		this.numReserva = numReserva;
	}

	public int getNumAsiento() {
		return numAsiento;
	}

	public void setNumAsiento(int numAsiento) {
		this.numAsiento = numAsiento;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}

	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}

	@Override
	public String toString() {
		return id + "Billete " + 
				"\nCategoria=" + Categoria +
				"\nPrecio=" + precio + 
				"\nNumero de Reserva: " + numReserva
				+ "\nNumero de Asiento: " + numAsiento + 
				"\nPagado: " + pagado + 
				"\nCliente=" + cliente + 
				"\nVuelo=" + vuelo
				+ "]";
	}

	


}