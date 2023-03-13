package pojos;

import java.util.Date;

public class Vuelo {

	private String numVuelo;
	private Date fecha;
	private String hora;
	private int asientos;
	
	public Vuelo() {
		super();
	}
	
	
	public Vuelo(String numVuelo, Date fecha, String hora, int asientos) {
		this.numVuelo = numVuelo;
		this.fecha = fecha;
		this.hora = hora;
		this.asientos = asientos;
	}


	public String getNumVuelo() {
		return numVuelo;
	}


	public void setNumVuelo(String numVuelo) {
		this.numVuelo = numVuelo;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public int getAsientos() {
		return asientos;
	}


	public void setAsientos(int asientos) {
		this.asientos = asientos;
	}


	@Override
	public String toString() {
		return "Vuelo [numVuelo=" + numVuelo + ", fecha=" + fecha + ", hora=" + hora + ", asientos=" + asientos + "]";
	}
	
	
	
}
