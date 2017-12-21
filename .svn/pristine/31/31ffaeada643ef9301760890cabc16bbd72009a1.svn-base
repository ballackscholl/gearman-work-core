package com.smht.service.core;

import org.gearman.common.Constants;
import org.gearman.worker.GearmanFunction;
import org.gearman.worker.GearmanFunctionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.smht.service.core.configuration.GmAction;

public class GearmanActionFactory implements GearmanFunctionFactory {
	
	public static final String APP_CONTEXT = "APP_CONTEXT";
	
	private static final org.slf4j.Logger LOG =  LoggerFactory.getLogger(
            Constants.GEARMAN_WORKER_LOGGER_NAME);
	
	private Class<? extends GearmanAction> clazz;
	private Context context;
	private String serviceName;
	private IdWorker idWorker;
	
	public GearmanActionFactory(String serviceName, Class<? extends GearmanAction> clazz, Context context, IdWorker idWorker){
		this.clazz = clazz;
		this.context = context;
		this.serviceName  = serviceName;
		this.idWorker = idWorker;
	}
	
	@Override
	public String getFunctionName() {
		return serviceName;
	}

	@Override
	public GearmanFunction getFunction() {
		
		try {
			Object application = context.get(APP_CONTEXT);
			if(application != null){
				GmAction gmAction = clazz.getAnnotation(GmAction.class);
				if(gmAction != null){
					Object gearmanFunction = null;
					try{
						gearmanFunction = ((ApplicationContext)application).getBean(gmAction.beanName());
					}catch(Exception e){
						LOG.error(e.getMessage());
					}
					if(gearmanFunction != null && gearmanFunction instanceof GearmanAction){
						((GearmanAction)gearmanFunction).initialize(serviceName, context, idWorker);
						return (GearmanAction)gearmanFunction;
					}
				}
			}
			GearmanAction func = this.clazz.newInstance();
			func.initialize(serviceName, context, idWorker);
			return func;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			LOG.error(e.getStackTrace().toString());
			return null;
		}
		
	}

}
