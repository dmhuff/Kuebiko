package com.jcap.junk;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import java.awt.Insets;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class AppWindowTest {

	private JFrame totallyGridbagfrm;
	private JTextField searchText;
	private JSplitPane splitPane;
	private JTextArea noteTextArea;
	private JTable noteTable;
	private JScrollPane noteScroll;
	private JScrollPane noteTableScroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindowTest window = new AppWindowTest();
					window.totallyGridbagfrm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindowTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		totallyGridbagfrm = new JFrame();
		totallyGridbagfrm.setTitle("Totally GridBag!");
		totallyGridbagfrm.setBounds(100, 100, 450, 300);
		totallyGridbagfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		totallyGridbagfrm.getContentPane().setLayout(gridBagLayout);
		
		searchText = new JTextField();
		GridBagConstraints gbc_searchText = new GridBagConstraints();
		gbc_searchText.insets = new Insets(0, 0, 5, 0);
		gbc_searchText.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchText.gridx = 0;
		gbc_searchText.gridy = 0;
		totallyGridbagfrm.getContentPane().add(searchText, gbc_searchText);
		searchText.setColumns(10);
		
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.gridheight = 3;
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		totallyGridbagfrm.getContentPane().add(splitPane, gbc_splitPane);
		
		noteScroll = new JScrollPane();
		splitPane.setRightComponent(noteScroll);
		
		noteTextArea = new JTextArea();
		noteScroll.setViewportView(noteTextArea);
		
		noteTableScroll = new JScrollPane();
		splitPane.setLeftComponent(noteTableScroll);
		
		noteTable = new JTable();
		noteTableScroll.setViewportView(noteTable);
	}

}
