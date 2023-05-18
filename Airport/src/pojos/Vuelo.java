
package pojos;

import java.util.Date;

public class Vuelo {

	private int idVuelo;
	private String hora;
	private int asientos;
	private String origen;
	private String destino;
	private Compañia compañia;

	
	public Vuelo() {
		super();
	}


	public Vuelo(int idVuelo, String hora, int asientos, String origen, String destino, Compañia compañia) {
		super();
		this.idVuelo = idVuelo;
		this.hora = hora;
		this.asientos = asientos;
		this.origen = origen;
		this.destino = destino;
		this.setCompañia(compañia);
	}


	public int getIdVuelo() {
		return idVuelo;
	}


	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
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


	public String getOrigen() {
		return origen;
	}


	public void setOrigen(String origen) {
		this.origen = origen;
	}


	public String getDestino() {
		return destino;
	}


	public void setDestino(String destino) {
		this.destino = destino;
	}


	public Compañia getCompañia() {
		return compañia;
	}


	public void setCompañia(Compañia compañia) {
		this.compañia = compañia;
	}


	@Override
	public String toString() {
		return "Vuelo [idVuelo=" + idVuelo + ", hora=" + hora + ", asientos=" + asientos
				+ ", origen=" + origen + ", destino=" + destino + ", compañia=" + compañia + "]";
	}
	
	
	
	
	
	
	
}