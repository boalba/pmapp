package pl.mwprojects.pmapp.confirmation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mwprojects.pmapp.user.User;
import pl.mwprojects.pmapp.user.UserService;

import java.util.Optional;

@Controller
public class ConfirmationController {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;

    public ConfirmationController(ConfirmationTokenService confirmationTokenService, UserService userService) {
        this.confirmationTokenService = confirmationTokenService;
        this.userService = userService;
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken, @RequestParam("email") String email) {
        Optional<ConfirmationToken> token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        Optional<User> user = userService.findUserByEmail(email);

        if(token.isPresent() && user.isPresent()) {
                user.get().setEnabled(1);
                userService.saveUserAfterEmailConfirmation(user.get());
                return "successfulConfirmation";
        }else {
            return "failureConfirmation";
        }
    }
}
