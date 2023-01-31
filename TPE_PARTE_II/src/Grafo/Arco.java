package Grafo;

/**
 * Esta clase representa la estructura de arcos que une dos vertices del grafo y los metodos necesarios.
 * @see Grafo y GrafoDirigido
 * @author Ana Celani
 *
 */

//Primer linea: descripcion general de que hace la funcion
//Sebunda linea: descripcion mas mas abundate y detallada del método
//parametros
//Retornos

public class Arco implements Comparable<Arco> {

	private String verticeOrigen;
	private String verticeDestino;
	private Integer etiqueta;

	public Arco(String verticeOrigen, String verticeDestino, Integer etiqueta) {
		this.verticeOrigen = verticeOrigen;
		this.verticeDestino = verticeDestino;
		this.etiqueta = etiqueta;
	}
	
	/**
	 * Devuelve el vértice origen del arco
	 * @return vertice origen
	 */
	public String getVerticeOrigen() {
		return verticeOrigen;
	}
	 /**
	  * Devuelve vertice destino del arco
	  * @return vertice destino
	  */
	public String getVerticeDestino() {
		return verticeDestino;
	}

	/**
	 * Devuelve el valor del arco, el cual se genera a partir de la cantidad de busquedas entre los vértices (géneros).
	 * @return valor del arco
	 */
	public Integer getEtiqueta() {
		return etiqueta;
	}
	
	/**
	 * Setea el valor de la etiqueta del arco el cual representa la cantidad de búsquedas entre ambos géneros (vértices).
	 * @param valor de la etiqueta
	 */
	public void setEtiqueta(Integer i) {
		this.etiqueta = i;
	}

	/**
	 * Comparador entre arcos. Define en base al valor de las etiquetas.
	 * @return -1, 0 o 1 en base a la comparación.
	 */
	@Override
	public int compareTo(Arco o) {
		return o.getEtiqueta().compareTo(this.getEtiqueta());
	}

}