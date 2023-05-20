
package pojos;

import java.util.ArrayList;
import java.util.Date;

public class Vuelo {

	private int idVuelo;
	private String hora;
	private int asientos;
	private Aeropuerto origen;
	private Aeropuerto destino;
	private Compañia compañia;
	private ArrayList<Compañia> compañias;

	
	public Vuelo() {
		super();
		compañias = new ArrayList<>();
	}





	public Vuelo(int idVuelo) {
		super();
		this.idVuelo = idVuelo;
	}





	public Vuelo(int idVuelo, String hora, int asientos, Aeropuerto origen, Aeropuerto destino) {
		super();
		this.idVuelo = idVuelo;
		this.hora = hora;
		this.asientos = asientos;
		this.origen = origen;
		this.destino = destino;
	}





	public Vuelo(int idVuelo, String hora, int asientos, Aeropuerto origen, Aeropuerto destino, Compañia compañia) {
		super();
		this.idVuelo = idVuelo;
		this.hora = hora;
		this.asientos = asientos;
		this.origen = origen;
		this.destino = destino;
		this.compañia = compañia;
	}






	public Vuelo(int idVuelo, String hora, int asientos, Aeropuerto origen, Aeropuerto destino,
			ArrayList<Compañia> compañias) {
		super();
		this.idVuelo = idVuelo;
		this.hora = hora;
		this.asientos = asientos;
		this.origen = origen;
		this.destino = destino;
		this.compañias = compañias;
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


	public Aeropuerto getOrigen() {
		return origen;
	}


	public void setOrigen(Aeropuerto origen) {
		this.origen = origen;
	}


	public Aeropuerto getDestino() {
		return destino;
	}


	public void setDestino(Aeropuerto destino) {
		this.destino = destino;
	}


	public Compañia getCompañia() {
		return compañia;
	}


	public void setCompañia(Compañia compañia) {
		this.compañia = compañia;
	}


	public ArrayList<Compañia> getCompañias() {
		return compañias;
	}


	public void setCompañias(ArrayList<Compañia> compañias) {
		this.compañias = compañias;
	}


	@Override
	public String toString() {
		return " ("+ idVuelo +") " + "Vuelo" +
				"\nHora: " + hora + 
				"\nAsientos: " + asientos +
				"\nOrigen: " + origen +
				"\nDestino " + destino;
	}


	


}