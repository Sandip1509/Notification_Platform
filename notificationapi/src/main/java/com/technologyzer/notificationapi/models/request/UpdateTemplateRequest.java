package com.technologyzer.notificationapi.models.request;

import com.technologyzer.notificationapi.exception.ValidationException;
import com.technologyzer.notificationapi.utils.CommonUtils;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

import java.util.Map;

import static com.technologyzer.notificationapi.constants.ErrorConstants.*;

@Data
public class UpdateTemplateRequest {

    private String name;

    private Map<String, String> templateVariables;

    private String messageTemplate;

    @AssertTrue
    public boolean validateTemplateVariable() {
        if (CommonUtils.isNotEmpty(templateVariables) && templateVariables.size() > 20) {
            throw new ValidationException(TEMPLATE_VARIABLE_ERROR);
        }

        if (CommonUtils.isNotEmpty(name) && name.trim().length() > 20) {
            throw new ValidationException(NAME_VARIABLE_ERROR);
        }

        if (CommonUtils.isNotEmpty(messageTemplate) && messageTemplate.trim().length() > 10000) {
            throw new ValidationException(MESSAGE_VARIABLE_ERROR);
        }

        return true;
    }

}
