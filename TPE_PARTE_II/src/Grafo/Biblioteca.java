package Grafo;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Esta clase administra los generos que se levantan a partir del administrador de archivos
 * @author Ana Celani
 * @see AdministradorArchivos 
 * 
 */
public class Biblioteca {
	
	private Grafo generos;
	private AdministradorArchivos admin;
	
	 /**
	  * Constructor de la clase Biblioteca, instancia un grafo
	  */
	public Biblioteca()  {
		this.generos = new GrafoDirigido();
	}
	
	/**
	 * Setea el administrador de archivos
	 * @param admin 
	 */
	public void setAdministradorArchivos(AdministradorArchivos admin) {
		this.admin = admin;
	}
	
	/**
	 * Se encarga de agregar cada uno de los distintos generos en la estructura del grafo.
	 * En caso de que el género ya exista no se agrega nuevamente.
	 */
	public void generarGrafoGeneros() {
		if (this.admin != null) {	
			
			BufferedReader archivo = this.admin.readFile();
			String line = "";
			int contador = 0;
			
			if(archivo != null) {
				 try {
					while ((line = archivo.readLine()) != null) {
							
						if (contador > 0) {
										
							//creamos el grafo
					        String[] items = line.split(",");
					        
					        for (int i = 0; i < items.length; i++) {  	
					        	if (!this.generos.contieneVertice(items[i])) {
					        		generos.agregarVertice(items[i]);
					        	}
					        	
					        	if (i > 0) { //Necesitamos que el vertice anterior exista para generar el arco
					        		if (!this.generos.existeArco(items[i-1], items[i])) {
					        			generos.agregarArco(items[i-1], items[i], 1);
					        		} else {
					        			Arco arco = this.generos.obtenerArco(items[i-1], items[i]);
					        			arco.setEtiqueta(arco.getEtiqueta() + 1);
					        		}
					        	}	
					        }
						}
						
						contador++;
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Encargada de imprimir la estructura del grafo
	 * @return string de los generos que se encuentran en la estructura del grafo.
	 */
	public String imprimirGrafo() {
		return this.generos.toString();
	}
	
	//SERVICIO 1
	 /**
	  * Primer servicio. 
	  * Se encarga de buscar en la estructura del grafo los n generos más buscados.
	  * Se define un género inicio y a partir de él se busca sobr el grafo los n géneros mas buscados luego del género inicial. 
	  * @param n número que define la cantidad de géneros a devolver
	  * @param genero a partir del cual se hace la búsqueda
	  * @return lista de los n géneros mas buscados
	  */
	public ArrayList<String> generosMasBuscados(int n, String genero) {
		ArrayList<String> solucion = new ArrayList<>();
		int minimo = 0;
		if (this.generos.contieneVertice(genero)) {
			ArrayList<Arco> arcos = this.generos.obtenerArcos(genero);
			Collections.sort(arcos); 
			
			if (n < arcos.size()) {
				minimo = n;
			} else {
				minimo = arcos.size();
			}
			
			for (int i = 0; i < minimo; i++) {
				solucion.add(arcos.get(i).getVerticeDestino());
			}
		}
		
		return solucion;
	}
	
	
	//SERVICIO 2	
	/**
	 * Servicio dos. 
	 * <br>
	 * Se encarga de hallar la secuencia de géneros con mayor valor de búsqueda. 
	 * Se define un género inicial dado por parámetro y se busca dentro de la estrcutura del grafo la secuencia que tiene una mayir
	 * suma de los valores de los arcos del grafo.
	 * <p>
	 * @param genero que da inicio a la secuencia de búsqueda
	 * @return una lista de géneros con la secuencia de búsqueda de mayor valor.
	 */
	public ArrayList<String> secuenciaMayorValorDeBusqueda(String genero){
		ArrayList<String> secuencia = new ArrayList<>();
		
		if (this.generos.contieneVertice(genero)) {
			HashMap<String, String> colores = new HashMap<>();
			Iterator<String> vertices = this.generos.obtenerVertices();
			while (vertices.hasNext()) { 
				String g = vertices.next();
				colores.put(g, "blanco");
			}
			
			String generoActual = genero;
			ArrayList<Arco> candidatos = this.generos.obtenerArcos(generoActual);	
			
			while (!candidatos.isEmpty() && colores.get(generoActual).equals("blanco")) {
								
				secuencia.add(generoActual);
				colores.put(generoActual, "amarillo");	
				generoActual = seleccionCandidato(candidatos);
				candidatos = this.generos.obtenerArcos(generoActual); 				
				
			}
			
			if (candidatos.isEmpty() && colores.get(generoActual).equals("blanco")) {
				secuencia.add(generoActual);
			}
		}	
		
		return secuencia;
	}
	
	private String seleccionCandidato(ArrayList<Arco> candidatos) {
		//Ordenamos los candidatos descendentemente y retornamos el primer genero (el mas pesado)
		//Candidatos nunca puede ser vacio (chequeado en el while)
		Collections.sort(candidatos);
		return candidatos.get(0).getVerticeDestino(); 
	}
	
	
	
	//SERVICIO 3
	
	/**
	 * Servicio tres.
	 * Se encarga de obtener los géneros a fines a un género dado por parámetro.
	 * Los géneros a fines son los que géneran un grafo cerrado.
	 * @param genero dado para buscar los géneros a fines.
	 * @return GrafoDirigido en caso de encontrar géneros a fines o null en caso de no encontrar géneros a fines.
	 */
	public GrafoDirigido obtenerGenerosAfines(String genero) {
		if (this.generos.contieneVertice(genero)) { //si contiene el genero 
			
			HashMap<String, String> colores = new HashMap<>();
			ArrayList<String> recorrido = new ArrayList<>();
			ArrayList<String> aux = new ArrayList<>();
			
			Iterator<String> vertices = this.generos.obtenerVertices(); //Obtenemos vertices del grafo generos
			while (vertices.hasNext()) { 
				String g = vertices.next();
				colores.put(g, "blanco");
			}
			
			this.buscarVinculacionCerrada(genero, aux, colores, recorrido);	
			return this.generarGrafo(recorrido);
			
		} 			
		
		return null;
	}

	/**
	 * Se encarga de buscar la vinculación cerrada.
	 * @param genero
	 * @param aux
	 * @param colores
	 * @param recorrido
	 */
	private void buscarVinculacionCerrada(String genero, ArrayList<String> aux, HashMap<String, String> colores, ArrayList<String> recorrido) {
		aux.add(genero);	
		if (colores.get(genero).equals("blanco")) { //si no esta visitado
			colores.put(genero, "amarillo");
			Iterator<String> adyacentes = this.generos.obtenerAdyacentes(genero);
			while (adyacentes.hasNext()) {
				String vertice = adyacentes.next();
				this.buscarVinculacionCerrada(vertice, aux, colores, recorrido);
			}
			
			colores.put(genero, "negro");
		}			
		if (genero.equals(aux.get(0))) {
			if (recorrido.isEmpty()) {
				recorrido.addAll(aux);
			} else if (recorrido.size() < aux.size())  {
				recorrido.clear();
				recorrido.addAll(aux);
			}
		}		
		aux.remove(aux.size() - 1);
	}

	private GrafoDirigido generarGrafo(ArrayList<String> recorrido) {
		GrafoDirigido grafo = new GrafoDirigido();
		for (int i = 0; i < recorrido.size(); i++) { 
			if (i < recorrido.size() - 1) {
				grafo.agregarVertice(recorrido.get(i)); 
			}
			if (i > 0) {
				Integer etiqueta = this.generos.obtenerArco(recorrido.get(i - 1), recorrido.get(i)).getEtiqueta(); 
				grafo.agregarArco(recorrido.get(i - 1), recorrido.get(i), etiqueta); 
			}
		}		
		return grafo;
	}
	
	
	public static void main(String[] args) {
		
		Biblioteca b = new Biblioteca();
		AdministradorArchivos admin = new AdministradorArchivos("./resources/dataset4.csv");
		
		b.setAdministradorArchivos(admin);		
		b.generarGrafoGeneros();
	
		long inicio = System.currentTimeMillis();
		
		System.out.println(b.obtenerGenerosAfines("servicios").toString());
		
		long fin = System.currentTimeMillis();
		double tiempo = (double) ((fin - inicio));
		
		System.out.println(tiempo + " milisegundos");
        System.out.println(inicio + " inicio de la carga");
        System.out.println(fin + " fin de la carga");
        
        //System.out.println( b.imprimirGrafo());
        //System.out.println(b.secuenciaMayorValorDeBusqueda("servicios"));
        //System.out.println(b.generosMasBuscados(3, "servicios"));        
	}

}
