package pl.mwprojects.pmapp.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.project.ProjectService;
import pl.mwprojects.pmapp.role.Role;
import pl.mwprojects.pmapp.role.RoleService;
import pl.mwprojects.pmapp.team.TeamService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final PersonDetailsService personDetailsService;
    private final TeamService teamService;

    public UserController(UserService userService, RoleService roleService, ProjectService projectService, PersonDetailsService personDetailsService, TeamService teamService) {
        this.userService = userService;
        this.roleService = roleService;
        this.projectService = projectService;
        this.personDetailsService = personDetailsService;
        this.teamService = teamService;
    }

    @ModelAttribute(name = "roles")
    public List<Role> roles(){
        return roleService.findAllRoles();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String userRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "userRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processUserRegistrationForm(@ModelAttribute(name = "user") @Validated User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "userRegistrationForm";
        }
        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            bindingResult.rejectValue("email", "error.user", "Użytkownik o takim email już istnieje!");
            return "userRegistrationForm";
        }else {
            user.setEnabled(1);
            userService.saveUser(user);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable Long id){
        Optional<User> currentUser = userService.findUserById(id);
        if(currentUser.isPresent()){
            currentUser.get().setPassword("");
            model.addAttribute("user", currentUser.get());
        }
        return "userRegistrationForm";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditUserForm(@ModelAttribute(name = "user") @Validated User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "userRegistrationForm";
        }
        userService.saveUser(user);
        return "redirect:/person/allPeople";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id){
        Optional<User> currentUser = userService.findUserById(id);
        if(currentUser.isPresent()){
            projectService.deleteUserFromProjectByUserId(id);
            personDetailsService.deleteUserFromPersonDetailsByUserId(id);
            teamService.deleteUserFromTeamByUserId(id);
            userService.deleteUser(currentUser.get());
        }
        return "redirect:/person/allPeople";
    }
}
