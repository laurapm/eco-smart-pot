package com.rainforest.eco.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Operation 
{
	private static Logger log = LogManager.getLogger(Operation.class);
	
	public static String getSha256(String message) {
		try {
			String shaKey = DigestUtils.sha256Hex(message);
			
			return shaKey;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
