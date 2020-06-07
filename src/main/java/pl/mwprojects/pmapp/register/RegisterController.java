package pl.mwprojects.pmapp.register;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mwprojects.pmapp.role.Role;
import pl.mwprojects.pmapp.role.RoleRepository;
import pl.mwprojects.pmapp.user.User;
import pl.mwprojects.pmapp.user.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class RegisterController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public RegisterController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute(name = "roles")
    public List<Role> roles(){
        return roleRepository.findAll();
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    public String userRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "userRegistrationForm";
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String processUserRegistrationForm(@ModelAttribute(name = "user") @Validated User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "userRegistrationForm";
        }
        Optional<User> optionalUser = Optional.ofNullable(userService.findUserByEmail(user.getEmail()));
        if(optionalUser.isPresent() && optionalUser.get().getEmail().equals(user.getEmail())){
            bindingResult.rejectValue("email", "error.user", "Użytkownik o takim email już istnieje!");
            return "userRegistrationForm";
        }else {
            userService.saveUser(user);
            return "redirect:/dashboard";
        }
    }
}
