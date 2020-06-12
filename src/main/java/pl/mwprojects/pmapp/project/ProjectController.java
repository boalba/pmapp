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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("project")
public class ProjectController {

    private final PersonDetailsService personDetailsService;
    private final ProjectService projectService;

    public ProjectController(PersonDetailsService personDetailsService, ProjectService projectService) {
        this.personDetailsService = personDetailsService;
        this.projectService = projectService;
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
        return "projectRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processProjectRegistrationForm(@ModelAttribute(name = "project") @Validated Project project, BindingResult bindingResult, @RequestParam("fileProject") MultipartFile file) throws IOException {
        if(bindingResult.hasErrors()){
            return "projectRegistrationForm";
        }
        Optional<Project> optionalProject = Optional.ofNullable(projectService.findProjectByProjectNumber(project.getProjectNumber()));
        if(optionalProject.isPresent()){
            bindingResult.rejectValue("projectNumber", "error.projectNumber", "Projekt o takim numerze już istnieje!");
            return "projectRegistrationForm";
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
        return "allProjects";
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String showProjectDetails(Model model, @PathVariable Long id){
        model.addAttribute("currentProject", projectService.findProjectById(id));
        model.addAttribute("peopleOnProject", personDetailsService.findAllPeopleByProjectId(id));
        return "projectDetails";
    }
}
