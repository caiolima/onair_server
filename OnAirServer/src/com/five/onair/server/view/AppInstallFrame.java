package com.five.onair.server.view;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;

import org.apache.commons.io.FileUtils;

import com.five.onair.server.persistence.model.Application;
import com.five.onair.server.persistence.model.ApplicationList;
import com.five.onair.server.utils.Configurations;
import com.five.onair.server.utils.Constants;
import com.five.onair.server.utils.UserOutUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;

public class AppInstallFrame extends JFrame {

	private static AppInstallFrame singleton;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_name;
	private JLabel lblDescrio;
	private JTextField txt_desc;
	private JLabel lblCaminhoApp;
	private JTextField txt_appPath;
	private JButton btnInstalar;
	private String path_name;

	public static AppInstallFrame getInstance() {
		if (singleton == null)
			singleton = new AppInstallFrame();
		
		return singleton;
	}

	/**
	 * Create the frame.
	 */
	private AppInstallFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				cleanValues();
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setResizable(false);
		setTitle("Instalando aplica\u00E7\u00E3o");
		setBounds(100, 100, 325, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0,
				0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel = new JLabel("Nome:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		txt_name = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		contentPane.add(txt_name, gbc_textField);
		txt_name.setColumns(10);

		lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		GridBagConstraints gbc_lblDescrio = new GridBagConstraints();
		gbc_lblDescrio.insets = new Insets(0, 5, 5, 5);
		gbc_lblDescrio.anchor = GridBagConstraints.WEST;
		gbc_lblDescrio.gridx = 0;
		gbc_lblDescrio.gridy = 2;
		contentPane.add(lblDescrio, gbc_lblDescrio);

		txt_desc = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 3;
		contentPane.add(txt_desc, gbc_textField_1);
		txt_desc.setColumns(10);
		
				lblCaminhoApp = new JLabel("Caminho App:");
				GridBagConstraints gbc_lblCaminhoApp = new GridBagConstraints();
				gbc_lblCaminhoApp.anchor = GridBagConstraints.WEST;
				gbc_lblCaminhoApp.insets = new Insets(0, 5, 5, 5);
				gbc_lblCaminhoApp.gridx = 0;
				gbc_lblCaminhoApp.gridy = 4;
				contentPane.add(lblCaminhoApp, gbc_lblCaminhoApp);
		
				txt_appPath = new JTextField();
				GridBagConstraints gbc_textField_3 = new GridBagConstraints();
				gbc_textField_3.gridwidth = 2;
				gbc_textField_3.insets = new Insets(0, 0, 5, 5);
				gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
				gbc_textField_3.gridx = 0;
				gbc_textField_3.gridy = 5;
				contentPane.add(txt_appPath, gbc_textField_3);
				txt_appPath.setColumns(10);
		
				btnInstalar = new JButton("Instalar");
				btnInstalar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						if (txt_name.getText().equals("")) {
							UserOutUtils.showPopUP("Por favor, insira um nome!",
									AppInstallFrame.this);
							return;
						}

						if (txt_desc.getText().equals("")) {
							UserOutUtils.showPopUP("Por favor, insira uma descrição!",
									AppInstallFrame.this);
							return;
						}

						if (txt_appPath.getText().equals("")) {
							UserOutUtils.showPopUP(
									"Por favor, selecione a pasta da aplicação!",
									AppInstallFrame.this);
							return;
						}

						String appPath=txt_appPath.getText().trim();
//						File dir=new File(appPath);
//						
//						if (appPath.equals("")||!dir.exists()) {
//							UserOutUtils.showPopUP(
//									"O caminho da aplicação não é válido. Verifique se o caminho está correto!",
//									AppInstallFrame.this);
//							return;
//						}
						
						
						
						Application app=new Application();
						
						app.setName(txt_name.getText());
						app.setDescription(txt_desc.getText());
						app.setUrl(appPath);
						
//						//ApplicationList.getInstance().installApp(app);
//						Configurations config=Configurations.getInstance();
//						File app_folder=new File(txt_appPath.getText());
//						
//						File dest=new File(config.getServerPath()+"/"+path_name);
//						
//						try {
//							FileUtils.copyDirectory(app_folder, dest);
//						} catch (IOException e1) {
//							boolean result=com.five.onair.server.utils.FileUtils.copyDirectoryLikeRoot(txt_appPath.getText(), config.getServerPath());
//							if(result){
//								app.setInstalledLocation(config.getServerPath()+"/"+path_name);
//								
//								UserOutUtils.showPopUP("Aplicação instalada com sucesso!", AppInstallFrame.this);
//							}else{
//								UserOutUtils.showPopUP("Erro ao instalar a aplicação!", AppInstallFrame.this);
//							}
//						}
//						
						ApplicationList.getInstance().installApp(app);
						AppInstallFrame.this.setVisible(false);
						

					}
				});
				GridBagConstraints gbc_btnInstalar = new GridBagConstraints();
				gbc_btnInstalar.insets = new Insets(10, 0, 10, 0);
				gbc_btnInstalar.gridx = 1;
				gbc_btnInstalar.gridy = 6;
				contentPane.add(btnInstalar, gbc_btnInstalar);
				
	}
	
	public void cleanValues(){
		txt_appPath.setText("");
		txt_desc.setText("");
		txt_name.setText("");
		this.path_name="";
	}

}
