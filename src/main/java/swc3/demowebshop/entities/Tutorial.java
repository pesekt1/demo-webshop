package swc3.demowebshop.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tutorials", schema = "swc3_webshop")
public class Tutorial {
    private long id;
    private String description;
    private Boolean published;
    private String title;

    //this constructor is for the testing suite because we need to create some objects
    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public Tutorial(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "published", nullable = true)
    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tutorial tutorial = (Tutorial) o;
        return id == tutorial.id && Objects.equals(description, tutorial.description) && Objects.equals(published, tutorial.published) && Objects.equals(title, tutorial.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, published, title);
    }
}
