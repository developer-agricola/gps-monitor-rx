package com.sonda.transporte.consola.agente.firmware.infrastructure.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * Represent a rule status.
 * 
 * @author Everis
 * @author Daniel Carvajal (daniel.carvajal.soto@everis.com)
 * @version 1.0
 *  
 */
public enum RuleEngineStatusEnum {	
	SUCCESS(0, "SUCCESS"),
	EXCEPTION(-1, "EXCEPTION"),
	ERROR(-2, "ERROR");
		

    private final Integer code;
    private final String message; 

	private RuleEngineStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * get engine status code
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}
	
	
	/**
	 * get engine status message
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}	
	
	/**
	 * 
	 * get all engine status
	 * 
	 * @return all engine status
	 */
    public static Map<Integer, String> getHashMapValues() {
        Map<Integer, String> hashMap = new HashMap<>();
        for (RuleEngineStatusEnum e : RuleEngineStatusEnum.values()) {
            hashMap.put(e.getCode(), e.getMessage());
        }
        return hashMap;
    }
}
