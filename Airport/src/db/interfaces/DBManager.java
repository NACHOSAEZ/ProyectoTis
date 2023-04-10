package db.interfaces;

import pojos.Cliente;

public interface DBManager {

	void connect();
	
	void disconnect();
	
	void startTables();
	
	void createTables();
	
	void addCliente(Cliente cliente);	
}
