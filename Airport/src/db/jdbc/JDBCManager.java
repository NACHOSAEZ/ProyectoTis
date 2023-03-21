package db.jdbc;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import db.interfaces.DBManager;

public class JDBCManager implements DBManager{

	
	private static Connection c;
	private static Statement stmt;
	final static Logger TERM = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//informacion en la terminal
	private static final String LOCATION = "./db/Aeropuerto.db";
	private static final String ficheroStart = "./db/ddl.sql";
	private static final String ficheroStartAeropuerto = "./db/ficheroStartAeropuertos.sql";
	private static final String ficheroStartVuelo = "./db/ficheroStartVuelos";
	//fin ficheros sql
	
	
	
	//conexion base de datos
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:" + LOCATION);
	        stmt = c.createStatement();
	        createTables();
	        startTables();
	        TERM.info("Se ha inicializado la base de datos");
		}catch (SQLException  | ClassNotFoundException e) {
			TERM.severe("ERROR al iniciar la base de datos de la aplicacion\n" + e.toString());
		}
	}


	public void disconnect() {
		try {
			stmt.close();
			c.close();
			TERM.info("Desconexion de la base de datos");
		}catch (SQLException e) {
			TERM.warning("ERROR al desconectarse de la base de datos\n" + e.toString());
		}
	}

	//metodo para introducir valores a la base de datos
	private void startTables() {
		// TODO Auto-generated method stub
		
	}
	
	//metodo para crear las tablas en la base de datos
	private void createTables() {
		File file = new File(ficheroStart);
		try (Scanner scanner = new Scanner(file)){
			
			String sqlStart = "";
			
			while( scanner.hasNextLine()) {
				sqlStart += scanner.nextLine();
			}
			
			stmt.executeUpdate(sqlStart);
			TERM.info("Tablas iniciales creadas correctamente");
			
		}catch(FileNotFoundException e) {
			TERM.info("Error al leer el fichero para inicializar las tablas\n" + e);
		}catch(SQLException e) {
			TERM.info("Error al leer el codigo SQL del fichero de inicializacion de tablas\n" + e);
		}
	}
	
	
	
		
	}

