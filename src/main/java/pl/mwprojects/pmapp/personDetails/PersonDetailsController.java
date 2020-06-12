package pl.mwprojects.pmapp.personDetails;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mwprojects.pmapp.user.User;
import pl.mwprojects.pmapp.user.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonDetailsController {

    private final PersonDetailsService personDetailsService;
    private final UserService userService;

    public PersonDetailsController(PersonDetailsService personDetailsService, UserService userService) {
        this.personDetailsService = personDetailsService;
        this.userService = userService;
    }

    @ModelAttribute(name = "usersWithoutDetails")
    public List<User> usersWithoutDetails(){
        return userService.findUsersWithoutPersonDetails();
    }

    @ModelAttribute(name = "allPeople")
    public List<PersonDetails> allPeople(){
        return personDetailsService.findAllPeopleInAlphabeticalOrder();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String personRegistrationForm(Model model){
        model.addAttribute("personDetails", new PersonDetails());
        return "personRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processPersonRegistrationForm(@ModelAttribute(name = "personDetails") @Validated PersonDetails personDetails, BindingResult bindingResult, @RequestParam("filePerson") MultipartFile file) throws Exception{
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

    @RequestMapping(value = "/allPeople", method = RequestMethod.GET)
    public String showAllPeople(){
        return "allPeople";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String showPersonDetails(Model model, @PathVariable Long id){
        Optional<PersonDetails> currentPerson = personDetailsService.findPersonDetailsById(id);
        if(currentPerson.isPresent()) {
            model.addAttribute("currentPersonDetails", currentPerson.get());
        }
        return "personDetails";
    }
}