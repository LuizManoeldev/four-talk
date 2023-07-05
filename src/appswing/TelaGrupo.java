
package appswing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


import regras_negocio.Fachada;

public class TelaGrupo {
	private JDialog frame;			//permite controle modal
	private JLabel Output;
	private JTextField txtIndividuo;
	private JTextField txtGrupo;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
//		public static void main(String[] args) {
//			EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					try {
//						TelaLogin window = new TelaLogin();
//						window.frame.setVisible(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}

	/**
	 * Create the application.
	 */
	public TelaGrupo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setResizable(false);
		frame.setTitle("Grupo");
		frame.setBounds(100, 100, 260, 234);
		frame.getContentPane().setLayout(null);
		
		// janela modal
		//---------------------------------------------
		frame.setModal(true);
		//---------------------------------------------
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		
		Output = new JLabel("");
		Output.setForeground(Color.RED);
		Output.setBounds(10, 125, 213, 27);
		frame.getContentPane().add(Output);
		
		txtIndividuo = new JTextField();
		txtIndividuo.setBounds(79, 33, 89, 20);
		frame.getContentPane().add(txtIndividuo);
		txtIndividuo.setColumns(10);
		
		txtGrupo = new JTextField();
		txtGrupo.setBounds(79, 64, 89, 20);
		frame.getContentPane().add(txtGrupo);
		txtGrupo.setColumns(10);
		
		JButton btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String individuo = txtIndividuo.getText();
				String grupo = txtGrupo.getText();
				
				if(individuo.isEmpty()) {
					Output.setText("Insira um Individuo");
				} else if (grupo.isEmpty()) {
					Output.setText("Insira um Grupo");
				} else {
					Output.setText("");
					try {
						Fachada.inserirGrupo(individuo, grupo);
						Output.setText(individuo + " inserido");
					
					} catch (Exception e1) 	{
						Output.setText(e1.getMessage());
					}
				}
			}
		});
		btnInserir.setBounds(10, 95, 75, 20);
		frame.getContentPane().add(btnInserir);
		
		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String individuo = txtIndividuo.getText();
				String grupo = txtGrupo.getText();
				
				if(individuo.isEmpty()) {
					Output.setText("Insira um Individuo");
				} else if (grupo.isEmpty()) {
					Output.setText("Insira um Grupo");
				} else {
					Output.setText("");
					try {
						Fachada.removerGrupo(individuo, grupo);
						Output.setText(individuo + " foi removido");
					
					} catch (Exception e1) 	{
						Output.setText(e1.getMessage());
					}
				}
			}
		});
		btnRemover.setBounds(85, 95, 89, 20);
		frame.getContentPane().add(btnRemover);
		
		JLabel lblIndividuo = new JLabel("Individuo");
		lblIndividuo.setBounds(10, 36, 59, 14);
		frame.getContentPane().add(lblIndividuo);
		
		JLabel lblGrupo = new JLabel("Grupo");
		lblGrupo.setBounds(10, 67, 46, 14);
		frame.getContentPane().add(lblGrupo);
		
		lblNewLabel = new JLabel("Inserir / Remover Grupo");
		lblNewLabel.setBounds(26, 8, 142, 14);
		frame.getContentPane().add(lblNewLabel);

		
		frame.setVisible(true);
	}

}