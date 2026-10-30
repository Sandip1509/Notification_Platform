package com.technologyzer.notificationapi.models.request;

import com.technologyzer.notificationapi.exception.ValidationException;
import com.technologyzer.notificationapi.utils.CommonUtils;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

import static com.technologyzer.notificationapi.constants.ErrorConstants.*;

@Data
public class SendNotificationRequest {

    @NotBlank(message = TEMPLATE_ID_IS_REQUIRED)
    private String templateId;
    private Map<String, Object> dynamicVariables;
    private NotificationType notificationType;

    @AssertTrue
    public boolean validateNotificationType() {
        if (CommonUtils.isEmpty(notificationType)) {
            throw new ValidationException(NOTIFICATION_TYPE_VARIABLE_ERROR);
        }

        if (CommonUtils.isEmpty(dynamicVariables)) {
            throw new ValidationException(SEND_NOTIFICATION_TEMPLATE_VARIABLE_ERROR);
        }
        return true;
    }

}
