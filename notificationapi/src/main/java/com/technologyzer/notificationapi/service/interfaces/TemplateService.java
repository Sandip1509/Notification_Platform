package com.technologyzer.notificationapi.service.interfaces;


import com.technologyzer.notificationapi.models.request.CreateTemplateRequest;
import com.technologyzer.notificationapi.models.request.TemplateFilterRequest;
import com.technologyzer.notificationapi.models.request.UpdateTemplateRequest;
import com.technologyzer.notificationapi.models.response.FilterTemplateResponse;
import com.technologyzer.notificationapi.models.response.TemplateResponse;

public interface TemplateService {
    TemplateResponse createTemplate(CreateTemplateRequest request);

    FilterTemplateResponse filterTemplate(TemplateFilterRequest request);

    TemplateResponse updateTemplate(String id, UpdateTemplateRequest request);

    void deleteTemplate(String id);
}
