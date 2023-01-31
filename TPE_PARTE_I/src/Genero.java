import java.util.ArrayList;

public class Genero {
	
	private String genero;
	private ArrayList<Libro> librosPertenecientes;
	
	public Genero(String g) {
		this.librosPertenecientes = new ArrayList<>();
		this.genero = g;
	}
	
	public void agregarLibro(Libro l) {
		this.librosPertenecientes.add(l);
	}
	
	@Override
	public boolean equals(Object o) {
		try {
			Genero genere = (Genero) o;
			return genero.equals(genere.getGenero());
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getGenero() {
		return this.genero;
	}
	
	public ArrayList<Libro> getLibros() {
		return this.librosPertenecientes;
	}
	
	public String toString() {
		return this.genero;
	}

}
