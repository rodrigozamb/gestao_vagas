package br.com.rodrigo.gestao_vagas.modules.candidate.controllers;

import br.com.rodrigo.gestao_vagas.exceptions.UserFoundException;
import br.com.rodrigo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rodrigo.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.rodrigo.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.rodrigo.gestao_vagas.modules.candidate.useCases.ProfileUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileUserUseCase profileUserUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity ){

        try{
            var result =  this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> getProfile(HttpServletRequest request){

        var companyId = request.getAttribute("candidate_id");
        try{
            var result =  this.profileUserUseCase.execute(UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
