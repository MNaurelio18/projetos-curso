package br.com.cod3r.calc.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame{

	public Calculadora() {
		
		organizarLayout();
		setSize(232, 322);
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE); //fechar aplicação
		setDefaultCloseOperation(EXIT_ON_CLOSE);//fechar aplicação
		setLocationRelativeTo(null);
		setVisible(true);
		}
		
		private void organizarLayout() { // juntou teclado e display instanciando, depois organizou
		setLayout(new BorderLayout());
		
		Display display = new Display();
		display.setPreferredSize(new Dimension(233, 60));
		add(display, BorderLayout.NORTH);
		Teclado tecado = new Teclado();
		add(tecado, BorderLayout.CENTER);
		
	}

	public static void main(String[] args) {
		
		new Calculadora();
	
	}
	
}
