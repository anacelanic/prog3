import java.util.ArrayList;

public class Indice {
	
	private ArrayList<Genero> indiceGeneros;
	private int iteraciones;
	
	public Indice() {
		this.indiceGeneros = new ArrayList<>();
	}
	
	public ArrayList<Genero> getGeneros() {
		return this.indiceGeneros;
	}
	
	public int getIteraciones() {
		return this.iteraciones;
	}
	
	public boolean tieneElGenero(String g) {
		Genero genero = new Genero(g);
		return this.indiceGeneros.contains(genero);
	}
	
	public Genero buscarGenero(String g) {
		int inicio = 0;
		this.iteraciones = 0;
		int fin = this.indiceGeneros.size() - 1;
		return buscadorGenero(g, inicio, fin);
	}

	private Genero buscadorGenero(String g, int inicio, int fin) {
		if(inicio  == fin) {
			if(this.indiceGeneros.get(inicio).getGenero().equals(g)) {
				return this.indiceGeneros.get(inicio);
			} 
			return null;
		} else {
			this.iteraciones++;
			int medio = ((fin - inicio) / 2) +  inicio;
			if(this.indiceGeneros.get(medio).getGenero().equals(g)) {
				return this.indiceGeneros.get(medio);
			} else if (this.indiceGeneros.get(medio).getGenero().compareTo(g) < 0) {
				return this.buscadorGenero(g, medio + 1, fin);
			} else {
				return this.buscadorGenero(g, inicio, medio);
			}
		}
	}

	public void insertarOrdenado(Genero genero) { //O(n)
		if(this.indiceGeneros.isEmpty()) {
			this.indiceGeneros.add(genero); //O(1)
		} else {
			int inicio = 0;
			int fin = this.indiceGeneros.size() - 1;
			int indice = this.buscadorPosicion(genero, inicio, fin); //log_2(n) log en base dos de n 
			if(indice == this.indiceGeneros.size()) {
				this.indiceGeneros.add(genero);
			} else {
				this.indiceGeneros.add(indice, genero); //O(n)
			}
		}		
	}
	
	
	private int buscadorPosicion(Genero g, int inicio, int fin) { //Busqueda binaria
		if(inicio  == fin) {
			if (this.indiceGeneros.get(inicio).getGenero().compareTo(g.getGenero()) < 0) { //primer string es menor
				return inicio + 1;
			} else {
				return inicio;
			}
		} else {
			int medio = ((fin - inicio) / 2) +  inicio;
			if(this.indiceGeneros.get(medio).equals(g)) {
				return medio;
			} else if (this.indiceGeneros.get(medio).getGenero().compareTo(g.getGenero()) < 0) { //primer string es menor
				return this.buscadorPosicion(g, medio + 1, fin);
			} else {
				return this.buscadorPosicion(g, inicio, medio);
			}
		}
	}

}
