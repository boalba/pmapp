package pl.mwprojects.pmapp.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.mwprojects.pmapp.personDetails.PersonDetails;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.user.User;
import pl.mwprojects.pmapp.user.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("currentUser")
public class DashboardController {

    private final UserService userService;
    private final PersonDetailsService personDetailsService;

    public DashboardController(UserService userService, PersonDetailsService personDetailsService) {
        this.userService = userService;
        this.personDetailsService = personDetailsService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String dashboard(Principal principal, Model model){
        Optional<Principal> optionalPrincipal = Optional.ofNullable(principal);
        if(optionalPrincipal.isPresent()){
            String userEmail = optionalPrincipal.get().getName();
            Optional<User> currentUser = userService.findUserByEmail(userEmail);
            if(currentUser.isPresent()) {
                model.addAttribute("currentUser", currentUser.get());
            }
            Optional<PersonDetails> personDetails = personDetailsService.findPersonDetailsById(currentUser.get().getId());
            if(personDetails.isPresent()){
                model.addAttribute("currentPerson", personDetails.get());
            }
        }
        return "dashboard";
    }
}
