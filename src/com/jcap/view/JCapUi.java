package com.jcap.view;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.google.common.collect.Lists;

public class JCapUi {
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
            @Override
            public void run() {
                try {
                    JCapUi window = new JCapUi();
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
    public JCapUi() {
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
        gridBagLayout.columnWidths = new int[] { 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0,
                Double.MIN_VALUE };
        totallyGridbagfrm.getContentPane().setLayout(gridBagLayout);

        searchText = new JTextField();
        AutoCompleteDecorator.decorate(searchText,
                Lists.newArrayList("Alpha", "Beta", "Gamma"), false);
        GridBagConstraints gbc_searchText = new GridBagConstraints();
        gbc_searchText.insets = new Insets(0, 0, 5, 0);
        gbc_searchText.fill = GridBagConstraints.HORIZONTAL;
        gbc_searchText.gridx = 0;
        gbc_searchText.gridy = 0;
        totallyGridbagfrm.getContentPane().add(searchText, gbc_searchText);
        searchText.setColumns(10);

        splitPane = new JSplitPane();
        splitPane.setBorder(null);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        GridBagConstraints gbc_splitPane = new GridBagConstraints();
        gbc_splitPane.gridheight = 4;
        gbc_splitPane.insets = new Insets(0, 0, 5, 0);
        gbc_splitPane.fill = GridBagConstraints.BOTH;
        gbc_splitPane.gridx = 0;
        gbc_splitPane.gridy = 1;
        totallyGridbagfrm.getContentPane().add(splitPane, gbc_splitPane);

        noteScroll = new JScrollPane();
        noteScroll.setBorder(null);
        splitPane.setRightComponent(noteScroll);

        noteTextArea = new JTextArea();
        noteTextArea.setLineWrap(true);
        noteTextArea.setTabSize(4);
        noteScroll.setViewportView(noteTextArea);

        noteTableScroll = new JScrollPane();
        splitPane.setLeftComponent(noteTableScroll);
        noteTable = new NoteTable();
        noteTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
//                int rowIndex = event.getFirstIndex();
                // TODO set text from note.
                noteTextArea.setText("SDFDSF");
                
            }});
        noteTableScroll.setViewportView(noteTable);
    }
}
