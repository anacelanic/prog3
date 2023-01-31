package Grafo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Esta clase se encarga de administrar los archivos csv
 * @author Ana Celani
 * 
 */
public class AdministradorArchivos {
	
	private String pathFileRead;

	public AdministradorArchivos(String pathFileRead) {
		this.pathFileRead = pathFileRead;
	}
	
	/**
	 * Crea el BufferedReader que permite leer la ruta especifica linea por linea
	 * @return BufferedReader
	 */
	public BufferedReader readFile() {
        //String csvFile = "./resources/dataset2.csv";
        try {
        	//Creamos el objeto BufferedReader que permite leer el archivo de la ruta especificada linea x linea
        	return new BufferedReader(new FileReader(this.pathFileRead)); 
           
        } catch (IOException e) {
        	
            e.printStackTrace();
        }
        
		return null;
    }

}
