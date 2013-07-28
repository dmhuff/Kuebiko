/**
 * Kuebiko - NoteTable.java
 * Copyright 2013 Dave Huffman (dave dot huffman at me dot com).
 * Open source under the BSD 3-Clause License.
 */

package dmh.kuebiko.view;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;

import dmh.kuebiko.model.Note;
import dmh.kuebiko.view.NoteTableModel.Column;

/**
 * UI table component for displaying a list of notes.
 *
 * @author davehuffman
 */
public class NoteTable extends JTable {
    private static final long serialVersionUID = 1L;

    private static class DateTimeCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        private static final DateTimeCellRenderer INSTANCE = new DateTimeCellRenderer();

        @Override
        protected void setValue(Object value) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            super.setValue((value == null)? "" : formatter.format(value));
        }
    }

    // Tags were never fully implemented.
    @SuppressWarnings("unused")
	private static class TagCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        private static final TagCellRenderer INSTANCE = new TagCellRenderer();
        private static final Joiner JOINER = Joiner.on(", ");

        @Override
        protected void setValue(Object value) {
            List<?> tagList = (List<?>) value;
            super.setValue(JOINER.join(tagList));
        }
    }

    // Tags were never fully implemented.
    @SuppressWarnings("unused")
	private static class TagCellEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 1L;

        private static final TagCellEditor  INSTANCE = new TagCellEditor();

        public TagCellEditor() {
            super(new JTextField());
        }

        @Override
        public Object getCellEditorValue() {
            Object cellEditorValue = super.getCellEditorValue();
            return Arrays.asList(ObjectUtils.toString(cellEditorValue).split(" "));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
            List<?> tagList = (List<?>) value;
            String tags = Joiner.on(" ").join(tagList);

            return super.getTableCellEditorComponent(table, tags, isSelected, row, column);
        }
    }

    private final NoteTableModel noteTableModel;
    private final TableRowSorter<NoteTableModel> sorter;

    NoteTable(NoteTableModel noteTableModel) {
        this.noteTableModel = noteTableModel;
        setModel(noteTableModel);

        sorter = new TableRowSorter<NoteTableModel>(this.noteTableModel);
        setRowSorter(sorter);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (column == Column.DATE_MODIFIED.ordinal()) {
            return DateTimeCellRenderer.INSTANCE;
        }
        return super.getCellRenderer(row, column);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        return super.getCellEditor(row, column);
    }

    /**
     * Apply a filter to the table's, hiding all rows that don't match.
     * @param filterString The string to use as a filter.
     */
    void filter(String filterString) {
        // Short-circuit if we're clearing the filter.
        if (StringUtils.isBlank(filterString)) {
            sorter.setRowFilter(null);
            return;
        }

        final RowFilter<NoteTableModel, Integer> rowFilter;
        try {
            rowFilter = RowFilter.regexFilter(String.format(".*?%s.*",
            		Pattern.quote(filterString)), Column.TITLE.ordinal());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid filter string", e);
        }
        sorter.setRowFilter(rowFilter);
    }

    /**
     * Manually select a note in the table.
     * @param title The title of the note to select.
     */
    void selectNote(String title) {
        if (isEmpty(title)) {
            return;
        }

        // Attempt to find an existing note by title.
        final String trimTitle = title.trim();
        final int titleCol = convertColumnIndexToView(Column.TITLE.ordinal());
        for (int r = 0; r < getRowCount(); r++) {
            if (trimTitle.equals(getValueAt(r, titleCol))) {
                changeSelection(r, titleCol, false, false);
                return;
            }
        }
    }

    void deleteSelectedNote() {
        noteTableModel.deleteNote(getSelectedNote());
        clearSelection();
    }

    /**
     * @return The currently selected note, or null if there is no selection.
     */
    Note getSelectedNote() {
        final int viewSelectedRow = getSelectedRow();
        final int modelSelectedRow = (viewSelectedRow < 0)? -1 : convertRowIndexToModel(viewSelectedRow);
        return (modelSelectedRow < 0)? null : noteTableModel.getNoteAtRow(modelSelectedRow);
    }

    public NoteTableModel getNoteTableModel() {
        return noteTableModel;
    }
}
