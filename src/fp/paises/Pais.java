package fp.paises;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import fp.utiles.Checkers;

public class Pais {
	
	private String codigoISO;
	private String nombre;
	private Long poblacion;
	private Double area;
	private String capital;
	private Continente continente;
	private String moneda;
	private List<Idioma> idiomas;
	private Set<String> vecinos;
	
	public Pais(String codigoISO, String nombre, Long poblacion, Double area, String capital, Continente continente,
			String moneda, List<Idioma> idiomas, Set<String> vecinos) {
		Checkers.check("El valor de la población debe ser mayor o igual a 0.", 
				poblacion >= 0);
		Checkers.check("El área debe ser mayor o igual a 0.", 
				area >= 0.);
		Checkers.check("Si la población es mayor a 0, entonces el país debe tener una capital y una moneda.", 
				(poblacion > 0 && capital != null && moneda != null) || (poblacion==0));
		this.codigoISO = codigoISO;
		this.nombre = nombre;
		this.poblacion = poblacion;
		this.area = area;
		this.capital = capital;
		this.continente = continente;
		this.moneda = moneda;
		this.idiomas = new ArrayList<>(idiomas);
		this.vecinos = new HashSet<>(vecinos);
	}

	public String getCodigoISO() {
		return codigoISO;
	}

	public String getNombre() {
		return nombre;
	}

	public Long getPoblacion() {
		return poblacion;
	}

	public Double getArea() {
		return area;
	}

	public String getCapital() {
		return capital;
	}

	public Continente getContinente() {
		return continente;
	}

	public String getMoneda() {
		return moneda;
	}

	public List<Idioma> getIdiomas() {
		return new ArrayList<>(idiomas);
	}

	public Set<String> getVecinos() {
		return new HashSet<>(vecinos);
	}
	
	public Double getDensidadPoblacion() {
		Double res = 0.;
		if (area != 0) {
			res = poblacion / area;
		}
		return res;
	}
	
	public Boolean getAislado() {
		return vecinos.isEmpty();
	}

	public int hashCode() {
		return Objects.hash(codigoISO);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		return Objects.equals(codigoISO, other.codigoISO);
	}

	public String toString() {
		return "Pais [codigoISO=" + codigoISO + ", nombre=" + nombre + ", poblacion=" + poblacion + ", area=" + area
				+ ", capital=" + capital + ", continente=" + continente + ", moneda=" + moneda + ", idiomas=" + idiomas
				+ ", vecinos=" + vecinos + "]";
	}
	
	// Boolean sonTodosVecinos(Set<Pais> paises): dado un conjunto de países, 
	// devuelve true si todos los países del conjunto dado como parámetro son 
	// vecinos del país, y false en caso contrario.
	public Boolean sonTodosVecinos(Set<Pais> paises) {
		Boolean res = true;
		for (Pais pais : paises) {
			if (!vecinos.contains(pais.codigoISO)) {
				res = false;
				break;
			}
		}
		return res;
	}
	
}
