package com.coworking.notifications_service.util;

import lombok.ToString;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class EmailContentBuilder {

    public String build(String templateContent, Map<String, String> placeholders) {
        String content = templateContent;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            content = content.replace("[" + entry.getKey() + "]", entry.getValue());
        }
        return content;
    }
}