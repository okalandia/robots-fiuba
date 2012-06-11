package modelo.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerTaTeTi {
	private static String PATH_LOGS= "logs/log4j.properties";
	
	private static final LoggerTaTeTi instancia= new LoggerTaTeTi();
	
	private LoggerTaTeTi() { }
	
	public static LoggerTaTeTi getInstancia() {
		return instancia;
	}
	
	public Logger getLogger(Object obj) {
		Logger logger= Logger.getLogger(obj.getClass().getName());
	  PropertyConfigurator.configure(PATH_LOGS);
	  return logger;
	}
}
