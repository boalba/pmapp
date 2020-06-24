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

    public Optional<Team> findTeamByUserId(Long userId){
        return teamRepository.findTeamByUserId(userId);
    }

    public Optional<Team> findTeamByTeamLeaderId(Long teamLeaderId){
        return teamRepository.findTeamByTeamLeaderId(teamLeaderId);
    }

    public Optional<Team> findTeamByProjectsId(Long projectId){
        return teamRepository.findTeamByProjectsId(projectId);
    }

    public Optional<Team> findTeamById(int teamId){
        return teamRepository.findById(teamId);
    }

    public void deleteTeam(Team team){
        teamRepository.delete(team);
    }

    public void deleteUserFromTeamByUserId(Long userId){
        teamRepository.deleteUserFromTeamByUserId(userId);
    }

    public void deleteTeamLeaderFromTeamByUserId(Long userId){
        teamRepository.deleteTeamLeaderFromTeamByUserId(userId);
    }

    public List<Team> findAllByTeamNameOrderByTeamNameAsc(String teamName){
        return teamRepository.findAllByTeamNameOrderByTeamNameAsc(teamName);
    }

}
