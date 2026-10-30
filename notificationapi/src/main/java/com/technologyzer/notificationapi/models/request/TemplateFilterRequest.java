package com.technologyzer.notificationapi.models.request;

import com.technologyzer.notificationapi.models.entity.Template;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateFilterRequest extends BaseSearchDTO<Template>{
    private String name;


    @Override
    public Class<Template> getEntity() {
        return Template.class;
    }
}
