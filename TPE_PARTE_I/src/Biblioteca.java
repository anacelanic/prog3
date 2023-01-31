import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Biblioteca {
	
	private LinkedList<Libro> libros;
	private Indice indice;
	private AdministradorArchivos admin;
	
	public Biblioteca()  {
		this.libros = new LinkedList<>();
		this.indice = new Indice();
	}
	
	public void setAdministradorArchivos(AdministradorArchivos admin) {
		this.admin = admin;
	}
	
	public void almacenarLibros() {
		if (this.admin != null) {	
			
			BufferedReader archivo = this.admin.readFile();
			String line = "";
			int contador = 0;
			
			if(archivo != null) {
				 try {
					while ((line = archivo.readLine()) != null) {
							
						if (contador > 0) {
										
							//creamos el libro
					        String[] items = line.split(",");
					        Libro libro = new Libro(items[0], items[1], Integer.parseInt(items[2]));
					        
					        //agregamos cada uno de los generos 
					        String[] generos = items[3].split(" ");
					        for (String g : generos) {
								libro.agregarGenero(g);
								
								if(!this.indice.tieneElGenero(g)) {
									//Creamos el genero
									Genero genero = new Genero(g);
									genero.agregarLibro(libro);
									//Lo agregamos
									this.indice.insertarOrdenado(genero);	
								} else {
									Genero genero = this.indice.buscarGenero(g);
									genero.agregarLibro(libro);
								}
							}      
					        //Agrego el libro a la LInkedList
					        libros.add(libro); 
						}
						
						contador++;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ArrayList<Libro> buscarPorGenero(String g) {
		Genero genero = this.indice.buscarGenero(g);
		if(genero != null) {
			return genero.getLibros();
		}
		return null; 
	}
	
	public void imprimirLibros(String g) {
		ArrayList<Libro> librosGenero = this.buscarPorGenero(g);
		if(librosGenero != null) {
			this.admin.writeFile(librosGenero);
		}
		
	}
	
	public static void main(String[] args) {
		
		Biblioteca b = new Biblioteca();
		AdministradorArchivos admin = new AdministradorArchivos("./resources/dataset3.csv","./resources/salida1.csv");
		
		b.setAdministradorArchivos(admin);

		b.almacenarLibros();
		
		long inicio = System.currentTimeMillis();
		
		b.imprimirLibros("juegos");
		
		long fin = System.currentTimeMillis();
		
		 double tiempo = (double) ((fin - inicio));
	        
		System.out.println(tiempo +" milisegundos");
        System.out.println(inicio +" inicio");
        System.out.println(fin +" fin");
        System.out.println("Cantidad de iteraciones: " + b.indice.getIteraciones());
        //cantidad generos
        System.out.println("Cantidad de géneros: " + b.indice.getGeneros().size());
        System.out.println(b.indice.getGeneros());
        
	}

}
