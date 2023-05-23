package db.interfaces;

import java.util.ArrayList;

import pojos.Aeropuerto;
import pojos.Billete;
import pojos.Cliente;
import pojos.Compañia;
import pojos.Empleado;
import pojos.Vuelo;

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

	Vuelo getVueloPorId(int numVuelo);

	ArrayList<Vuelo> getVuelos();

	int eliminarVuelo(Vuelo vuelo);

	boolean addVuelo(Vuelo vuelo);

	ArrayList<Empleado> getEmpleadoPorAeropuerto(int idAeropuerto);

	Compañia getCompañiaPorId(int idAeropuerto);

	boolean addVuelo_Compañia(Vuelo vuelo);
	
	ArrayList<Vuelo> getVuelosPorCompañia(int idCompañia);


	int getNumeroAsientosUltimoVuelo();

	boolean addBillete(Billete billete);

	boolean addBilleteDefault(Billete billete);

	boolean addBilleteCliente(Billete billete, Cliente cliente);

	ArrayList<Billete> getBilletesPorVuelo(int idVueloo);

	boolean addBilleteACliente(int idCliente, int idBillete);

	Cliente getClientePorEmail(String email);

	ArrayList<Billete> getBilletesCliente(int idCliente);

}