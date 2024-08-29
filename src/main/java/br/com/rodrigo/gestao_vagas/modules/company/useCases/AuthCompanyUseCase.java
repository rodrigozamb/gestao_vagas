package br.com.rodrigo.gestao_vagas.modules.company.useCases;

import br.com.rodrigo.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import br.com.rodrigo.gestao_vagas.modules.company.dtos.AuthCompanyResponseDTO;
import br.com.rodrigo.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        System.out.println(authCompanyDTO.getUsername()+" - "+authCompanyDTO.getPassword());
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(()-> {
            throw new UsernameNotFoundException("Username/Password Incorrect");
        });

        var passwordsMatch = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if(!passwordsMatch){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withSubject(company.getId().toString()).sign(algorithm);

        var responseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(Date.from(expiresIn))
                .build();

        return responseDTO;
    }
}
