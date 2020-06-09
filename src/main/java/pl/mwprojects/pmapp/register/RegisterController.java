package pl.mwprojects.pmapp.register;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mwprojects.pmapp.personDetails.PersonDetails;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.personDetails.PersonalDetailsRepository;
import pl.mwprojects.pmapp.role.Role;
import pl.mwprojects.pmapp.role.RoleRepository;
import pl.mwprojects.pmapp.user.User;
import pl.mwprojects.pmapp.user.UserRepository;
import pl.mwprojects.pmapp.user.UserService;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PersonalDetailsRepository personalDetailsRepository;
    private final PersonDetailsService personDetailsService;

    public RegisterController(UserService userService, RoleRepository roleRepository, UserRepository userRepository, PersonDetailsService personDetailsService, PersonalDetailsRepository personalDetailsRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.personDetailsService = personDetailsService;
        this.personalDetailsRepository = personalDetailsRepository;
    }

    @ModelAttribute(name = "roles")
    public List<Role> roles(){
        return roleRepository.findAll();
    }

    @ModelAttribute(name = "usersWithoutDetails")
    public List<User> usersWithoutDetails(){
        return userService.findUsersWithoutPersonDetails();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "userRegistrationForm";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
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
            return "redirect:/";
        }
    }
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String personRegistrationForm(Model model){
        model.addAttribute("personDetails", new PersonDetails());
        return "personRegistrationForm";
    }
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public String processPersonRegistrationForm(@ModelAttribute(name = "personDetails") @Validated PersonDetails personDetails, BindingResult bindingResult, @RequestParam("file") MultipartFile file) throws Exception{
        if(bindingResult.hasErrors()){
            return "personRegistrationForm";
        }
        if(personDetails.getUser() == null){
            bindingResult.rejectValue("user", "error.user", "Nie ma kogo dodać!");
            return "personRegistrationForm";
        }
            try{
                if(!file.isEmpty() && file != null) {
                    byte[] bytes = file.getBytes();
                    byte[] encodeBase64 = Base64.encodeBase64(bytes);
                    String base64Encoded = new String(encodeBase64, "UTF-8");
                    personDetails.setImage(base64Encoded);
                }
                personDetailsService.savePerson(personDetails);
                return "redirect:/";
            }catch(org.springframework.dao.DataIntegrityViolationException e){
                bindingResult.rejectValue("user", "error.user", "Pracownik o takim email już istnieje!");
                return "personRegistrationForm";
            }
    }
}