package modelo.logger;

import modelo.Constantes;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerTaTeTi {
	
	private static final LoggerTaTeTi instancia= new LoggerTaTeTi();
	
	private LoggerTaTeTi() { }
	
	public static LoggerTaTeTi getInstancia() {
		return instancia;
	}
	
	public Logger getLogger(Object obj) {
		Logger logger= Logger.getLogger(obj.getClass().getName());
	  PropertyConfigurator.configure(Constantes.ARCH_LOGS);
	  return logger;
	}
}
