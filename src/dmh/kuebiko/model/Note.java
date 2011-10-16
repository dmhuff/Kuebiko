/**
 * Kuebiko - Note.java
 * Copyright 2011 Dave Huffman (daveh303 at yahoo dot com).
 * TODO license info.
 */

package dmh.kuebiko.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Value object representing a note.
 *
 * @author davehuffman
 */
public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Enumeration of possible entity states.
     */
    public enum State {
        /** The entity is an exact and unsullied representation of the data it 
         *  represents in the data store. */
        CLEAN, 
        /** The entity has been marked for deletion. */
        DELETED, 
        /** The entity contains data that does not exist in the data store. */
        DIRTY, 
        /** The entity has been partially loaded and does not contain all of the
         *  data in the data store. */
        HOLLOW, 
        /** The entity does not exist in the data store. */
        NEW; 
    }

    private transient NoteTextLazyLoader loader;

    private final int id;
    
    private State state;
    
    private String title;
    private String text;
    private Date createDate;
    private Date modifiedDate;
    
    private List<String> tags = Lists.newArrayList();
    
    /**
     * Create a new, unsaved note with default values.
     */
    public Note() {
        this(0, State.NEW);
        assignDefaults();
    }
    
    Note(int id, String title, Date createDate, Date modifiedDate, NoteTextLazyLoader loader) {
        this(id, State.HOLLOW);
        this.text = null;
        
        this.title = title;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.loader = loader;
    }

    Note(int id, String title, String text, Date createDate, Date modifiedDate) {
        this(id, State.CLEAN);
        
        this.title = title;
        this.text = text;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
    
    /**
     * Copy constructor.
     * @param source The note to copy.
     */
    Note(Note source) {
        this(source.getId(), source.getTitle(), source.getText(), 
                source.getCreateDate(), source.getModifiedDate());
    }

    /**
     * Copy constructor.
     * @param id The ID of the new note. 
     * @param source The note to copy.
     */
    Note(int id, Note source) {
        this(id, source.getTitle(), source.getText(), source.getCreateDate(), 
                source.getModifiedDate());
    }

    private Note(int id, State state) {
        this.id = id;
        this.state = state;
    }
    
    private void assignDefaults() {
        this.createDate = new Date();
    }
    
    /**
     * Reset the dirty flag on this note, which signifies that it's data is 
     * consistent with the data store. This method should only be called from 
     * the model layer.
     */
    void reset() {
        state = isLazy()? State.HOLLOW : State.CLEAN;
    }
    
    /**
     * @return True if this entity supports lazy loading.
     */
    boolean isLazy() {
        return (loader != null);
    }

    public boolean isNew() {
        return (id == 0 || state == State.NEW);
    }
    
    public boolean isClean() {
        return state == State.CLEAN;
    }
    
    public boolean isDirty() {
        return state == State.DIRTY;
    }
    
    /**
     * @return True if this note is {@link Note.State#HOLLOW}.
     */
    boolean isHollow() {
        return state == State.HOLLOW;
    }

    @Override
    public String toString() {
        return "Note [state=" + state + ", id=" + id + ", title=" + title
                + ", text=" + text + ", createDate=" + createDate
                + ", modifiedDate=" + modifiedDate + ", tags=" + tags + "]";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((createDate == null) ? 0 : createDate.hashCode());
        result = prime * result + id;
        result = prime * result
                + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Note other = (Note) obj;
        if (createDate == null) {
            if (other.createDate != null)
                return false;
        } else if (!createDate.equals(other.createDate))
            return false;
        if (id != other.id)
            return false;
        if (modifiedDate == null) {
            if (other.modifiedDate != null)
                return false;
        } else if (!modifiedDate.equals(other.modifiedDate))
            return false;
        if (state != other.state)
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }
    
    /**
     * Mark this note as dirty.
     */
    private void markAsDirty() {
        if (isHollow()) {
            throw new IllegalStateException(
                    "Hollow entities cannot be marked as dirty.");
        }
        
        if (state != State.NEW) {
            state = State.DIRTY;
        }
    }
    
    public State getState() {
        return state;
    }

    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        markAsDirty();
        this.title = title;
    }

    public String getText() {
        if (isHollow()) {
            try {
                text = loader.loadText(this);
            } catch (PersistenceException e) {
                throw new RuntimeException(e); // XXX determine what to do with this exception.
            }
            state = State.CLEAN;
        }
        return text;
    }

    public void setText(String text) {
        if (isHollow()) {
            throw new IllegalStateException("Note is hollow.");
        }
        
        markAsDirty();
        this.text = text;
    }

    public Date getCreateDate() {
        return createDate;
    }
    
    void setCreateDate(Date createDate) {
        markAsDirty();
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    void setModifiedDate(Date modifiedDate) {
        markAsDirty();
        this.modifiedDate = modifiedDate;
    }
    
    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }
    
    public void setTags(List<String> tags) {
        markAsDirty();
        this.tags = tags;
    }
}
