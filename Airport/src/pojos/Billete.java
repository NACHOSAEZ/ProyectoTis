package pojos;

public class Billete {
	
	private int id;
	private String Categoria;
	private int precio;
	private int numReserva;
	private String numAsiento;
	private boolean pagado;
	private Cliente cliente;
	
	public Billete() {
		super();
	}

	public Billete(int id, String categoria, int precio, int numReserva, String numAsiento, boolean pagado,
			Cliente cliente) {
		super();
		this.id = id;
		Categoria = categoria;
		this.precio = precio;
		this.numReserva = numReserva;
		this.numAsiento = numAsiento;
		this.pagado = pagado;
		this.cliente = cliente;
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

	public String getNumAsiento() {
		return numAsiento;
	}

	public void setNumAsiento(String numasiento) {
		this.numAsiento = numasiento;
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

	@Override
	public String toString() {
		return "Billete [id=" + id + ", Categoria=" + Categoria + ", precio=" + precio + ", numReserva=" + numReserva
				+ ", numasiento=" + numAsiento + ", pagado=" + pagado + ", cliente=" + cliente + "]";
		}
}