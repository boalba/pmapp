package pl.mwprojects.pmapp.project;

import pl.mwprojects.pmapp.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private String hash;

    @NotNull
    @NotEmpty
    @ElementCollection(targetClass=String.class)
    private Set<String> phase = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

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

    public Set<String> getPhase() {
        return phase;
    }

    public void setPhase(Set<String> phase) {
        this.phase = phase;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
