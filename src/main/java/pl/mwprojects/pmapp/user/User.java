package pl.mwprojects.pmapp.user;

import pl.mwprojects.pmapp.role.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(groups = {AddUserConstrain.class, EditUserConstrain.class})
    @NotEmpty(groups = {AddUserConstrain.class, EditUserConstrain.class})
    @Email(groups = {AddUserConstrain.class, EditUserConstrain.class})
    @Column(unique = true)
    private String email;

    @NotNull(groups = {AddUserConstrain.class, EditUserPasswordConstrain.class})
    @NotEmpty(groups = {AddUserConstrain.class, EditUserPasswordConstrain.class})
    private String password;

    private int enabled;

    @ManyToOne
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}