package com.rainforest.eco;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rainforest.eco.analytics.TreatmentThread;
import com.rainforest.eco.services.Log;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		Log.logger.info("Starting Thread: TreatmentThread");
		Log.logger.info("Starting Thread: Analyze pot measurements to schedule treatments");
		//  Create pool with 10 threads
		int numThreads = 1;
		final ScheduledExecutorService schExService = Executors.newScheduledThreadPool(numThreads);
		// Create runnable thread
		final Runnable treatmentAnalyzer = new TreatmentThread();
		// Thread scheduling
		int minute = 60;
		schExService.scheduleWithFixedDelay(treatmentAnalyzer, minute, 10 * minute, TimeUnit.SECONDS);
		Log.logger.info("Thread Started: Currently Running");
	}

}
