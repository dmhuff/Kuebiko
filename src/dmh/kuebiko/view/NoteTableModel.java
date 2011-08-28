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
    
    /*private String genUniqueNoteTitle() { // TODO unit test.
        // Get a list of default note titles.
        final Collection<String> defaultTitles = 
                Collections2.filter(noteMngr.getNoteTitles(), 
                        new Predicate<String>(){
                            @Override
                            public boolean apply(String input) {
                                return input.startsWith(DEFAULT_NOTE_TITLE);
                            }
                        });
        
        if (defaultTitles.size() == 0) {
            return DEFAULT_NOTE_TITLE;
        }
        
        // Default note titles exist; work thought the list and create a new, 
        // unique default note title.
        Pattern titleRegex = Pattern.compile(DEFAULT_NOTE_TITLE + " (/d+)?");
        int maxSuffix = 1;
        for (String title: defaultTitles) {
            Matcher matcher = titleRegex.matcher(title);
            if (matcher.matches()) {
                int suffix = Integer.parseInt(matcher.group(1));
                if (suffix > maxSuffix) {
                    maxSuffix = suffix;
                }
            }
        }
        return String.format("%s %d", DEFAULT_NOTE_TITLE, maxSuffix + 1);
    }*/

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
        // XXX should getNotes() ever be null?
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
        final Note note = getNoteAtRow(row);
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
    
    @Override
    @SuppressWarnings("unchecked")
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        System.out.printf("setValueAt(aValue=[%s;%s], rowIndex=[%s], columnIndex=[%s])", 
//                aValue, aValue.getClass(), rowIndex, columnIndex);
        if (columnIndex == Column.TAGS.ordinal()) {
            getNoteAtRow(rowIndex).setTags((List<String>) aValue);
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
}
