package sportshop.web.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {

  ADMIN_READ("admin:read"),
  ADMIN_UPDATE("admin:update"),
  ADMIN_DELETE("admin:delete"),
  ADMIN_CREATE("admin:create");

  private final String permission;
}
