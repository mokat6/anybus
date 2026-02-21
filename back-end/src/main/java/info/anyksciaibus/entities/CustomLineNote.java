package info.anyksciaibus.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CustomLineNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String noteKey;
    private String noteValue;





    public String getNoteKey() {
        return noteKey;
    }

    public void setNoteKey(String noteKey) {
        this.noteKey = noteKey;
    }

    public String getNoteValue() {
        return noteValue;
    }

    public void setNoteValue(String noteValue) {
        this.noteValue = noteValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
