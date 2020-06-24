package pl.mwprojects.pmapp.user;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mwprojects.pmapp.assignment.AssignmentService;
import pl.mwprojects.pmapp.confirmation.ConfirmationToken;
import pl.mwprojects.pmapp.confirmation.ConfirmationTokenService;
import pl.mwprojects.pmapp.emailSender.EmailSenderService;
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
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;
    private final AssignmentService assignmentService;

    public UserController(UserService userService, RoleService roleService, ProjectService projectService, PersonDetailsService personDetailsService, TeamService teamService, ConfirmationTokenService confirmationTokenService, EmailSenderService emailSenderService, AssignmentService assignmentService) {
        this.userService = userService;
        this.roleService = roleService;
        this.projectService = projectService;
        this.personDetailsService = personDetailsService;
        this.teamService = teamService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
        this.assignmentService = assignmentService;
    }

    @ModelAttribute(name = "roles")
    public List<Role> roles(){
        return roleService.findAllRoles();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String userRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "user/userRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processUserRegistrationForm(@ModelAttribute(name = "user") @Validated(AddUserConstrain.class) User user, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "user/userRegistrationForm";
        }
        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            bindingResult.rejectValue("email", "error.user", "Użytkownik o takim email już istnieje!");
            return "user/userRegistrationForm";
        }
        userService.saveUser(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenService.saveToken(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Dokończ rejestrację w aplikacji PM Application!");
        mailMessage.setFrom("maciek.wyzykowski@gmail.com");
        mailMessage.setText("Aby dokończyć rejestrację w aplikacji PM Application kliknij poniższy link: (Maciek RZĄDZI!!! ;) " + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken() + "&email=" + user.getEmail());

        emailSenderService.sendEmail(mailMessage);

        return "confirmation/successfulRegistration";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable Long id){
        Optional<User> currentUser = userService.findUserById(id);
        if(currentUser.isPresent()){
            model.addAttribute("user", currentUser.get());
        }
        return "user/userEditForm";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditUserForm(@ModelAttribute(name = "user") @Validated(EditUserConstrain.class) User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "user/userEditForm";
        }
        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            bindingResult.rejectValue("email", "error.user", "Użytkownik o takim email już istnieje!");
            return "user/userEditForm";
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
        return "user/userPasswordEditForm";
    }

    @RequestMapping(value = "/editPass/{id}", method = RequestMethod.POST)
    public String processEditUserPasswordForm(@ModelAttribute(name = "user") @Validated(EditUserPasswordConstrain.class) User user, BindingResult bindingResult, @RequestParam(name = "passwordRepeat") String passwordRepeat){
        if(bindingResult.hasErrors()){
            return "user/userPasswordEditForm";
        }
        if(!user.getPassword().equals(passwordRepeat)){
            bindingResult.rejectValue("password", "error.password", "Hasła nie są takie same!");
            return "user/userPasswordEditForm";
        }
        Optional<User>baseUser = userService.findUserById(user.getId());
        userService.saveEditUserPassword(user, baseUser.get());
        return "redirect:/person/allPeople";
    }

    @RequestMapping(value = "/editOwnPass", method = RequestMethod.GET)
    public String editOwnPassword(Model model){
        model.addAttribute("user", new User());
        return "user/userPasswordEditForm";
    }

    @RequestMapping(value = "/editOwnPass", method = RequestMethod.POST)
    public String processEditOwnPasswordForm(@ModelAttribute(name = "user") @Validated(EditUserPasswordConstrain.class) User user, BindingResult bindingResult, @RequestParam(name = "passwordRepeat") String passwordRepeat, Principal principal){
        if(bindingResult.hasErrors()){
            return "user/userPasswordEditForm";
        }
        if(!user.getPassword().equals(passwordRepeat)){
            bindingResult.rejectValue("password", "error.password", "Hasła nie są takie same!");
            return "user/userPasswordEditForm";
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
            teamService.deleteTeamLeaderFromTeamByUserId(id);
            assignmentService.deleteUserFromAssignmentByUserId(id);
            userService.deleteUser(currentUser.get());
        }
        return "redirect:/person/allPeople";
    }
}
