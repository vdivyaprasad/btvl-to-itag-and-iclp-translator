package com.apolloits.viaplus.btvltoitagandiclptranslator.utility;

import org.springframework.stereotype.Component;

@Component
public class ITagUtility {

    public String getTagStatus(String tagStatus){
        if(tagStatus.equals("V")){
            return "1";
        }else if(tagStatus.equals("Z")){
            return "3";
        }
        return "2";

    }

    public String getAccountTypeIndicator(String fleetIndicator) {
        if(fleetIndicator.equals("Y")){
            return "F";
        }else if(fleetIndicator.equals("N")){
            return "*";
        }
        return "*";
    }

    public String getTagType(String tagType) {
        return switch (tagType) {
            case "S", "Switchable" -> "S";
            case "F", "Feedback" -> "F";
            case "G", "Feedback and Switchable" -> "G";
            default -> "*";
        };
    }
}
