package com.five.onair.server.view;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.five.onair.server.utils.Configurations;
import com.five.onair.server.utils.UserOutUtils;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ConfigFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7599105129521687611L;
	private JTextField textField;
	private static ConfigFrame singleton;
	private JTextField txt_path;
	
	public static ConfigFrame getInstance(){
		if(singleton==null)
			singleton=new ConfigFrame();
		
		return singleton;
	}
	
	/**
	 * Create the frame.
	 */
	private ConfigFrame() {
		
		final Configurations config=Configurations.getInstance(); 
		
		setResizable(false);
		setTitle("Configura\u00E7\u00F5es");
		setBounds(100, 100, 377, 172);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblIpDoServidor = new JLabel("IP do servidor:");
		GridBagConstraints gbc_lblIpDoServidor = new GridBagConstraints();
		gbc_lblIpDoServidor.insets = new Insets(10, 10, 5, 5);
		gbc_lblIpDoServidor.anchor = GridBagConstraints.WEST;
		gbc_lblIpDoServidor.gridx = 0;
		gbc_lblIpDoServidor.gridy = 0;
		getContentPane().add(lblIpDoServidor, gbc_lblIpDoServidor);
		
		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 5, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		textField.setText(config.getServerIP());
		
		JLabel lblPastaWww = new JLabel("Pasta www:");
		GridBagConstraints gbc_lblPastaWww = new GridBagConstraints();
		gbc_lblPastaWww.anchor = GridBagConstraints.WEST;
		gbc_lblPastaWww.insets = new Insets(0, 10, 5, 5);
		gbc_lblPastaWww.gridx = 0;
		gbc_lblPastaWww.gridy = 2;
		getContentPane().add(lblPastaWww, gbc_lblPastaWww);
		
		txt_path = new JTextField();
		txt_path.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 5, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 3;
		getContentPane().add(txt_path, gbc_textField_1);
		txt_path.setColumns(10);
		txt_path.setText(config.getServerPath());
		
		JButton btnSelecionar = new JButton("Selecionar...");
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser=new JFileChooser();
				
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
				int res=fileChooser.showDialog(ConfigFrame.this, "Selecinar");
				if(res==JFileChooser.APPROVE_OPTION){
					txt_path.setText(fileChooser.getSelectedFile().getAbsolutePath());
					
				}
				
			}
		});
		GridBagConstraints gbc_btnSelecionar = new GridBagConstraints();
		gbc_btnSelecionar.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelecionar.gridx = 2;
		gbc_btnSelecionar.gridy = 3;
		getContentPane().add(btnSelecionar, gbc_btnSelecionar);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String serverPath=txt_path.getText().trim();
				File folder=new File(serverPath);
				if(!folder.exists()){
					UserOutUtils.showPopUP("O caminho para o servidor não existe! Por favor, tente novamente.", ConfigFrame.this);
					return;
				}else if(!folder.canWrite()){
					int res=JOptionPane.showOptionDialog(ConfigFrame.this, "A pasta em que seus arquivos serão instalados é protegida.\nAlterações podem afetar o comportamento do sistema\nDeseja continuar?", "Aviso importante!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Sim","Não"}, null);
					if(res==JOptionPane.NO_OPTION)
						return;
					
				}if(!folder.canRead()){
					UserOutUtils.showPopUP("A pasta selecionada não permite leitura! Por favor, selecione outra!", ConfigFrame.this);
				}
				
				config.setServerPath(txt_path.getText());
				
				Configurations.getInstance().setServerIP(textField.getText());
				Configurations.getInstance().save();
				
				UserOutUtils.showPopUP("Alterações salvas com sucesso!", ConfigFrame.this);
				
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 10, 0);
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 4;
		getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		
		
	
		

	}

}
