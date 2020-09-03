package com.rainforest.eco.analytics;

import java.net.HttpURLConnection;
import java.net.URL;

import com.rainforest.eco.services.Log;

public class TreatmentThread implements Runnable
{
	@Override
	public void run() 
	{
		try {
			callAnalyzerApi();	
		} catch (Exception e) {
			Log.logger.error("Some error ocurred " + e);
		}
	}
	
	/**
	 * Call the API to execute a function that retrieves the data and creates 
	 * treatments depending on their values.
	 *  
	 * @return Boolean indicating the execution state of the remote call.
	 */
	private Boolean callAnalyzerApi() {
		try {
			Log.logger.info("Requesting measurements");
			
			// Creating Request
			URL url = new URL("http://localhost:8080/api/analyze/measurements");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
			
			int responseCode = con.getResponseCode();
			Log.logger.info("The process finished with code: " + responseCode);
			
			con.disconnect();
			
			Log.logger.info("Returning measurements");
			
			return true;
		} catch (Exception e) {
			Log.logger.info("Some error ocurred while retrieving measurements: " + e);
			return false;
		}
	}

}
