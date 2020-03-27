package com.trinet.benefits.oe.common;

public final class PathVariables {
	
	public static final String PROCESS_ID = "processId";
	public static final String WORKER_ID = "workerId";
	public static final String TASK_STEP_ID = "taskStepId";
	public static final String BATCH_PROCESS_TASK_ID = "batchProcessTaskId";
	public static final String TASK_STATUS =  "status" ;
	public static final String PARTITION_SIZE = "partitionSize";
	
	private PathVariables() {
		throw new AssertionError("Cannot instantiate com.trinet.benefits.oe.common.");
	}

}
