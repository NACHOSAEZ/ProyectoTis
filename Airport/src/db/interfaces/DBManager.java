package db.interfaces;

import java.util.ArrayList;

import pojos.Aeropuerto;
import pojos.Billete;
import pojos.Cliente;
import pojos.Compañia;
import pojos.Empleado;

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

	ArrayList<Empleado> getEmpleados();

	boolean addEmpleado(Empleado empleado);

	Aeropuerto getAeropuertoPorId(int idAeropuerto);

	int eliminarEmpleado(Empleado empleado);

	Empleado getEmpleadoPorId(String idEmpleado);

	ArrayList<Billete> getBilletes();

	ArrayList<Cliente> getClientes();

	Cliente getClientePorId(String idCliente);

	int eliminarCliente(Cliente cliente);

	boolean addBillete(Billete billete);

}