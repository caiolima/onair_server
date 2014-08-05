package com.five.onair.server.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.GridBagLayout;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

import com.five.onair.server.delegates.AppListDelegate;
import com.five.onair.server.persistence.model.Application;
import com.five.onair.server.persistence.model.ApplicationList;
import com.five.onair.server.utils.FileUtils;
import com.five.onair.server.utils.UserOutUtils;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class InstalledAppFrame extends JFrame implements AppListDelegate {

	private JPanel contentPane;
	private TableModel model;
	private static InstalledAppFrame singleton;
	private JTable table;
	private ApplicationList list;

	public static InstalledAppFrame getInstance() {
		if (singleton == null) {
			singleton = new InstalledAppFrame();
		}

		return singleton;
	}

	/**
	 * Create the frame.
	 */
	private InstalledAppFrame() {

		setTitle("Aplica\u00E7\u00F5es instaladas");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		table = new JTable();

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 2;
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		contentPane.add(scrollPane, gbc_table);

		JButton btnAdd = new JButton("Adicionar...");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame frame = AppInstallFrame.getInstance();
				frame.setVisible(true);

			}
		});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.EAST;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 1;
		contentPane.add(btnAdd, gbc_btnAdd);

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int pos = table.getSelectedRow();
				if (pos >= 0) {
					ApplicationList list = ApplicationList.getInstance();
					Application app = list.get(pos);

					UnistallAppDialog dialog=UnistallAppDialog.getInstanceFor(app);
					dialog.setVisible(true);

				}
			}
		});
		GridBagConstraints gbc_btnRemover = new GridBagConstraints();
		gbc_btnRemover.gridx = 1;
		gbc_btnRemover.gridy = 1;
		contentPane.add(btnRemover, gbc_btnRemover);

		fillTable();
		ApplicationList.getInstance().registerToDelegate(this);
	}

	@Override
	public void applicationInstalled(ApplicationList appList, Application app) {
		fillTable();
	}

	@Override
	public void applicationRemoved(ApplicationList appList, Application app) {
		fillTable();
	}

	private void fillTable() {

		if (table == null)
			return;

		String[] columnNames = { "Nome", "protocolo", "Porta", "Caminho" };

		list = ApplicationList.getInstance();

		Object[][] table_value = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {

			Application app = list.get(i);

			table_value[i][0] = app.getName();
			table_value[i][1] = app.getProtocol();
			table_value[i][2] = app.getPort();
			table_value[i][3] = app.getInstalledLocation();
		}
		model = new DefaultTableModel(table_value, columnNames);
		table.setModel(model);
		
	}

}
