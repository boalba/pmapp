package pl.mwprojects.pmapp.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(){
        return "loginPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        return "loginPage";
    }
}
