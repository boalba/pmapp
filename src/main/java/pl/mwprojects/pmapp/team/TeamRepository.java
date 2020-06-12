package pl.mwprojects.pmapp.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Optional<Team> findTeamByTeamName(String teamName);
}
