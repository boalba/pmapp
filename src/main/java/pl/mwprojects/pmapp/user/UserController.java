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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
    public String processUserRegistrationForm(@ModelAttribute(name = "user") @Validated(AddUserConstrain.class) User user, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "userRegistrationForm";
        }
        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            bindingResult.rejectValue("email", "error.user", "Użytkownik o takim email już istnieje!");
            return "userRegistrationForm";
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable Long id){
        Optional<User> currentUser = userService.findUserById(id);
        if(currentUser.isPresent()){
            model.addAttribute("user", currentUser.get());
        }
        return "userEditForm";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditUserForm(@ModelAttribute(name = "user") @Validated(EditUserConstrain.class) User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "userEditForm";
        }
        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            bindingResult.rejectValue("email", "error.user", "Użytkownik o takim email już istnieje!");
            return "userEditForm";
        }
        Optional<User>baseUser = userService.findUserById(user.getId());
        userService.saveEditUser(user, baseUser.get());
        return "redirect:/person/allPeople";
    }

    @RequestMapping(value = "/editPass/{id}", method = RequestMethod.GET)
    public String editUserPassword(Model model, @PathVariable Long id){
        Optional<User> currentUser = userService.findUserById(id);
        if(currentUser.isPresent()){
            currentUser.get().setPassword("");
            model.addAttribute("user", currentUser.get());
        }
        return "userPasswordEditForm";
    }

    @RequestMapping(value = "/editPass/{id}", method = RequestMethod.POST)
    public String processEditUserPasswordForm(@ModelAttribute(name = "user") @Validated(EditUserPasswordConstrain.class) User user, BindingResult bindingResult, @RequestParam(name = "passwordRepeat") String passwordRepeat){
        if(bindingResult.hasErrors()){
            return "userPasswordEditForm";
        }
        if(!user.getPassword().equals(passwordRepeat)){
            bindingResult.rejectValue("password", "error.password", "Hasła nie są takie same!");
            return "userPasswordEditForm";
        }
        Optional<User>baseUser = userService.findUserById(user.getId());
        userService.saveEditUserPassword(user, baseUser.get());
        return "redirect:/person/allPeople";
    }

    @RequestMapping(value = "/editOwnPass", method = RequestMethod.GET)
    public String editOwnPassword(Model model){
        model.addAttribute("user", new User());
        return "userPasswordEditForm";
    }

    @RequestMapping(value = "/editOwnPass", method = RequestMethod.POST)
    public String processEditOwnPasswordForm(@ModelAttribute(name = "user") @Validated(EditUserPasswordConstrain.class) User user, BindingResult bindingResult, @RequestParam(name = "passwordRepeat") String passwordRepeat, Principal principal){
        if(bindingResult.hasErrors()){
            return "userPasswordEditForm";
        }
        if(!user.getPassword().equals(passwordRepeat)){
            bindingResult.rejectValue("password", "error.password", "Hasła nie są takie same!");
            return "userPasswordEditForm";
        }
        Optional<Principal> optionalPrincipal = Optional.ofNullable(principal);
        if(optionalPrincipal.isPresent()) {
            Optional<User> baseUser = userService.findUserByEmail(optionalPrincipal.get().getName());
            userService.saveEditUserPassword(user, baseUser.get());
        }
        return "redirect:/";
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
