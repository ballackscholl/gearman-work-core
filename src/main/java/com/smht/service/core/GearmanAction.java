package com.smht.service.core;

import org.gearman.client.GearmanJobResult;
import org.gearman.common.Constants;
import org.gearman.common.GearmanPacketImpl;
import org.gearman.common.GearmanPacketMagic;
import org.gearman.common.GearmanPacketType;
import org.gearman.worker.AbstractGearmanFunction;
import org.slf4j.LoggerFactory;

public abstract class GearmanAction extends AbstractGearmanFunction implements ActionSender{
		
	private static final org.slf4j.Logger LOG =  LoggerFactory.getLogger(
            Constants.GEARMAN_WORKER_LOGGER_NAME);
	
	public final static int MAX_PACKET_SIZE = 1024 * 8;
	
	private Context context;
	private IdWorker idWorker;
		
	protected void initialize(String name, Context context, IdWorker idWorker){
		this.name = name;
		this.context = context;
		this.idWorker = idWorker;
		
	}
	
	protected Context getContext() {
		return context;
	}
	
	public long nextId() {
		if(this.idWorker == null){
			return 0;
		}
		return this.idWorker.nextId();
	}
	
	protected byte[] getRequest() {
		return (byte[]) this.data;
	}

	@Override
	public void send(byte[] data) {
		
		if(data.length >= MAX_PACKET_SIZE){
			int lastPacketLen = data.length%MAX_PACKET_SIZE;
			int packetSize = data.length / MAX_PACKET_SIZE + (lastPacketLen > 0 ? 1 : 0);
			
			for(int i=0; i < packetSize; i++){
				int len = (i == packetSize - 1 ? (lastPacketLen==0? MAX_PACKET_SIZE : lastPacketLen) :MAX_PACKET_SIZE);
				byte[] tmp = new byte[len];
				System.arraycopy(data, i*MAX_PACKET_SIZE, tmp, 0, len);
				if(i == packetSize - 1){
					fireEvent(new GearmanPacketImpl(GearmanPacketMagic.REQ,
			                GearmanPacketType.WORK_COMPLETE,
			                GearmanPacketImpl.generatePacketData(jobHandle, tmp)));
				}else{
					fireEvent(new GearmanPacketImpl(GearmanPacketMagic.REQ,
			                GearmanPacketType.WORK_DATA,
			                GearmanPacketImpl.generatePacketData(jobHandle, tmp)));
				}
			}
		}else{
			fireEvent(new GearmanPacketImpl(GearmanPacketMagic.REQ,
	                GearmanPacketType.WORK_COMPLETE,
	                GearmanPacketImpl.generatePacketData(jobHandle, data)));
		}
        
    }
	
	
	@Override
	public GearmanJobResult call() {

        GearmanJobResult result = null;
        try {
            result = execute();
        } catch (Exception e) {
        	LOG.error(e.getMessage());
        	e.printStackTrace();
        	fireEvent(new GearmanPacketImpl(GearmanPacketMagic.REQ,
                    GearmanPacketType.WORK_EXCEPTION,
                    GearmanPacketImpl.generatePacketData(jobHandle,
                    		"worker exception".getBytes())));
        	return null;
        }
        
        if(result != null){
        	if (result.jobSucceeded()) {
        		send(result.getResults());
            } else {
            	fireEvent(new GearmanPacketImpl(GearmanPacketMagic.REQ,
                        GearmanPacketType.WORK_FAIL, jobHandle));
            }
        }
          
        return result;
	}	
}
