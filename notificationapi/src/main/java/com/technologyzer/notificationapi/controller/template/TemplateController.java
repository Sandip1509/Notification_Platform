package com.technologyzer.notificationapi.controller.template;

import com.technologyzer.notificationapi.models.request.CreateTemplateRequest;
import com.technologyzer.notificationapi.models.request.TemplateFilterRequest;
import com.technologyzer.notificationapi.models.request.UpdateTemplateRequest;
import com.technologyzer.notificationapi.models.response.FilterTemplateResponse;
import com.technologyzer.notificationapi.models.response.TemplateResponse;
import com.technologyzer.notificationapi.service.interfaces.TemplateService;
import com.technologyzer.models.enums.APIResponseCode;
import com.technologyzer.models.interfaces.GenericAPIResponse;
import com.technologyzer.services.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/template")
public class TemplateController {

    private final TemplateService templateService;
    private final ResponseHandler responseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericAPIResponse<TemplateResponse> createTemplate(@Valid @RequestBody CreateTemplateRequest request) {
        return responseHandler.ok(templateService.createTemplate(request), APIResponseCode.SUCCESS);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GenericAPIResponse<FilterTemplateResponse> filterTemplate(TemplateFilterRequest request) {
        return responseHandler.ok(templateService.filterTemplate(request));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GenericAPIResponse<TemplateResponse> updateTemplate(@PathVariable String id, @RequestBody UpdateTemplateRequest request) {
        return responseHandler.ok(templateService.updateTemplate(id,request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GenericAPIResponse<String> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return responseHandler.ok("Template Deleted Successfully");
    }

}
