package sportshop.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sportshop.web.Model.Log;




public interface LogRepository extends JpaRepository<Log, Integer>, JpaSpecificationExecutor<Log>{

}
