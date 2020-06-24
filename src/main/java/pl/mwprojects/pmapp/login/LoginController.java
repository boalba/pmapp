package pl.mwprojects.pmapp.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        return "login/loginPage";
    }

    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String loginErrorPage(){
        return "login/loginErrorPage";
    }
}
