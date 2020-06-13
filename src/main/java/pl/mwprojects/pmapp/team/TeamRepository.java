package pl.mwprojects.pmapp.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mwprojects.pmapp.project.Project;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Optional<Team> findTeamByTeamName(String teamName);

    @Query(value = "SELECT * FROM teams ORDER BY team_name ASC",
            nativeQuery = true)
    List<Team> findAllTeamsOrderedByTeamNameASC();

}
