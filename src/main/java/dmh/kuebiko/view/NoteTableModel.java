/**
 * Kuebiko - NoteTableModel.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */

package dmh.kuebiko.view;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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

    /** Enumeration of all columns supported by this table model. */
    public static enum Column {
    	// Note: the Tags and Date Created columns were never fully implemented.

        TITLE("Title"),
        DATE_MODIFIED("Date Modified"),
        TAGS("Tags"),
        DATE_CREATED("Date Created");

        private final String label;

        private Column(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
    private static final Set<Column> displayColumns = EnumSet.of(
    		Column.TITLE, Column.DATE_MODIFIED);

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
    	return displayColumns.size();
    }

    @Override
    public String getColumnName(int col) {
        return Column.values()[col].getLabel();
    }

    @Override
    public int getRowCount() {
        return getNotes() == null? 0 : noteMngr.getNoteCount();
    }

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
        case TAGS:
            return note.getTags();
        case DATE_MODIFIED:
            return note.getModifiedDate();
        case DATE_CREATED:
            return note.getCreateDate();
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
    	// Eventually the plan was to allow users to edit note tags from the
    	// note table itself.
    	// return columnIndex == Column.TAGS.ordinal();

        return false;
    }
}
