package com.sonda.transporte.consola.agente.firmware.infrastructure.engine;

import com.sonda.transporte.consola.agente.firmware.application.rule.Rule;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

public class RuleFactory {

	private RuleFactory(){}

	public static final UtilLogger log = UtilLogger.getLogger(RuleFactory.class);

	public static RuleEngine createNewEngineInstance(Class<? extends Rule>... clazzBusinessRules) {
		RuleEngine ruleEngine = new RuleEngine();
		try {
			for (Class<? extends Rule> clazzRule : clazzBusinessRules) {
				Rule businessRuleFollowing = clazzRule.newInstance();
				ruleEngine.addRule(businessRuleFollowing);
			}
		}catch (IllegalAccessException | InstantiationException e){
			log.error(e.getMessage(), e);
		}
		return ruleEngine;
	}

}
