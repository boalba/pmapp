package pl.mwprojects.pmapp.personDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PersonalDetailsRepository extends JpaRepository<PersonDetails, Long> {
}
