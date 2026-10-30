package com.technologyzer.notificationapi.models.request;

import com.technologyzer.notificationapi.utils.CommonUtils;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

import static com.technologyzer.notificationapi.constants.ErrorConstants.*;

@Data
public class CreateTemplateRequest {

    @Size(max = 10000, message = NAME_VARIABLE_ERROR)
    @NotBlank(message = "Name Field Is Required")
    private String name;

    private Map<String, String> templateVariables;

    @Size(max = 10000, message = MESSAGE_VARIABLE_ERROR)
    @NotBlank(message = "Message Template Field Is Required")
    private String messageTemplate;

    @AssertTrue(message = TEMPLATE_VARIABLE_ERROR)
    public boolean validateTemplateVariable() {
        return CommonUtils.isNotEmpty(templateVariables) && templateVariables.size() <= 20;
    }

}
