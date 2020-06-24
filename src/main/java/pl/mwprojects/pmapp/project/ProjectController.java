package pl.mwprojects.pmapp.project;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mwprojects.pmapp.personDetails.PersonDetails;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.team.Team;
import pl.mwprojects.pmapp.team.TeamService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("project")
public class ProjectController {

    private final PersonDetailsService personDetailsService;
    private final ProjectService projectService;
    private final TeamService teamService;

    public ProjectController(PersonDetailsService personDetailsService, ProjectService projectService, TeamService teamService) {
        this.personDetailsService = personDetailsService;
        this.projectService = projectService;
        this.teamService = teamService;
    }

    @ModelAttribute(name = "phaseList")
    public List<String> phases(){
        return Arrays.asList("WPK", "PB", "PP", "PW", "PPW");
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
    public String projectRegistrationForm(Model model){
        model.addAttribute("project", new Project());
        return "project/projectRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processProjectRegistrationForm(@ModelAttribute(name = "project") @Validated Project project, BindingResult bindingResult, @RequestParam("fileProject") MultipartFile file) throws IOException {
        if(bindingResult.hasErrors()){
            return "project/projectRegistrationForm";
        }
        Optional<Project> optionalProject = projectService.findProjectByProjectNumber(project.getProjectNumber());
        if(optionalProject.isPresent()){
            bindingResult.rejectValue("projectNumber", "error.projectNumber", "Projekt o takim numerze już istnieje!");
            return "project/projectRegistrationForm";
        }
        if(!file.isEmpty() && file != null) {
            byte[] bytes = file.getBytes();
            byte[] encodeBase64 = Base64.encodeBase64(bytes);
            String base64Encoded = new String(encodeBase64, "UTF-8");
            project.setImage(base64Encoded);
        }
        projectService.saveProject(project);
        return "redirect:/";
    }

    @RequestMapping(value = "/allProjects", method = RequestMethod.GET)
    public String showAllProjects(){
        return "project/allProjects";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchProject(Model model, @RequestParam(name = "projectNumber") Long projectNumber, @RequestParam(name = "hash") String hash, @RequestParam(name = "projectName") String projectName){
        model.addAttribute("searchedProjects", projectService.findAllByProjectNumberOrHashOrProjectNameOrderByProjectNumberAsc(projectNumber, hash, projectName));
        return "project/searchedProjects";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String showProjectDetails(Model model, @PathVariable Long id){
        Optional<Project> currentProject = projectService.findProjectById(id);
        if(currentProject.isPresent()) {
            model.addAttribute("currentProject", currentProject.get());
            model.addAttribute("peopleOnProject", personDetailsService.findAllPeopleByProjectId(currentProject.get().getId()));
            Optional<Team> optionalTeamByProject = teamService.findTeamByProjectsId(currentProject.get().getId());
            if (optionalTeamByProject.isPresent()) {
                model.addAttribute("currentProjectTeam", optionalTeamByProject.get());
            }
        }
        return "project/projectDetails";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editProject(Model model, @PathVariable Long id){
        Optional<Project> currentProject = projectService.findProjectById(id);
        if(currentProject.isPresent()) {
            model.addAttribute("project", currentProject.get());
        }
        return "project/projectRegistrationForm";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditProjectForm(@ModelAttribute(name = "project") @Validated Project project, BindingResult bindingResult, @RequestParam("fileProject") MultipartFile file) throws Exception{
        if(bindingResult.hasErrors()){
            return "project/projectRegistrationForm";
        }
        if(!file.isEmpty() && file != null) {
            byte[] bytes = file.getBytes();
            byte[] encodeBase64 = Base64.encodeBase64(bytes);
            String base64Encoded = new String(encodeBase64, "UTF-8");
            project.setImage(base64Encoded);
        }
        projectService.saveProject(project);
        return "redirect:/project/allProjects";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteProject(@PathVariable Long id){
        Optional<Project> currentProject = projectService.findProjectById(id);
        if(currentProject.isPresent()){
            projectService.deleteProjectFromUserByProjectId(currentProject.get().getId());
            projectService.deleteProjectFromTeamByProjectId(currentProject.get().getId());
            projectService.deleteProject(currentProject.get());
        }
        return "redirect:/project/allProjects";
    }
}
