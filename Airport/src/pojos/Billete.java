package pojos;

public class Billete {
	
	private int id;
	private String Categoria;
	private int precio;
	private int numreserva;
	private String numasiento;
	private boolean pagado;
	
	public Billete() {
		super();
	}


	public Billete(int id, String categoria, int precio, int numreserva, String numasiento, boolean pagado) {
		super();
		this.id = id;
		Categoria = categoria;
		this.precio = precio;
		this.numreserva = numreserva;
		this.numasiento = numasiento;
		this.pagado = pagado;
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

	public int getNumreserva() {
		return numreserva;
	}

	public void setNumreserva(int numreserva) {
		this.numreserva = numreserva;
	}

	public String getNumasiento() {
		return numasiento;
	}

	public void setNumasiento(String numasiento) {
		this.numasiento = numasiento;
	}

	public boolean isPagado() {
		return pagado;
	}

	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	
	@Override
	public String toString() {
		return "Billete [id=" + id + ", Categoria=" + Categoria + ", precio=" + precio + ", numreserva=" + numreserva
				+ ", numasiento=" + numasiento + ", pagado=" + pagado + "]";
	}

	
	
}
