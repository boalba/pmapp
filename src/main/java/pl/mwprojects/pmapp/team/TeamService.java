package pl.mwprojects.pmapp.team;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> findTeamByTeamName(String teamName){
        return teamRepository.findTeamByTeamName(teamName);
    }

    public void saveTeam(Team team){
        teamRepository.save(team);
    }

    public List<Team> findAllTeamsOrderedByTeamNameASC(){
        return teamRepository.findAllTeamsOrderedByTeamNameASC();
    }
}
