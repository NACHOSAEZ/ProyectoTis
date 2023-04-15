package db.interfaces;

import pojos.Aeropuerto;
import pojos.Cliente;

public interface DBManager {

	void connect();
	
	void disconnect();
	
	void startTables();
	
	void createTables();
	
	boolean addCliente(Cliente cliente);
	
	boolean addAeropuerto(Aeropuerto aeropuerto);
}
