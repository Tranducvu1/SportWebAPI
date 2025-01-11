package sportshop.web.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

	 USER(Set.of(
		        Permission.USER_READ,
		        Permission.USER_UPDATE
		    )),
		    ADMIN(Set.of(
		        Permission.ADMIN_READ,
		        Permission.ADMIN_UPDATE,
		        Permission.ADMIN_DELETE,
		        Permission.ADMIN_CREATE,
		        Permission.USER_READ,
		        Permission.USER_UPDATE
		    ));

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority(this.name()));
    return authorities;
  }
	}
