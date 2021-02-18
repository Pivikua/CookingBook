package ua.pivik.testCB.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @autor Alexander Pivovarov
 */

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;
    private Date dateCeation;
    private Long parentId;
    private Long childId;

    public Recipe() {
    }

    public Recipe(String text, Date dateCeation, Long parentId, Long childId) {
        this.text = text;
        this.dateCeation = dateCeation;
        this.parentId = parentId;
        this.childId = childId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCeation() {
        return dateCeation;
    }

    public void setDateCeation(Date dateCeation) {
        this.dateCeation = dateCeation;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }
}
