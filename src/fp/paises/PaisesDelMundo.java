package fp.paises;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaisesDelMundo {
	
	private SortedSet<Pais> paises;
	private LocalDateTime fechaHoraActualizacion;
	
	public PaisesDelMundo(LocalDateTime fechaHoraActualizacion, Stream<Pais> paises) {
		this.fechaHoraActualizacion = fechaHoraActualizacion;
		this.paises = paises.collect(Collectors.toCollection(() -> 
		new TreeSet<>(Comparator.comparing(Pais::getNombre))));
	}
	
	public PaisesDelMundo(List<Pais> paises) {
		this.paises = paises.stream().collect(Collectors.toCollection(() -> 
		new TreeSet<>(Comparator.comparing(Pais::getNombre))));
		this.fechaHoraActualizacion = LocalDateTime.now();
	}
	
	public SortedSet<Pais> getPaises() {
		return new TreeSet<>(paises);
	}
	
	public LocalDateTime getFechaHoraActualizacion() {
		return fechaHoraActualizacion;
	}
	
	public Integer getNumeroPaises() {
		return paises.size();
	}

	public int hashCode() {
		return Objects.hash(fechaHoraActualizacion, paises);
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaisesDelMundo other = (PaisesDelMundo) obj;
		return Objects.equals(fechaHoraActualizacion, other.fechaHoraActualizacion)
				&& Objects.equals(paises, other.paises);
	}

	public String toString() {
		return "PaisesDelMundo [paises=" + paises + ", fechaHoraActualizacion=" + fechaHoraActualizacion + "]";
	}
	
	// 1. Double porcentajeAreaDeContinente(Continente cont): calcula el porcentaje de 
	// la superficie del continente dado como parámetro con respecto a la superficie 
	// total del mundo. (1 punto)
	public Double porcentajeAreaDeContinente(Continente cont) {
		Double areaMundo = paises.stream()
				.collect(Collectors.summingDouble(Pais::getArea));
		Double areaCont = paises.stream()
				.filter(p -> p.getContinente().equals(cont))
				.collect(Collectors.summingDouble(Pais::getArea));
		return areaCont / areaMundo;
	}
	
	// 2. List<String> nombresDeVecinos(String pais): devuelve una lista con los nombres 
	// de los vecinos del país recibido como parámetro. La lista debe estar ordenada alfabéticamente. 
	// Tenga en cuenta que la lista debe contener los nombres de los vecinos (no los códigos iso). (1,5 puntos)
	public List<String> nombresDeVecinos(String pais) {
		Map<String, String> paisesPorISO = paises.stream()
				.collect(Collectors.toMap(Pais::getCodigoISO, 
						Pais::getNombre));
		Pais paisBuscado = paises.stream()
				.filter(p -> p.getNombre().equals(pais))
				.findFirst()
				.get();
		return paisBuscado.getVecinos()
				.stream()
				.map(isoVecino -> paisesPorISO.get(isoVecino.trim()))
				.sorted()
				.collect(Collectors.toList());
	}
	
	// 3. SortedMap<String, List<String>> paisesPorIdiomas(): devuelve un Map ordenado que
	// contiene los nombres de los países que hablan el mismo idioma. Las claves deben 
	// estar ordenadas según su orden natural. Además, las listas con los nombres de los países 
	// también deben estar ordenadas según su orden alfabético. Este ejercicio debe resolverse 
	// utilizando bucles (no con streams). (1,5 puntos)
	public SortedMap<String, List<String>> paisesPorIdiomas() {
		SortedMap<String, List<String>> paisesIdiomas = new TreeMap<>();
		for (Pais pais : paises) {
			for (Idioma idioma : pais.getIdiomas()) {
				String clave = idioma.nombre();
				if (paisesIdiomas.containsKey(clave)) {
					paisesIdiomas.get(clave).add(pais.getNombre());
				} else {
					List<String> valor = new ArrayList<>();
					valor.add(pais.getNombre());
					paisesIdiomas.put(clave, valor);
				}
			}
		}
		for (List<String> paises : paisesIdiomas.values()) {
			Collections.sort(paises);
		}
		return paisesIdiomas;
	}
	
}
