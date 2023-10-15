package br.com.cod3r.calc.modelo;

@FunctionalInterface
public interface MemoriaObservador {// display estará interessado em saber quando o valor de memoria modificar

	void valorAlterado(String novoValor);
}
