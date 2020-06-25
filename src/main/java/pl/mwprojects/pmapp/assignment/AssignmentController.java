package pl.mwprojects.pmapp.assignment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mwprojects.pmapp.personDetails.PersonDetails;
import pl.mwprojects.pmapp.personDetails.PersonDetailsService;
import pl.mwprojects.pmapp.project.Project;
import pl.mwprojects.pmapp.project.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private final PersonDetailsService personDetailsService;
    private final ProjectService projectService;
    private final AssignmentService assignmentService;

    public AssignmentController(PersonDetailsService personDetailsService, ProjectService projectService, AssignmentService assignmentService) {
        this.personDetailsService = personDetailsService;
        this.projectService = projectService;
        this.assignmentService = assignmentService;
    }

    @ModelAttribute(name = "allPeople")
    public List<PersonDetails> allPeople(){
        return personDetailsService.findAllPeopleInAlphabeticalOrder();
    }

    @ModelAttribute(name = "allProjects")
    public List<Project> allProjects(){
        return projectService.findAllProjectsOrderedByProjectNumberASC();
    }

    @ModelAttribute(name = "allAssignments")
    public Map<Assignment, Project> allAssignments(){
        List<Assignment> allAssignments = assignmentService.findAllAssignmentsOrderByAssignmentStopAsc();
        List<Project> allProjects = projectService.findAllProjects();
        Map<Assignment, Project> allAssignmentsWithProjects = new HashMap<>();

        for(Assignment assignment : allAssignments){
            for(Project project : allProjects){
                if(assignment.getProject().equals(project)){
                    allAssignmentsWithProjects.put(assignment, project);
                }
            }
        }

        return allAssignmentsWithProjects;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String assignmentRegistrationForm(Model model){
        model.addAttribute("assignment", new Assignment());
        return "assignment/assignmentRegistrationForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processAssignmentRegistrationForm(@ModelAttribute(name = "assignment") @Validated Assignment assignment, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "assignment/assignmentRegistrationForm";
        }
        Optional<Assignment> optionalAssignment = assignmentService.findAssignmentByAssignmentName(assignment.getAssignmentName());
        if(optionalAssignment.isPresent()){
            bindingResult.rejectValue("assignmentName", "error.assignmentName", "Zadanie o takiej nazwie już istnieje!");
            return "assignment/assignmentRegistrationForm";
        }
        assignmentService.saveAssignment(assignment);
        return "redirect:/assignment/allAssignments";
    }

    @RequestMapping(value = "/allAssignments", method = RequestMethod.GET)
    public String showAllAssignments(){
        return "assignment/allAssignments";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchAssignments(Model model, @RequestParam(name = "assignmentName") String assignmentName, @RequestParam(name = "projectNumber") Long projectNumber, @RequestParam(name = "projectName") String projectName){
        List<Assignment> searchedAssignments = assignmentService.findAllByAssignmentNameOrProjectNumberOrProjectNameOrderByAssignmentStopAsc(assignmentName, projectNumber, projectName);
        List<Project> allProjects = projectService.findAllProjects();
        Map<Assignment, Project> allSearchedAssignmentsWithProjects = new HashMap<>();

        for(Assignment assignment : searchedAssignments){
            for(Project project : allProjects){
                if(assignment.getProject().equals(project)){
                    allSearchedAssignmentsWithProjects.put(assignment, project);
                }
            }
        }
        model.addAttribute("searchedAssignments", allSearchedAssignmentsWithProjects);
        return "assignment/searchedAssignments";
    }


}