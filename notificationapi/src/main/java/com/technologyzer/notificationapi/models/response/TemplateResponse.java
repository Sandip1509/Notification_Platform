package com.technologyzer.notificationapi.models.response;

import com.technologyzer.notificationapi.models.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponse {
    private String id;
    private String name;

    public TemplateResponse(final Template template) {
        setId(template.getId().toString());
        setName(template.getName());
    }
}
