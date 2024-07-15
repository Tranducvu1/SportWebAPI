//package sportshop.web.Service;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import sportshop.web.DTO.Role;
//import sportshop.web.Model.NguoiDung;
//import sportshop.web.Repository.UserRepository;
//
//
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private UserRepository repo;
//
//	@Override
//	@Transactional(readOnly = true)
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		NguoiDung user = repo.findByEmail(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("User with email " + username + " was not be found");
//		}
//		
//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		Set<Role> roles = user.getRole()
//		for (Role role : roles) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role.get()));
//		}
//		return new User(username, user.getPassword(), grantedAuthorities);
//	}
//
//}
//
