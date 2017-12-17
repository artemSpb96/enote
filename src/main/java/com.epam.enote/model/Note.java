package com.epam.enote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "note")
@Getter
@Setter
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "notebook_id")
    private Notebook notebook;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "note_label",
        joinColumns = {@JoinColumn(name = "note_id")},
        inverseJoinColumns = {@JoinColumn(name = "label_id")}
    )
    private Set<Label> labels;

    public Note() {
        timestamp = LocalDateTime.now();
    }

    public Note(String content) {
        this();
    }
}
