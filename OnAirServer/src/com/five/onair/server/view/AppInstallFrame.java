package com.five.onair.server.view;

import javax.swing.JComboBox;
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
	private JLabel lblPort;
	private JTextField txt_port;
	private JLabel lblCaminhoApp;
	private JTextField txt_appPath;
	private JButton btnInstalar;
	private JComboBox<String> cmb_protocol;
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

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

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
		setBounds(100, 100, 325, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblProtocol = new JLabel("Protocolo:");
		GridBagConstraints gbc_lblProtocol = new GridBagConstraints();
		gbc_lblProtocol.anchor = GridBagConstraints.WEST;
		gbc_lblProtocol.insets = new Insets(5, 5, 5, 5);
		gbc_lblProtocol.gridx = 0;
		gbc_lblProtocol.gridy = 0;
		contentPane.add(lblProtocol, gbc_lblProtocol);

		cmb_protocol = new JComboBox<String>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		contentPane.add(cmb_protocol, gbc_comboBox);
		cmb_protocol.addItem("tcp");
		cmb_protocol.addItem("http");

		JLabel lblNewLabel = new JLabel("Nome:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		txt_name = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 3;
		contentPane.add(txt_name, gbc_textField);
		txt_name.setColumns(10);

		lblPort = new JLabel("Porta:");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.insets = new Insets(0, 5, 5, 5);
		gbc_lblPorta.anchor = GridBagConstraints.WEST;
		gbc_lblPorta.gridx = 0;
		gbc_lblPorta.gridy = 4;
		contentPane.add(lblPort, gbc_lblPorta);

		txt_port = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 5;
		contentPane.add(txt_port, gbc_textField_1);
		txt_port.setColumns(5);

		lblCaminhoApp = new JLabel("Caminho App:");
		GridBagConstraints gbc_lblCaminhoApp = new GridBagConstraints();
		gbc_lblCaminhoApp.anchor = GridBagConstraints.WEST;
		gbc_lblCaminhoApp.insets = new Insets(0, 5, 5, 5);
		gbc_lblCaminhoApp.gridx = 0;
		gbc_lblCaminhoApp.gridy = 6;
		contentPane.add(lblCaminhoApp, gbc_lblCaminhoApp);

		txt_appPath = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 2;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 0;
		gbc_textField_3.gridy = 7;
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

				if (txt_port.getText().equals("")) {
					UserOutUtils.showPopUP("Por favor, insira uma porta!",
							AppInstallFrame.this);
					return;
				}
				
				String appPath = txt_appPath.getText().trim();
				String selected_protocol = cmb_protocol.getSelectedItem().toString();
				
				if (selected_protocol.equals("http") && appPath.equals("")) {
					UserOutUtils.showPopUP("Por favor, insira um caminho para a aplciação!",
							AppInstallFrame.this);
					return;
				}
				
				if (appPath.equals("")) {
					appPath = "/";
				}
				
				Application app = new Application();

				app.setName(txt_name.getText());
				app.setDescription("default description");
				app.setUrl(appPath);
				app.setPort(txt_port.getText());
				app.setProtocol(cmb_protocol.getSelectedItem().toString());
				
				ApplicationList.getInstance().installApp(app);
				AppInstallFrame.this.setVisible(false);

			}
		});
		GridBagConstraints gbc_btnInstalar = new GridBagConstraints();
		gbc_btnInstalar.insets = new Insets(10, 0, 10, 0);
		gbc_btnInstalar.gridx = 1;
		gbc_btnInstalar.gridy = 8;
		contentPane.add(btnInstalar, gbc_btnInstalar);

	}

	public void cleanValues() {
		txt_appPath.setText("");
		txt_port.setText("");
		txt_name.setText("");
		this.path_name = "";
	}

}
