package br.com.cod3r.calc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {//nao esta relacionado com interface gráfic
	                  //interface grafica vai usar a logica que esta em memoria, mas nao precisa amarrar ao swing

	
	private enum TipoComando{//pode enum em uma classe
		ZERAR,SINAL, NUMERO, DIV, MULT, SUB, SOMA, IGUAL,VIRGULA;
	};
	
	private static final Memoria instancia = new Memoria();
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	
	private TipoComando ultimaOperacao = null;
	private boolean substituir = false; //o texto no display
	private String textoAtual = "";
	private String textoBuffer = ""; //valor interno

	private  Memoria() {
		
	}

	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	public void processarComando(String texto) {
		
		TipoComando tipoComando = detectarTipoComando(texto);
		
		if(tipoComando == null) {
			return;
		}
		else if(tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}
		else if(tipoComando == TipoComando.SINAL && textoAtual.contains("-")) {
			textoAtual = textoAtual.substring(1);
		}
		else if(tipoComando == TipoComando.SINAL && !textoAtual.contains("-")) {
			textoAtual = "-" + textoAtual;
		}
		else if(tipoComando == TipoComando.NUMERO || tipoComando == TipoComando.VIRGULA){
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;		
		}
		else {
			substituir = true;
			textoAtual = obterResultadoOperacao();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
			
		observadores.forEach(o -> o.valorAlterado(getTextoAtual()));//notifica os observadores quando houver um comando(soma, subtracao etc) 
	}

	private String obterResultadoOperacao() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
		
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		}
		else if(ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		}
		if(ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		}
		if(ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		}
			
		String texto = Double.toString(resultado).replace(".", ",");
		boolean inteiro = texto.endsWith(",0");
	
		return inteiro ? texto.replace(",0", "") : texto;
	}

	private TipoComando detectarTipoComando(String texto) { //detecta o comando
		if(textoAtual.isEmpty() && texto == "0") {
			return null;
		}	
		
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e) {
			//Quando nao for numero
			
			if("AC".equals(texto)) {
				return TipoComando.ZERAR;	
			}
			else if("/".equals(texto)) {
				return TipoComando.DIV;
			}
			else if("*".equals(texto)) {
				return TipoComando.MULT;
			}
			else if("+".equals(texto)) {
				return TipoComando.SOMA;
			}
			else if("-".equals(texto)) {
				return TipoComando.SUB;
			}
			else if("=".equals(texto)) {
				return TipoComando.IGUAL;
			}
			else if(",".equals(texto) && !textoAtual.contains(",")) {//so se o texto atual ja nao tiver nenhuma virgula
				return TipoComando.VIRGULA;
			}
			else if("±".equals(texto)) {
				return TipoComando.SINAL;
			}		
		}	
		
		return null;
	}
		
	 
}
