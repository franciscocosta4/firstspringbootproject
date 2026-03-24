package com.example.firstspringbootproject.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.firstspringbootproject.repository.UserRepository;

@Service // marca esta classe como um serviço Spring → fica disponível para injeção
public class CustomUserDetailsService implements UserDetailsService {
     // UserDetailsService é a interface que o Spring Security chama durante o login
    // apenas se implementa um único método: loadUserByUsername

    private final UserRepository userRepository;
    // injeção de dependência pelo construtor (tambem dava para fazer @Autowired no campo mas não é tão boa prática)
    // o Spring vê que o construtor precisa de UserRepository e injeta automaticamente
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override

     // o Spring chama este método com o valor que o utilizador escreveu no campo "username" do form
    // no nosso caso esse campo contém o email
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
        // se não encontrar manda uma exeção que assume que as credenciais são inválidas
            .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado: " + email));
            // () -> new ... é um lambda: função anónima sem parâmetros que cria o objeto
    }
}
