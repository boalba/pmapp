package pl.mwprojects.pmapp.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mwprojects.pmapp.role.Role;
import pl.mwprojects.pmapp.role.RoleService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
            return "personRegistrationForm";
        }
        userService.saveUser(user);
        return "redirect:/person/allPeople";
    }
}
