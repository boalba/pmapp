package pl.mwprojects.pmapp.assignment;

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

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String showAssignmentDetails(Model model, @PathVariable Long id){
        Optional<Assignment> currentAssignment = assignmentService.findAssignmentById(id);
        if(currentAssignment.isPresent()) {
            model.addAttribute("currentAssignment", currentAssignment.get());
            model.addAttribute("currentAssignmentPeople", personDetailsService.findAllPeopleOnAssignmentByAssignmentId(id));
        }
        return "assignment/assignmentDetails";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editAssignment(Model model, @PathVariable Long id){
        Optional<Assignment> currentAssignment = assignmentService.findAssignmentById(id);
        if(currentAssignment.isPresent()) {
            model.addAttribute("assignment", currentAssignment.get());
        }
        return "assignment/assignmentEditForm";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String processEditAssignmentForm(@PathVariable Long id, @ModelAttribute(name = "assignment") @Validated Assignment assignment, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "project/projectRegistrationForm";
        }
        Optional<Assignment> optionalAssignment = assignmentService.findAssignmentByAssignmentName(assignment.getAssignmentName());
        if(optionalAssignment.isPresent()){
            bindingResult.rejectValue("assignmentName", "error.assignmentName", "Zadanie o takiej nazwie już istnieje!");
            return "assignment/assignmentEditForm";
        }
        assignmentService.saveAssignment(assignment);
        return "redirect:/assignment/allAssignments";
    }


}
