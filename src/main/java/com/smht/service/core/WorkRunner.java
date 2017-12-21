package com.smht.service.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.gearman.worker.GearmanWorkerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smht.service.core.configuration.FunctionInfo;
import com.smht.service.core.configuration.ServerInfo;
import com.smht.service.core.configuration.ServiceInfo;
import com.smht.service.core.configuration.WorkerInfo;

	


public class WorkRunner extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Context context;
	
	private WorkerInfo wokerInfo;
	
	private long workerId;
	
	private ServiceInfo serviceInfo;
	
	public WorkRunner(Context context, WorkerInfo wokerInfo, ServiceInfo serviceInfo, long workerId){
		this.setDaemon(true);
		this.context = context;
		this.wokerInfo = wokerInfo;
		this.serviceInfo = serviceInfo;
		this.workerId = workerId;
	}
	
	@Override
	public void run() {
		try {
			initGearmanWorker();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void initGearmanWorker() throws ClassNotFoundException {

		int threadsNum = this.wokerInfo.getThreadPoolCapacity();
		ExecutorService executorService = null;
		if (threadsNum <= 0) {
			executorService = Executors.newCachedThreadPool(new ThreadFactoryImpl("ACTION_"));
		} else if(threadsNum > 0) {
			executorService = Executors.newFixedThreadPool(threadsNum, new ThreadFactoryImpl("ACTION_"));
		}

		GearmanWorkerImpl worker = new GearmanWorkerImpl(executorService);
		
		IdWorker idWorker = null;
		if(this.serviceInfo.getId() != -1){
			idWorker = new IdWorker(this.workerId, this.serviceInfo.getId());
		}
		
		for (FunctionInfo functionInfo : this.wokerInfo.getActions()) {
			String serviceClass = functionInfo.getClazz();
			String serviceName = functionInfo.getServiceName();
			
			worker.registerFunctionFactory(new GearmanActionFactory(
					serviceName, (Class<? extends GearmanAction>) Class
							.forName(serviceClass), this.context, idWorker), functionInfo.getTimeout());
		}
		
		for (ServerInfo server : this.wokerInfo.getServerInfos()) {
			worker.addServer(server.getIp(), server.getPort());
		}
		
		worker.work();
	}
	
}
