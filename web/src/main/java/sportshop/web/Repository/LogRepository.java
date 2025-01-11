package sportshop.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.Log;



@Repository
public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<Log>{

}
