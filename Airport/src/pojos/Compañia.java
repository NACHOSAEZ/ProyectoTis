package pojos;

import java.util.ArrayList;

public class Compañia {

	
	private int id;
	private String correo;
	private String numtelf;
	private String nombre;
	private String paginaweb;
	private String pais;
	private ArrayList<Vuelo> vuelos;
	
	
	public Compañia() {
		super();
		vuelos = new ArrayList<>();
	}






	public Compañia(int id, String correo, String numtelf, String nombre, String paginaweb, String pais) {
		super();
		this.id = id;
		this.correo = correo;
		this.numtelf = numtelf;
		this.nombre = nombre;
		this.paginaweb = paginaweb;
		this.pais = pais;
	}






	public Compañia(int id, String correo, String numtelf, String nombre, String paginaweb, String pais,
			ArrayList<Vuelo> vuelos) {
		super();
		this.id = id;
		this.correo = correo;
		this.numtelf = numtelf;
		this.nombre = nombre;
		this.paginaweb = paginaweb;
		this.pais = pais;
		this.vuelos = vuelos;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getNumtelf() {
		return numtelf;
	}


	public void setNumtelf(String numtelf) {
		this.numtelf = numtelf;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPaginaweb() {
		return paginaweb;
	}


	public ArrayList<Vuelo> getVuelos() {
		return vuelos;
	}


	public void setVuelos(ArrayList<Vuelo> vuelos) {
		this.vuelos = vuelos;
	}


	public void setPaginaweb(String paginaweb) {
		this.paginaweb = paginaweb;
	}


	public String getPais() {
		return pais;
	}


	public void setPais(String pais) {
		this.pais = pais;
	}


	@Override
	public String toString() {
		return id + ". Compañia: " + nombre +
				"\nPagina Web: " + paginaweb + 
				"\nPais: " + pais + 
				"\nNumero de telefono: " + numtelf + 
				"\nEmail de contacto: " + correo +
				vuelos;
	}
	
	
}
