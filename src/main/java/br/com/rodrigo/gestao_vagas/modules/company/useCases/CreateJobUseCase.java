package br.com.rodrigo.gestao_vagas.modules.company.useCases;

import br.com.rodrigo.gestao_vagas.exceptions.UserFoundException;
import br.com.rodrigo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rodrigo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rodrigo.gestao_vagas.modules.company.repositories.JobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobsRepository jobsRepository;

    public JobEntity execute(JobEntity jobEntity){

        return this.jobsRepository.save(jobEntity);
    }

}

