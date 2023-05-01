package db.interfaces;

import java.util.ArrayList;

import pojos.Aeropuerto;
import pojos.Cliente;
import pojos.Compañia;

public interface DBManager {

	void connect();
	
	void disconnect();
	
	void startTables();
	
	void createTables();
	
	boolean addCliente(Cliente cliente);
	
	boolean addAeropuerto(Aeropuerto aeropuerto);

	ArrayList<Aeropuerto> getAeropuertos();

	ArrayList<Compañia> getCompañias();

	Aeropuerto getAeropuertoPorCodigo(String codigo);

	boolean addCompañia(Compañia compañia);

	Compañia getCompañiaPorNombre(String idNombre);

	int eliminarAeropuerto(Aeropuerto aeropuerto);

	int eliminarCompañia(Compañia compañia);


}