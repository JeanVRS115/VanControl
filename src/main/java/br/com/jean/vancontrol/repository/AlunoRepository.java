package br.com.jean.vancontrol.repository;

import br.com.jean.vancontrol.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByMotorista_CpfOrderByNomeAsc(String cpf);

    Optional<Aluno> findByIdAndMotorista_Cpf(Long id, String cpf);
}