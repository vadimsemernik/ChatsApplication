package server.logic.stateUpdates;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerStateUpdaterExecutor {
	
private static ExecutorService executor;
	
	public static void initExecutor(){
		executor = Executors.newCachedThreadPool();
	}
	
	public static void initExecutor(int workers){
		executor = Executors.newFixedThreadPool(workers);
	}
	
	public static void addTask (Runnable task){
		executor.execute(task);
	}

}
