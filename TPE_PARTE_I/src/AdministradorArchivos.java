import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AdministradorArchivos {
	
	private String pathFileRead;
	private String pathFileWrite;

	public AdministradorArchivos(String pathFileRead, String pathFileWrite) {
		this.pathFileRead = pathFileRead;
		this.pathFileWrite = pathFileWrite;
	}
	
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
	 
	public void writeFile(ArrayList<Libro> libros) {
		BufferedWriter bw = null;
		try {
			//File file = new File("./resources/salida1.csv");
			File file = new File(this.pathFileWrite);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			
			for (Libro libro : libros) {
				bw.write(libro.toString());
				bw.newLine();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
