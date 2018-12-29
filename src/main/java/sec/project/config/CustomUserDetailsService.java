package sec.project.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.Signup;
import sec.project.repository.AccountRepository;
import sec.project.repository.SignupRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    //For creating some demo data.
    @Autowired
    private SignupRepository signupRepository;
    
    @PostConstruct
    public void init() {
        Account account = new Account();
        account.setUsername("admin");
        account.setPassword("qwerty");
        accountRepository.save(account);
        
        account = new Account();
        account.setUsername("su");
        account.setPassword("123456");
        accountRepository.save(account);
        
        //setup some demo data
        Signup signup = new Signup("demo","demo data");
        signupRepository.save(signup);
        
        signup = new Signup("demo name","demo address");
        signupRepository.save(signup);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }
}
