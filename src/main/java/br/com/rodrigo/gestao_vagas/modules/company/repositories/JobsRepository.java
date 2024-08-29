package br.com.rodrigo.gestao_vagas.modules.company.repositories;

import br.com.rodrigo.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobsRepository extends JpaRepository<JobEntity, UUID> {
}
