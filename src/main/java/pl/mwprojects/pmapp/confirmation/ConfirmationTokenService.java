package pl.mwprojects.pmapp.confirmation;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public ConfirmationToken findByConfirmationToken(String confirmationToken){
        return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
    }

    public void saveToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }
}
