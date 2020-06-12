package pl.mwprojects.pmapp.team;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.mwprojects.pmapp.personDetails.PersonDetails;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.project.Project;
import pl.mwprojects.pmapp.project.ProjectService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final PersonDetailsService personDetailsService;
    private final ProjectService projectService;

    public TeamController(TeamService teamService, PersonDetailsService personDetailsService, ProjectService projectService) {
        this.teamService = teamService;
        this.personDetailsService = personDetailsService;
        this.projectService = projectService;
    }

    @ModelAttribute(name = "allPeople")
    public List<PersonDetails> allPeople(){
        return personDetailsService.findAllPeopleInAlphabeticalOrder();
    }

    @ModelAttribute(name = "allProjects")
    public List<Project> allProjects(){
        return projectService.findAllProjectsOrderedByProjectNumberASC();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String teamRegistrationForm(Model model){
        model.addAttribute("team", new Team());
        return "teamRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processTeamRegistrationForm(@ModelAttribute(name = "team") @Validated Team team, BindingResult bindingResult, @RequestParam("fileTeam") MultipartFile file) throws IOException {
        if(bindingResult.hasErrors()){
            return "teamRegistrationForm";
        }
        Optional<Team> optionalTeam = teamService.findTeamByTeamName(team.getTeamName());
        if(optionalTeam.isPresent()){
            bindingResult.rejectValue("teamName", "error.teamName", "Zespół o takiej nazwie już istnieje!");
            return "teamRegistrationForm";
        }
        if(!file.isEmpty() && file != null) {
            byte[] bytes = file.getBytes();
            byte[] encodeBase64 = Base64.encodeBase64(bytes);
            String base64Encoded = new String(encodeBase64, "UTF-8");
            team.setImage(base64Encoded);
        }
        teamService.saveTeam(team);
        return "redirect:/";
    }
}
