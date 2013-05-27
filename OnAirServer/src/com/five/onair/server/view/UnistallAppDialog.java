package com.five.onair.server.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import java.awt.Insets;
import javax.swing.JCheckBox;

import com.five.onair.server.persistence.model.Application;
import com.five.onair.server.persistence.model.ApplicationList;
import com.five.onair.server.utils.FileUtils;
import com.five.onair.server.utils.UserOutUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.io.File;

public class UnistallAppDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JCheckBox chk_deleteFlag;
	private static UnistallAppDialog singleton;
	private Application currentApplication;
	private JFrame owner;
	private boolean finished=false;
	private JLabel lblVocTemCerteza;

	public static UnistallAppDialog getInstanceFor(Application app) {
		if (singleton == null)
			singleton = new UnistallAppDialog(app);
		else
			singleton.setCurrentApplication(app);
		
		singleton.defaultValues();
		return singleton;
	}

	/**
	 * Create the dialog.
	 */
	private UnistallAppDialog(Application app) {
		setCurrentApplication(app);
		
		setResizable(false);
		setTitle("Excluir aplicativo");
		setBounds(100, 100, 450, 182);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel(
					new ImageIcon(
							UnistallAppDialog.class
									.getResource("/com/sun/java/swing/plaf/gtk/resources/gtk-dialog-warning-6.png")));

			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel.insets = new Insets(0, 20, 0, 15);
			gbc_lblNewLabel.gridheight = 2;
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			lblVocTemCerteza = new JLabel(
					"<html><body>Voc\u00EA tem certeza que deseja deletar a aplica\u00E7\u00E3o <b>"
							+ currentApplication.getName()
							+ "</b>?<br/>Esta operação não poderá ser desfeita!</body></html>");
			GridBagConstraints gbc_lblVocTemCerteza = new GridBagConstraints();
			gbc_lblVocTemCerteza.fill = GridBagConstraints.BOTH;
			gbc_lblVocTemCerteza.anchor = GridBagConstraints.WEST;
			gbc_lblVocTemCerteza.insets = new Insets(0, 0, 5, 0);
			gbc_lblVocTemCerteza.gridwidth = 2;
			gbc_lblVocTemCerteza.gridx = 1;
			gbc_lblVocTemCerteza.gridy = 0;
			contentPanel.add(lblVocTemCerteza, gbc_lblVocTemCerteza);
		}
		{
			chk_deleteFlag = new JCheckBox(
					"Deletar todo o conte\u00FAdo da aplica\u00E7\u00E3o");
			GridBagConstraints gbc_chk_deleteFlag = new GridBagConstraints();
			gbc_chk_deleteFlag.anchor = GridBagConstraints.WEST;
			gbc_chk_deleteFlag.gridx = 1;
			gbc_chk_deleteFlag.gridy = 1;
			contentPanel.add(chk_deleteFlag, gbc_chk_deleteFlag);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						ApplicationList list = ApplicationList.getInstance();
//						if (chk_deleteFlag.isSelected()) {
//							File folder = new File(currentApplication
//									.getInstalledLocation());
//							if (folder.exists()) {
//								boolean deleted = false;
//								if (!folder.delete()) {
//									deleted = FileUtils
//											.deleteDirectoryWithRoot(folder
//													.getAbsolutePath());
//								}
//								if (!deleted) {
//									UserOutUtils
//											.showPopUP(
//													"Ocorreu algum problema quando tentamos deletar o aplicativo!\nVerifique se ele está sendo usado por outro programa.",
//													UnistallAppDialog.this);
//									return;
//								}
//							}
//						}
						list.desinstallApp(currentApplication);
						
						setVisible(false);

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						UnistallAppDialog.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public Application getCurrentApplication() {
		return currentApplication;
	}

	public void setCurrentApplication(Application currentApplication) {
		this.currentApplication = currentApplication;
	}
	
	private void defaultValues(){
		chk_deleteFlag.setSelected(false);
		lblVocTemCerteza.setText("<html><body>Voc\u00EA tem certeza que deseja deletar a aplica\u00E7\u00E3o <b>"
							+ currentApplication.getName()
							+ "</b>?<br/>Esta operação não poderá ser desfeita!</body></html>");
	}

}
