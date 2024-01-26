//package sportshop.web.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import jakarta.transaction.Transactional;
//import sportshop.web.Model.Account;
//import sportshop.web.Repository.AccountRepository;
//import sportshop.web.Repository.UserDetailsImpl;
//
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService{
//	@Autowired
//	private AccountRepository accountRepository;
//	
//	
//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Account account = accountRepository.findByUsername(username)
//										.orElseThrow(() -> new UsernameNotFoundException("Not found"+username));
//		
//		return UserDetailsImpl.build(account);
//	}
//
//}
