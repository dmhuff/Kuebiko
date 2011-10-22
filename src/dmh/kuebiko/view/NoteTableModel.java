/**
 * Kuebiko - NoteTableModel.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dmh.kuebiko.controller.NoteManager;
import dmh.kuebiko.model.Note;

/**
 * Model for the UI note table.
 *
 * @author davehuffman
 */
public class NoteTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    
    /** Enumeration of all columns supported by this table model.  
     *  TODO i18n. */
    public static enum Column {
        TITLE("Title"), 
//        TAGS("Tags"), 
        DATE_MODIFIED("Date Modified"); 
//        DATE_CREATED("Date Created");
        
        private final String label;
        
        private Column(String label) {
            this.label = label;
        }
        
        public String getLabel() {
            return label;
        }
    }

    private final NoteManager noteMngr;
    
    public NoteTableModel(NoteManager noteMngr) {
        this.noteMngr = noteMngr;
    }
    
    private List<Note> getNotes() {
        return noteMngr.getNotes();
    }
    
    /**
     * Add a new note to the stack with a default title.
     * @return The title of the new note.
     */
    String addNewNote() {
        final String title = noteMngr.addNewNote();
        onNoteAdded();
        return title;
    }
    
    /**
     * Add a new note to the stack with a particular title.
     * @param title The new note's title.
     */
    void addNewNote(String title) {
        noteMngr.addNewNote(title);
        onNoteAdded();
    }

    /**
     * Handler for when a note is added to the stack.
     */
    private void onNoteAdded() {
        int newNoteRow = noteMngr.getNoteCount() - 1;
        fireTableRowsInserted(newNoteRow, newNoteRow);
    }
    
    void deleteNote(Note note) {
        noteMngr.deleteNote(note);
        fireTableDataChanged();
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
        return getNotes() == null? 0 : noteMngr.getNoteCount();
    }
    
    /**
     * @param rowIndex
     * @return
     */
    Note getNoteAtRow(int rowIndex) {
        return noteMngr.getNoteAt(rowIndex);
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (noteMngr.isEmpty()) {
            return null;
        }
        
        Note note = getNoteAtRow(row);
        switch (Column.values()[col]) {
        case TITLE:
            return note.getTitle();
//        case TAGS:
//            return note.getTags();
        case DATE_MODIFIED:
            return note.getModifiedDate();
//        case DATE_CREATED:
//            return note.getCreateDate();
        }
        
        throw new IllegalArgumentException(
                String.format("Invalid column index [%d].", col));
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
    
    @Override
    @SuppressWarnings("unchecked")
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        if (columnIndex == Column.TAGS.ordinal()) {
//            getNoteAtRow(rowIndex).setTags((List<String>) aValue);
//        } else {
            throw new IllegalArgumentException(String.format(
                    "Cell (row=[%s],col=[%s]) is not editable.", 
                    rowIndex, columnIndex));
//        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
//        return columnIndex == Column.TAGS.ordinal();
    }
}
