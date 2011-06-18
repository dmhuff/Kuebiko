/**
 * Kuebiko - NoteTableModel.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package com.jcap.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jcap.model.Note;

/**
 * Model for the UI note table.
 *
 * @author davehuffman
 */
public class NoteTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    // TODO i18n.
    public enum Column {
        TITLE("Title"), 
        TAGS("Tags"), 
        DATE_MODIFIED("Date Modified"), 
        DATE_CREATED("Date Created");
        
        private final String label;
        
        private Column(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
    }

    private final List<Note> notes;
    
    public NoteTableModel() {
        this(new ArrayList<Note>());
    }
    
    public NoteTableModel(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }
    
    @Override
    public String getColumnName(int col) {
        return Column.values()[col].getLabel();
    }

    @Override
    public int getRowCount() {
        return notes == null? 0 : notes.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (notes == null || notes.isEmpty()) {
            return null;
        }
        final Note note = notes.get(row);
        switch (col) {
        case 0:
            return note.getTitle();
        case 1:
            return note.getTags();
        case 2:
            return note.getModifiedDate();
        case 3:
            return note.getCreateDate();
        default:
            throw new IllegalArgumentException(
                    String.format("Invalid column index [%d].", col));
        }
    }
    
    /**
     * Retrieve the value in a particular cell.
     * @param row The cell's row index.
     * @param column The cell's column identifier.
     * @return The value of the requested cell, or null if none exists.
     */
    public Object getValueAt(int row, Column column) {
        return getValueAt(row, column.ordinal());
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        System.out.printf("setValueAt(aValue=[%s;%s], rowIndex=[%s], columnIndex=[%s])", 
//                aValue, aValue.getClass(), rowIndex, columnIndex);
        if (columnIndex == Column.TAGS.ordinal()) {
            notes.get(rowIndex).setTags((Collection<String>) aValue);
        } else {
            throw new IllegalArgumentException(String.format(
                    "Cell (row=[%s],col=[%s]) is not editable.", 
                    rowIndex, columnIndex));
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == Column.TAGS.ordinal();
    }
    
    /**
     * @return An immutable view of this model's notes.
     */
    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }
}
