package sportshop.web.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.Token;



@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{

	
	 @Query("SELECT t FROM Token t INNER JOIN nguoidung u ON t.nguoidung.id = u.id WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
	List<Token> findAllValidTokenByUser(@Param(value = "id") Long id);
	Optional<Token> findBytoken(String token);
}
