package br.com.rodrigo.gestao_vagas.modules.candidate.useCases;

import br.com.rodrigo.gestao_vagas.exceptions.UserFoundException;
import br.com.rodrigo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rodrigo.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.rodrigo.gestao_vagas.modules.candidate.dtos.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileUserUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId){
        var candidate = this.candidateRepository.findById(candidateId)
                .orElseThrow(()->{
                    throw new UserFoundException();
                });

        var response = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .id(candidate.getId())
                .name(candidate.getName())
                .build();

        return response;
    }

}
