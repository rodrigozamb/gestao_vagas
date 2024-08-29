package br.com.rodrigo.gestao_vagas.modules.candidate.useCases;

import br.com.rodrigo.gestao_vagas.exceptions.UserFoundException;
import br.com.rodrigo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rodrigo.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity){

        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        var passwordEncoded = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(passwordEncoded);

        return this.candidateRepository.save(candidateEntity);
    }
}
