package pl.mwprojects.pmapp.team;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mwprojects.pmapp.personDetails.PersonDetails;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.project.Project;
import pl.mwprojects.pmapp.project.ProjectService;

import javax.transaction.Transactional;
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

    @ModelAttribute(name = "peopleWithoutTeam")
    public List<PersonDetails> peopleWithoutTeam(){
        return personDetailsService.findAllPeopleWithoutTeamId();
    }

    @ModelAttribute(name = "projectsWithoutTeams")
    public List<Project> projectsWithoutTeams(){
        return projectService.findAllProjectsWithoutTeamId();
    }

    @ModelAttribute(name = "allProjects")
    public List<Project> allProjects(){
        return projectService.findAllProjectsOrderedByProjectNumberASC();
    }

    @ModelAttribute(name = "allTeams")
    public List<Team> allTeams(){
        return teamService.findAllTeamsOrderedByTeamNameASC();
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
        if(team.getUsers().contains(team.getTeamLeader())){
            bindingResult.rejectValue("users", "error.users", "Kierownik nie może być jednocześnie członkiem zespołu");
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

    @RequestMapping(value = "/allTeams", method = RequestMethod.GET)
    public String showAllTeams(){
        return "allTeams";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTeam(Model model, @PathVariable int id){
            teamService.editTeam(id, model, personDetailsService, projectService);
        return "teamRegistrationForm";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditTeamForm(@ModelAttribute(name = "team") @Validated Team team, BindingResult bindingResult, @RequestParam("fileTeam") MultipartFile file) throws Exception{
        if(bindingResult.hasErrors()){
            return "teamRegistrationForm";
        }
        if(!file.isEmpty() && file != null) {
            byte[] bytes = file.getBytes();
            byte[] encodeBase64 = Base64.encodeBase64(bytes);
            String base64Encoded = new String(encodeBase64, "UTF-8");
            team.setImage(base64Encoded);
        }
        teamService.saveTeam(team);
        return "redirect:/team/allTeams";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteTeam(@PathVariable int id){
        Optional<Team> currentTeam = teamService.findTeamById(id);
        if(currentTeam.isPresent()){
            teamService.deleteTeam(currentTeam.get());
        }
        return "redirect:/team/allTeams";
    }
}
