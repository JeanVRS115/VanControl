package br.com.jean.vancontrol.service;

import br.com.jean.vancontrol.model.Aluno;
import br.com.jean.vancontrol.model.Motorista;
import br.com.jean.vancontrol.repository.AlunoRepository;
import br.com.jean.vancontrol.repository.MotoristaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final MotoristaRepository motoristaRepository;

    public AlunoService(
            AlunoRepository alunoRepository,
            MotoristaRepository motoristaRepository
    ) {
        this.alunoRepository = alunoRepository;
        this.motoristaRepository = motoristaRepository;
    }

    public Aluno cadastrar(
            String cpfMotoristaLogado,
            String nome,
            String cpf,
            String nomeResponsavel1,
            String cpfResponsavel1,
            String nomeResponsavel2,
            String cpfResponsavel2
    ) {
        String cpfMotorista = normalizarCpf(cpfMotoristaLogado);
        String cpfAluno = normalizarCpf(cpf);
        String cpfResp1 = normalizarCpf(cpfResponsavel1);
        String cpfResp2 = normalizarCpf(cpfResponsavel2);

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }

        validarCpf(cpfAluno, "O CPF do aluno deve ter 11 dígitos.");
        validarResponsavel(nomeResponsavel1, cpfResp1, "responsável 1");
        validarResponsavel(nomeResponsavel2, cpfResp2, "responsável 2");

        if ((nomeResponsavel1 == null || nomeResponsavel1.isBlank())
                && (nomeResponsavel2 == null || nomeResponsavel2.isBlank())) {
            throw new IllegalArgumentException("Informe pelo menos um responsável.");
        }

        Motorista motorista = motoristaRepository.findByCpf(cpfMotorista)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Motorista conectado não encontrado."
                ));

        Aluno aluno = new Aluno();
        aluno.setNome(nome.trim());
        aluno.setCpf(cpfAluno);
        aluno.setNomeResponsavel1(limpar(nomeResponsavel1));
        aluno.setCpfResponsavel1(limpar(cpfResp1));
        aluno.setNomeResponsavel2(limpar(nomeResponsavel2));
        aluno.setCpfResponsavel2(limpar(cpfResp2));
        aluno.setMotorista(motorista);

        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarDoMotorista(String cpfMotoristaLogado) {
        String cpfMotorista = normalizarCpf(cpfMotoristaLogado);
        return alunoRepository.findByMotorista_CpfOrderByNomeAsc(cpfMotorista);
    }

    public void excluir(Long alunoId, String cpfMotoristaLogado) {
        String cpfMotorista = normalizarCpf(cpfMotoristaLogado);

        Aluno aluno = alunoRepository
                .findByIdAndMotorista_Cpf(alunoId, cpfMotorista)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno não encontrado para este motorista."
                ));

        alunoRepository.delete(aluno);
    }

    public Aluno atualizar(
            Long alunoId,
            String cpfMotoristaLogado,
            String nome,
            String cpf,
            String nomeResponsavel1,
            String cpfResponsavel1,
            String nomeResponsavel2,
            String cpfResponsavel2
    ) {
        String cpfMotorista = normalizarCpf(cpfMotoristaLogado);
        String cpfAluno = normalizarCpf(cpf);
        String cpfResp1 = normalizarCpf(cpfResponsavel1);
        String cpfResp2 = normalizarCpf(cpfResponsavel2);

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }

        validarCpf(cpfAluno, "O CPF do aluno deve ter 11 dígitos.");
        validarResponsavel(nomeResponsavel1, cpfResp1, "responsável 1");
        validarResponsavel(nomeResponsavel2, cpfResp2, "responsável 2");

        if ((nomeResponsavel1 == null || nomeResponsavel1.isBlank())
                && (nomeResponsavel2 == null || nomeResponsavel2.isBlank())) {
            throw new IllegalArgumentException("Informe pelo menos um responsável.");
        }

        Aluno aluno = alunoRepository
                .findByIdAndMotorista_Cpf(alunoId, cpfMotorista)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno não encontrado para este motorista."
                ));

        aluno.setNome(nome.trim());
        aluno.setCpf(cpfAluno);
        aluno.setNomeResponsavel1(limpar(nomeResponsavel1));
        aluno.setCpfResponsavel1(limpar(cpfResp1));
        aluno.setNomeResponsavel2(limpar(nomeResponsavel2));
        aluno.setCpfResponsavel2(limpar(cpfResp2));

        return alunoRepository.save(aluno);
    }

    private void validarResponsavel(String nome, String cpf, String identificacao) {
        boolean temNome = nome != null && !nome.isBlank();
        boolean temCpf = cpf != null && !cpf.isBlank();

        if (temNome != temCpf) {
            throw new IllegalArgumentException(
                    "Preencha nome e CPF do " + identificacao + ", ou deixe ambos vazios."
            );
        }

        if (temCpf) {
            validarCpf(cpf, "O CPF do " + identificacao + " deve ter 11 dígitos.");
        }
    }

    private void validarCpf(String cpf, String mensagem) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    private String normalizarCpf(String cpf) {
        return cpf == null ? "" : cpf.replaceAll("\\D", "");
    }

    private String limpar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}