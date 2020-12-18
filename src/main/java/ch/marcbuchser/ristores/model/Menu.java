package ch.marcbuchser.ristores.model;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Indexed
public class Menu extends PanacheEntity {

    @FullTextField(analyzer = "name")
    @KeywordField(name = "menuTitle_sort", sortable = Sortable.YES, normalizer = "sort")
    public String menuTitle;

    @FullTextField(analyzer = "name")
    @KeywordField(name = "menuContent_sort", sortable = Sortable.YES, normalizer = "sort")
    public String menuContent;

    @FullTextField(analyzer = "name")
    @KeywordField(name = "menuDate_sort", sortable = Sortable.YES, normalizer = "sort")
    public String menuDate;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @IndexedEmbedded
    public List<Guest> guests;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }

        Menu other = (Menu) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public String dateString(){
        return menuDate.toString();
    }
}

