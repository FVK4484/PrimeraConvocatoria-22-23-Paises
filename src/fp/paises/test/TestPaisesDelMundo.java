package fp.paises.test;

import java.util.ArrayList;
import java.util.List;

import fp.paises.Continente;
import fp.paises.FactoriaPaises;
import fp.paises.Pais;
import fp.paises.PaisesDelMundo;

public class TestPaisesDelMundo {

	public static void main(String[] args) {
		List<Pais> paises = FactoriaPaises.leePaises("./data/CSV de la sesión 2");
		PaisesDelMundo paisesMundo = new PaisesDelMundo(paises);
		
		testLeePaises();
		testPorcentajeAreaDeContinente(paisesMundo, Continente.SOUTH_AMERICA);
		testNombresDeVecinos(paisesMundo, "Spain");
		testPaisesPorIdiomas(paisesMundo);

	}
	
	private static void testLeePaises() {
		List<Pais> paises = FactoriaPaises.leePaises("./data/CSV de la sesión 2");
		for (Pais pais : paises) {
			System.out.println(pais);
		}
	}
	
	public static void testPorcentajeAreaDeContinente(PaisesDelMundo paisesMundo, Continente cont) {
		System.out.println("Test porcentajeAreaDeContinente (" + cont + ")\n" + "-------------------------------------------");
		System.out.println(paisesMundo.porcentajeAreaDeContinente(cont));
	}
	
	public static void testNombresDeVecinos(PaisesDelMundo paisesMundo, String pais) {
		System.out.println("Test nombresDeVecinos (" + pais + ")\n" + "-------------------------------------------");
		System.out.println(paisesMundo.nombresDeVecinos(pais));
	}
	
	public static void testPaisesPorIdiomas(PaisesDelMundo paisesMundo) {
		System.out.println("Test paisesPorIdiomas\n" + "-------------------------------------------");
		
		var diccionario = paisesMundo.paisesPorIdiomas();
		
		List<java.util.Map.Entry<?, ?>> lista = new ArrayList<>(diccionario.entrySet());
		int total = lista.size();
		
		StringBuilder texto = new StringBuilder();
		texto.append("{");
		
		if (total <= 6) {
			for (int i = 0; i < total; i++) {
				texto.append(lista.get(i).getKey()).append("=").append(lista.get(i).getValue());
				if (i < total - 1) texto.append(", ");
			}
		} else {
			for (int i = 0; i < 3; i++) {
				texto.append(lista.get(i).getKey()).append("=").append(lista.get(i).getValue());
				texto.append(", ");
			}
			
			texto.append("…, ");
			
			for (int i = total - 3; i < total; i++) {
				texto.append(lista.get(i).getKey()).append("=").append(lista.get(i).getValue());
				if (i < total - 1) texto.append(", ");
			}
		}
		
		texto.append("}"); 
		
		System.out.println(texto.toString());
	}

}
