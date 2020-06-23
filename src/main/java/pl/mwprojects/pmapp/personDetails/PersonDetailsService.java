package pl.mwprojects.pmapp.personDetails;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonDetailsService {

    private final PersonalDetailsRepository personalDetailsRepository;

    public PersonDetailsService(PersonalDetailsRepository personalDetailsRepository) {
        this.personalDetailsRepository = personalDetailsRepository;
    }

    public void savePerson(PersonDetails personDetails){
        personalDetailsRepository.save(personDetails);
    }

    public Optional<PersonDetails> findPersonDetailsById(Long id){
        return personalDetailsRepository.findById(id);
    }

    public List<PersonDetails> findAllPeopleInAlphabeticalOrder(){
        return personalDetailsRepository.findAllPeopleInAlphabeticalOrder();
    }

    public List<PersonDetails> findAllPeopleByProjectId(Long projectId){
        return personalDetailsRepository.findAllPeopleByProjectId(projectId);
    }

    public List<PersonDetails> findAllPeopleWithoutTeamId(){
        return personalDetailsRepository.findAllPeopleWithoutTeamId();
    }

    public void deleteUserFromPersonDetailsByUserId(Long userId){
        personalDetailsRepository.deleteUserFromPersonDetailsByUserId(userId);
    }

    public Optional<PersonDetails> findPersonDetailsOfCurrentTeamLeader(int teamId){
        return personalDetailsRepository.findPersonDetailsOfCurrentTeamLeader(teamId);
    }

    public List<PersonDetails> findAllPersonDetailsOfCurrentTeam(int teamId){
        return personalDetailsRepository.findAllPersonDetailsOfCurrentTeam(teamId);
    }

    public List<PersonDetails> findAllByFirstNameOrSureNameOrderBySureNameAsc(String firstName, String sureName){
        return  personalDetailsRepository.findAllByFirstNameOrSureNameOrderBySureNameAsc(firstName, sureName);
    }
}
