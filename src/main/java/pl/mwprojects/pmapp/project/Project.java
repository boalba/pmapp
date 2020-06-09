//package pl.mwprojects.pmapp.project;
//
//import pl.mwprojects.pmapp.user.User;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "projects")
//public class Project {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    @NotNull
//    @NotEmpty
//    Long projectNumber;
//
//    @NotNull
//    @NotEmpty
//    String projectName;
//
//    @ManyToMany()
//    Set<User> users = new HashSet<>();
//
//
//}
