package br.com.jean.vancontrol.security;

import br.com.jean.vancontrol.model.Motorista;
import br.com.jean.vancontrol.repository.MotoristaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MotoristaUserDetailsService implements UserDetailsService {

    private final MotoristaRepository motoristaRepository;

    public MotoristaUserDetailsService(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) {
        String cpfNormalizado = cpf.replaceAll("\\D", "");

        Motorista motorista = motoristaRepository
                .findByCpf(cpfNormalizado)
                .orElseThrow(() -> new UsernameNotFoundException("CPF não encontrado."));

        return User.withUsername(motorista.getCpf())
                .password(motorista.getSenhaHash())
                .roles("MOTORISTA")
                .build();
    }
}