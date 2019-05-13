package com.sonda.transporte.consola.agente.firmware.infrastructure.engine;

import com.sonda.transporte.consola.agente.firmware.application.rule.BusinessRule;
import com.sonda.transporte.consola.agente.firmware.application.rule.Rule;
import com.sonda.transporte.consola.agente.firmware.application.rule.StatusRule;
import com.sonda.transporte.consola.agente.firmware.domain.Dispositivo;
import com.sonda.transporte.consola.agente.firmware.domain.Firmware;
import com.sonda.transporte.consola.agente.firmware.infrastructure.enums.RuleEngineStatusEnum;
import com.sonda.transporte.consola.agente.firmware.infrastructure.util.UtilLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RuleEngine {

    private List<BusinessRule> businessRules;

    private List<StatusRule> statusRules;

    private RuleEngineStatusEnum ruleStatus;

    public static final UtilLogger log = UtilLogger.getLogger(RuleEngine.class);

    public void process(Dispositivo dispositivo, Firmware firmware) {
        boolean isValid = false;
        try {
            for (BusinessRule businessRule : businessRules) {
                isValid = businessRule.execute(dispositivo, firmware);
                //
                if (isValid) { // its ok
                    ruleStatus = RuleEngineStatusEnum.SUCCESS; // set status engine

                } else {
                    ruleStatus = RuleEngineStatusEnum.ERROR;
                    break;
                }
            }
        } catch (Exception e) {
            ruleStatus = RuleEngineStatusEnum.ERROR;
            //
            log.error(e.getMessage(), e);
        }
    }

    public void process(Firmware firmware) {
        boolean isValid = false;
        try {
            for (StatusRule statusRuleRule : statusRules) {
                isValid = statusRuleRule.execute(firmware);
                //
                if (isValid) { // its ok
                    ruleStatus = RuleEngineStatusEnum.SUCCESS; // set status engine

                } else {
                    ruleStatus = RuleEngineStatusEnum.ERROR;
                    break;
                }
            }
        } catch (Exception e) {
            ruleStatus = RuleEngineStatusEnum.ERROR;
            //
            log.error(e.getMessage(), e);
        }
    }

    public boolean addRule(Rule rule) {
        boolean isAdd = false;
        if (rule instanceof BusinessRule) {
            if (Objects.nonNull(businessRules)) {
                isAdd = businessRules.add((BusinessRule) rule);
            } else {
                businessRules = new ArrayList<>();
                isAdd = businessRules.add((BusinessRule) rule);
            }
        } else if (rule instanceof StatusRule) {
            if (Objects.nonNull(statusRules)) {
                isAdd = statusRules.add((StatusRule) rule);
            } else {
                statusRules = new ArrayList<>();
                isAdd = statusRules.add((StatusRule) rule);
            }
        }
        return isAdd;
    }

    public List<BusinessRule> getBusinessRules() {
        return businessRules;
    }

    public void setBusinessRules(List<BusinessRule> businessRules) {
        this.businessRules = businessRules;
    }

    public RuleEngineStatusEnum getRuleStatus() {
        return ruleStatus;
    }

}
