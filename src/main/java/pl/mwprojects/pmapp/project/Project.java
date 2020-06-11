package pl.mwprojects.pmapp.project;

import pl.mwprojects.pmapp.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long projectNumber;

    @NotNull
    @NotEmpty
    private String projectName;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 3)
    private String hash;

    @NotNull
    @NotEmpty
    @ElementCollection(targetClass=String.class)
    private Set<String> phases = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @Lob
    private String image;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Long projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Set<String> getPhases() {
        return phases;
    }

    public void setPhases(Set<String> phases) {
        this.phases = phases;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
