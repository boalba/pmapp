package pl.mwprojects.pmapp.personDetails;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
