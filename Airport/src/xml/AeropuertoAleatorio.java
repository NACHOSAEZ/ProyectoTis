package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import pojos.Aeropuerto;

public class AeropuertoAleatorio {

	 final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	 private static DBManager dbman = new JDBCManager();

		public static Aeropuerto getAeropuertosAleatorio() {
			Random rand = new Random();
			ArrayList <Aeropuerto> aeropuertos = dbman.getAeropuertos();
			Aeropuerto aeropuerto = null;
			int i = rand.nextInt(aeropuertos.size()+1);
			aeropuerto = aeropuertos.get(i);
			return aeropuerto;
		}

		public void marshalling() throws JAXBException {
			Aeropuerto a = getAeropuertosAleatorio();
			// Creamos el JAXBContext
			JAXBContext jaxbC = JAXBContext.newInstance(Aeropuerto.class);
			// Creamos el JAXBMarshaller
			Marshaller jaxbM = jaxbC.createMarshaller();
			// Formateo bonito
			jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			//jaxbM.setProperty("com.sun.xml.bind.xmlHeaders", "\n<!DOCTYPE Aeropuerto SYSTEM \"Aeropuerto.dtd\">");
			//jaxbM.setProperty("com.sun.xml.bind.xmlDeclaration", false);
			// Escribiendo en un fichero
			File XMLfile = new File("./src/xml/Aeropuerto.xml");
			jaxbM.marshal(a, XMLfile);
			// Escribiendo por pantalla
			jaxbM.marshal(a, System.out);
		}
		
		public void unMarshalling() throws JAXBException {
			// Creamos el JAXBContext
			JAXBContext jaxbC = JAXBContext.newInstance(Aeropuerto.class);
			// Creamos el JAXBMarshaller
			Unmarshaller jaxbU = jaxbC.createUnmarshaller();
			// Leyendo un fichero
			File XMLfile = new File("./src/xml/Aeropuerto.xml");
			// Creando el objeto
			Aeropuerto a = (Aeropuerto) jaxbU.unmarshal(XMLfile);
			// Escribiendo por pantalla el objeto
			System.out.println(a);
		}

}
