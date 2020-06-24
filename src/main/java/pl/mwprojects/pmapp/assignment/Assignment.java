package pl.mwprojects.pmapp.assignment;

import org.springframework.format.annotation.DateTimeFormat;
import pl.mwprojects.pmapp.project.Project;
import pl.mwprojects.pmapp.team.Team;
import pl.mwprojects.pmapp.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String assignmentName;

    @NotNull
    @NotEmpty
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignmentCreated;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate assignmentStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate assignmentStop;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @ManyToOne
    private Project project;

    public Assignment() {
        assignmentCreated = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAssignmentCreated() {
        return assignmentCreated;
    }

    public void setAssignmentCreated(LocalDate assignmentCreated) {
        this.assignmentCreated = assignmentCreated;
    }

    public LocalDate getAssignmentStart() {
        return assignmentStart;
    }

    public void setAssignmentStart(LocalDate assignmentStart) {
        this.assignmentStart = assignmentStart;
    }

    public LocalDate getAssignmentStop() {
        return assignmentStop;
    }

    public void setAssignmentStop(LocalDate assignmentStop) {
        this.assignmentStop = assignmentStop;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
