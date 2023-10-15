package br.com.cod3r.calc.visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.cod3r.calc.modelo.Memoria;
import br.com.cod3r.calc.modelo.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador{ // componente que agrupa outros componentes

	private final JLabel label;
	
	public Display() {
		Memoria.getInstancia().adicionarObservador(this);//display esta interessado em ser notificado quando valor modificar para atualizar o label do display
		                                                 // junta a interface com a logica
		setBackground(new Color(46, 49, 50));
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		label.setForeground(Color.WHITE);
		label.setFont(new Font("courier", Font.PLAIN, 25));
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
		add(label);
	}
	
	@Override
		public void valorAlterado(String novoValor) {//sempre que houver mudança na memoria o display será avisado 
			label.setText(novoValor);                //e setará o valor no texto do label
			                                         //tem que resgistrar o displau na memoria para ser avisado
		} 
	
}
