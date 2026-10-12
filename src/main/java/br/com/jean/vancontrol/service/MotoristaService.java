package br.com.jean.vancontrol.service;

import br.com.jean.vancontrol.model.Motorista;
import br.com.jean.vancontrol.repository.MotoristaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final PasswordEncoder passwordEncoder;

    public MotoristaService(
            MotoristaRepository motoristaRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.motoristaRepository = motoristaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Motorista cadastrar(String usuario, String cpf, String senha) {
        String cpfNormalizado = cpf.replaceAll("\\D", "");

        if (usuario == null || usuario.isBlank()) {
            throw new IllegalArgumentException("O nome de usuário é obrigatório.");
        }

        if (!cpfNormalizado.matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve ter 11 dígitos.");
        }

        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter ao menos 6 caracteres.");
        }

        if (motoristaRepository.findByCpf(cpfNormalizado).isPresent()) {
            throw new IllegalArgumentException("Já existe um motorista cadastrado com este CPF.");
        }

        Motorista motorista = new Motorista();
        motorista.setUsuario(usuario.trim());
        motorista.setCpf(cpfNormalizado);
        motorista.setSenhaHash(passwordEncoder.encode(senha));

        return motoristaRepository.save(motorista);
    }
}