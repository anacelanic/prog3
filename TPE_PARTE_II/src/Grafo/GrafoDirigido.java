package Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GrafoDirigido implements Grafo {
	
	private HashMap<String, ArrayList<Arco>> map; //estructuraGrafoDirigido;
	
	public GrafoDirigido() {
		this.map = new HashMap<>();
	}

	@Override
	public void agregarVertice(String verticeId) { //O(1)
		if(!map.containsKey(verticeId)) { //O(1)  
			ArrayList<Arco> value = new ArrayList<>();
			map.put(verticeId, value); //O(1)
		}
	}

	@Override
	public void borrarVertice(String verticeId) {
		if(map.containsKey(verticeId)) { 
			map.remove(verticeId);
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				ArrayList<Arco> arcos = map.get(key);
				for (Arco arco : arcos) {
					if(arco.getVerticeDestino() == verticeId) {
						arcos.remove(arco);
					}
				}
			}
		}
	}

	@Override
	public void agregarArco(String verticeId1, String verticeId2, Integer etiqueta) {
		if(map.containsKey(verticeId1) && map.containsKey(verticeId2)) {
			Arco arco = new Arco(verticeId1, verticeId2, etiqueta);
			ArrayList<Arco> arcos = map.get(verticeId1);
			if(!arcos.contains(arco)) { //falta redefinir el equals en clase arco
				arcos.add(arco);
			}
		}
	}

	@Override
	public void borrarArco(String verticeId1, String verticeId2) {
		if(map.containsKey(verticeId1)) {
			ArrayList<Arco> arcos = map.get(verticeId1);
			for (Arco arco : arcos) {
				if(arco.getVerticeDestino() == verticeId2) {
					arcos.remove(arco);
					return;
				}
			}
		}
	}

	@Override
	public boolean contieneVertice(String verticeId) {
		return map.containsKey(verticeId);
	}

	@Override
	public boolean existeArco(String verticeId1, String verticeId2) {
		if(map.containsKey(verticeId1)) {
			ArrayList<Arco> arcos = map.get(verticeId1);
			for (Arco arco : arcos) {
				if(arco.getVerticeDestino().equals(verticeId2)) {
					return true;

				}
			}
		}
		return false;
	}

	@Override
	public Arco obtenerArco(String verticeId1, String verticeId2) {
		if(map.containsKey(verticeId1) && map.containsKey(verticeId2)) {
			ArrayList<Arco> arcos = map.get(verticeId1);
			for (Arco arco : arcos) {
				if(arco.getVerticeDestino().equals(verticeId2)) {
					return arco;

				}
			}
		}
		return null;
	}

	@Override
	public int cantidadVertices() {
		return map.size();
	}

	@Override
	public int cantidadArcos() {
		int cant = 0;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			ArrayList<Arco> arcos = map.get(key);
			cant += arcos.size();
		}
		return cant;
	}

	@Override
	public Iterator<String> obtenerVertices() {
		return map.keySet().iterator(); //Obtengo una lista con los vertices
	}

	@Override
	public Iterator<String> obtenerAdyacentes(String verticeId) {
		if(map.containsKey(verticeId)) {
			ArrayList<Arco> arcos = map.get(verticeId);
			ArrayList<String> adyacentes = new ArrayList<>(); 
			for (Arco arco : arcos) {
				adyacentes.add(arco.getVerticeDestino());
			}
			return adyacentes.iterator();
		} else {
			return null;
		}
	}
	
	@Override
	public Iterator<Arco> obtenerArcos() {
		if(!map.isEmpty()) {
			ArrayList<Arco> arcos = new ArrayList<>();
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				arcos.addAll(map.get(key));
			}
			return arcos.iterator();
		} else {
			return null;
		}
	}
	
	public ArrayList<Arco> obtenerArcos(String verticeId) {
		if(map.containsKey(verticeId)) {
			ArrayList<Arco> arcos = map.get(verticeId);
			return arcos;
		} else {
			return null;
		}
	}
	
	public String toString() {
		Iterator<Arco> arcos = this.obtenerArcos();
		StringBuffer buffer = new StringBuffer();
		while(arcos.hasNext()) {
			Arco next = arcos.next();
			buffer.append(next.getVerticeOrigen() + " -> " + next.getVerticeDestino() + " [label = " + next.getEtiqueta() + "];\n");
		}
		return buffer.toString();
	}
	

}
