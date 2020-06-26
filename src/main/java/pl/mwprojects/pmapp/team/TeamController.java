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
        return "team/teamRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processTeamRegistrationForm(@ModelAttribute(name = "team") @Validated Team team, BindingResult bindingResult, @RequestParam("fileTeam") MultipartFile file) throws IOException {
        if(bindingResult.hasErrors()){
            return "team/teamRegistrationForm";
        }
        Optional<Team> optionalTeam = teamService.findTeamByTeamName(team.getTeamName());
        if(optionalTeam.isPresent()){
            bindingResult.rejectValue("teamName", "error.teamName", "Zespół o takiej nazwie już istnieje!");
            return "team/teamRegistrationForm";
        }
        if(team.getUsers().contains(team.getTeamLeader())){
            bindingResult.rejectValue("users", "error.users", "Kierownik nie może być jednocześnie członkiem zespołu");
            return "team/teamRegistrationForm";
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

    @RequestMapping(value = "/allTeams", method = RequestMethod.GET)
    public String showAllTeams(){
        return "team/allTeams";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchTeam(Model model, @RequestParam(name = "teamName") String teamName){
        model.addAttribute("searchedTeams", teamService.findAllByTeamNameOrderByTeamNameAsc(teamName));
        return "team/searchedTeams";
    }


    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String showTeamDetails(Model model, @PathVariable int id){
        Optional<Team> currentTeam = teamService.findTeamById(id);
        if(currentTeam.isPresent()) {
            model.addAttribute("currentTeam", currentTeam.get());
            model.addAttribute("currentTeamProjects", projectService.findAllProjectsOfCurrentTeam(currentTeam.get().getId()));
            if(currentTeam.get().getTeamLeader() != null) {
                model.addAttribute("personDetailsOfTeamLeader", personDetailsService.findPersonDetailsOfCurrentTeamLeader(currentTeam.get().getId()).get());
            }
            model.addAttribute("personDetailsOfTeam", personDetailsService.findAllPersonDetailsOfCurrentTeam(currentTeam.get().getId()));
        }
        return "team/teamDetails";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTeam(Model model, @PathVariable int id) {
        Optional<Team> currentTeam = teamService.findTeamById(id);
        if(currentTeam.isPresent()) {
            model.addAttribute("team", currentTeam);
            Optional<PersonDetails> currentTeamLeader = personDetailsService.findPersonDetailsOfCurrentTeamLeader(id);
            if(currentTeamLeader.isPresent()){
                model.addAttribute("currentTeamLeader", currentTeamLeader.get());
            }
            model.addAttribute("peopleOfCurrentTeam", personDetailsService.findAllPersonDetailsOfCurrentTeam(id));
            model.addAttribute("projectsOfCurrentTeam", projectService.findAllProjectsOfCurrentTeam(id));
        }
        return "team/teamEditForm";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditTeamForm(Model model, @PathVariable int id, @ModelAttribute(name = "team") @Validated Team team, BindingResult bindingResult, @RequestParam("fileTeam") MultipartFile file) throws Exception{
        Optional<PersonDetails> currentTeamLeader = personDetailsService.findPersonDetailsOfCurrentTeamLeader(id);
        if(bindingResult.hasErrors()){
            if(currentTeamLeader.isPresent()){
                model.addAttribute("currentTeamLeader", currentTeamLeader.get());
            }
            model.addAttribute("peopleOfCurrentTeam", personDetailsService.findAllPersonDetailsOfCurrentTeam(id));
            model.addAttribute("projectsOfCurrentTeam", projectService.findAllProjectsOfCurrentTeam(id));
            return "team/teamEditForm";
        }
        if(team.getUsers().contains(team.getTeamLeader())){
            bindingResult.rejectValue("users", "error.users", "Kierownik nie może być jednocześnie członkiem zespołu");
            if(currentTeamLeader.isPresent()){
                model.addAttribute("currentTeamLeader", currentTeamLeader.get());
            }
            model.addAttribute("peopleOfCurrentTeam", personDetailsService.findAllPersonDetailsOfCurrentTeam(id));
            model.addAttribute("projectsOfCurrentTeam", projectService.findAllProjectsOfCurrentTeam(id));
            return "team/teamEditForm";
        }
        if(!file.isEmpty() && file != null) {
            byte[] bytes = file.getBytes();
            byte[] encodeBase64 = Base64.encodeBase64(bytes);
            String base64Encoded = new String(encodeBase64, "UTF-8");
            team.setImage(base64Encoded);
        }else{
        Optional<Team> oldTeam = teamService.findTeamById(id);
            if(oldTeam.isPresent()) {
                team.setImage(oldTeam.get().getImage());
            }
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
