/**
 * 
 */
package com.trinet.benefits.oe.web;

import  com.trinet.benefits.oe.common.PathVariables;

/**
 * @author imistry1
 *
 * 
 */
public final class Mappings {
	
	public static final String VERSION = "/v1/platform";
	public static final String APP_HEALTH = "/monitor";
	public static final String UPLOAD_FILE = "/upload";
	public static final String GET_BATCH_BY_ID = "/getbatch"+"/{"+PathVariables.PROCESS_ID+"}";
	public static final String GET_BATCH_BY_APP_ID = "/getbatchbyappid"+"/{"+PathVariables.PROCESS_ID+"}";
	public static final String GET_ALL_BATCH_PROCESS = "/getallbatches";
	public static final String REGISTER_BATCH = "/registerbatch";
	public static final String GET_BATCH_PARAMS = "/getbatchparams"+"/{"+PathVariables.PROCESS_ID+"}";

	public static final String ADD_TASK = "/addtask";
	public static final String GET_ALL_TASK = "/allTask";
	public static final String GET_TASK_ITEMS_BY_WORKER_ID = "/getTaskItems"+"/{"+PathVariables.WORKER_ID+"}"+"/{"+PathVariables.TASK_STEP_ID+"}"+"/{"+PathVariables.TASK_STATUS+"}"+"/{"+PathVariables.PARTITION_SIZE+"}";

	public static final String CANCEL_TASK = "/cancelTask/{"+PathVariables.BATCH_PROCESS_TASK_ID+"}";
	

	public static final String GET_PREPARED_UNPREPARED_STEP_ID = "/gettaskttep";
	public static final String UPDATE_PREPARED_FLAG = "/updateprepared"+"/{"+PathVariables.TASK_STEP_ID+"}";
	public static final String UPDATE_TASK_ITEMS = "/updatetaskitems"+"/{"+PathVariables.TASK_STATUS+"}";

	
	private Mappings() {
		throw new AssertionError("Cannot instantiate com.trinet.dm.web.Mappings.");
	}
}
