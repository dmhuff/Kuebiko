package com.jcap.view;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.lang.ObjectUtils;

import com.google.common.base.Joiner;
import com.jcap.model.Note;
import com.jcap.model.NoteDao;
import com.jcap.model.NoteDaoDevelopment;
import com.jcap.view.NoteTableModel.Column;

public class NoteTable extends JTable {
    private static final long serialVersionUID = 1L;
    
    private static class DateTimeCellRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public void setValue(Object value) {
            setText((value == null)? "" : DateFormat.getDateTimeInstance().format(value));
        }
    }
    
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
    
    private static class TagCellEditor extends DefaultCellEditor {
        private static final long serialVersionUID = 1L;

        private static final TagCellEditor  INSTANCE = new TagCellEditor();

        public TagCellEditor() {
            super(new JTextField());
        }

        @Override
        public Object getCellEditorValue() {
//            System.out.println("getCellEditorValue() #=> " + super.getCellEditorValue());
            Object cellEditorValue = super.getCellEditorValue();
            return Arrays.asList(ObjectUtils.toString(cellEditorValue).split(" "));
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
//            System.out.printf("getTableCellEditorComponent(table=[%s], "
//                + "value = [%s], isSelected = [%s], row = [%s], column = [%s])%n", 
//                table.getClass(), value, isSelected, row, column);
            
            List<?> tagList = (List<?>) value;
            
            String tags = Joiner.on(" ").join(tagList);
            
            return super.getTableCellEditorComponent(table, tags, isSelected, row, column);
        }
    }

    public NoteTable() {
        NoteDao noteDao = new NoteDaoDevelopment();
        final List<Note> searchNotes = noteDao.readNotes();
        NoteTableModel noteTableModel = new NoteTableModel(searchNotes);
        setModel(noteTableModel);
        
        setDefaultRenderer(Date.class, new DateTimeCellRenderer());
    }
    
    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        if (column == Column.TAGS.ordinal()) {
            return TagCellRenderer.INSTANCE;
        }
        return super.getCellRenderer(row, column);
    }
    
    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        if (column == Column.TAGS.ordinal()) {
            return TagCellEditor.INSTANCE;
        }
        return super.getCellEditor(row, column);
    }
}
