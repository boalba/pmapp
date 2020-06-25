package pl.mwprojects.pmapp.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.mwprojects.pmapp.assignment.AssignmentService;
import pl.mwprojects.pmapp.personDetails.PersonDetails;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.project.ProjectService;
import pl.mwprojects.pmapp.team.Team;
import pl.mwprojects.pmapp.team.TeamService;
import pl.mwprojects.pmapp.user.User;
import pl.mwprojects.pmapp.user.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("currentUser")
public class DashboardController {

    private final UserService userService;
    private final PersonDetailsService personDetailsService;
    private final ProjectService projectService;
    private final TeamService teamService;
    private final AssignmentService assignmentService;

    public DashboardController(UserService userService, PersonDetailsService personDetailsService, ProjectService projectService, TeamService teamService, AssignmentService assignmentService) {
        this.userService = userService;
        this.personDetailsService = personDetailsService;
        this.projectService = projectService;
        this.teamService = teamService;
        this.assignmentService = assignmentService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String dashboard(Principal principal, Model model){
        Optional<Principal> optionalPrincipal = Optional.ofNullable(principal);
        if(optionalPrincipal.isPresent()){
            String userEmail = optionalPrincipal.get().getName();
            Optional<User> loggedUser = userService.findUserByEmail(userEmail);
            if(loggedUser.isPresent()) {
                model.addAttribute("loggedUser", loggedUser.get());
                model.addAttribute("loggedProjects", projectService.findAllProjectsByUserId(loggedUser.get().getId()));
                Optional<PersonDetails> loggedPersonDetails = personDetailsService.findPersonDetailsById(loggedUser.get().getId());
                if(loggedPersonDetails.isPresent()){
                    model.addAttribute("loggedPerson", loggedPersonDetails.get());
                }
                Optional<Team> loggedUserTeamByTeamLeader = teamService.findTeamByTeamLeaderId(loggedUser.get().getId());
                if(loggedUserTeamByTeamLeader.isPresent()){
                    model.addAttribute("loggedUserTeamByTeamLeader",loggedUserTeamByTeamLeader.get());
                }else{
                    Optional<Team> loggedUserTeamByUser = teamService.findTeamByUserId(loggedUser.get().getId());
                    if(loggedUserTeamByUser.isPresent()){
                        model.addAttribute("loggedUserTeamByUser", loggedUserTeamByUser.get());
                    }
                }
                model.addAttribute("loggedAssignments", assignmentService.findAllAssignmentsByUserIdOrderByAssignmentStopAsc(loggedUser.get().getId()));
            }
        }
        return "dashboard/dashboard";
    }
}
