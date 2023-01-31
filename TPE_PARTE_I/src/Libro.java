import java.util.ArrayList;

public class Libro {
	
	private String titulo;
	private String autor;
	private int cantPaginas;
	private ArrayList<String> generos;
	
	public Libro(String titulo, String autor, int cantPaginas) {
		this.titulo = titulo;
		this.autor = autor;
		this.cantPaginas = cantPaginas;
		this.generos = new ArrayList<>();
	}
	
	public void agregarGenero(String g) {
		this.generos.add(g);
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
   public String toString() {
        return this.getTitulo();
    }
	
}


