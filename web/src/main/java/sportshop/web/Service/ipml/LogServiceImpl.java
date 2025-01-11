package sportshop.web.Service.ipml;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Entity.Log;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Service.LogService;


@Service
public class LogServiceImpl  implements LogService {
	@Autowired
	private LogRepository logRepository;

    @Override
      public void saveLog(String logMessage) {
        Log log = new Log();
        log.setLogString(logMessage);
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        logRepository.save(log);
    }
    
}
