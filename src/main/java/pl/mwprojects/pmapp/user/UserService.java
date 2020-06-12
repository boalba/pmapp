package pl.mwprojects.pmapp.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mwprojects.pmapp.role.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        userRepository.save(user);
    }

    public List<User> findUsersWithoutPersonDetails(){
        return userRepository.findUsersWithoutPersonalDetails();
    }

    public List<User> findAllUsersByProjectId(Long projectId){
        return userRepository.findAllUsersByProjectId(projectId);
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }
}
