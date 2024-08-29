package br.com.rodrigo.gestao_vagas.modules.company.controllers;


import br.com.rodrigo.gestao_vagas.modules.company.dtos.CreateJobDTO;
import br.com.rodrigo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rodrigo.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/jobs")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){

        try{
            var comp_id = request.getAttribute("company_id");

            var jobEntity = JobEntity.builder()
                    .benefits(createJobDTO.getBenefits())
                    .companyId(UUID.fromString(comp_id.toString()))
                    .description(createJobDTO.getDescription())
                    .level(createJobDTO.getLevel())
                    .build();

            var job = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(job);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }
}
