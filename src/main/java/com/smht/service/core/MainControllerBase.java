package com.smht.service.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smht.service.core.configuration.Configuration;
import com.smht.service.core.configuration.WorkerInfo;


public abstract class MainControllerBase {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Configuration configure = null;

    private Context context = Context.getInstance();
    
	private List<Thread> runnerList = new ArrayList<Thread>();
	
	protected Context getContext(){
		return this.context;
	}
	
	protected void setConfigure(Configuration configure){
		this.configure = configure;
	}
	
	abstract protected void initializeConfig() throws Exception;

	public void runWork() throws Exception{
		
		this.initializeConfig();
		
		long workerId = 0;
		for(WorkerInfo workerInfo : this.configure.getWorkers()){
			for(int i=0; i < workerInfo.getCount(); i++){
				Thread runner = new WorkRunner(this.context, workerInfo, 
												this.configure.getServiceInfo(), workerId); 
				this.runnerList.add(runner);
				runner.start();
				workerId++;
			}
		}
			
		Object waiter = new Object();
		synchronized(waiter){
			try {
				waiter.wait();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
}
