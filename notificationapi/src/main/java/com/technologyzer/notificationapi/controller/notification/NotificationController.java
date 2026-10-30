package com.technologyzer.notificationapi.controller.notification;

import com.technologyzer.notificationapi.models.request.SendNotificationRequest;
import com.technologyzer.notificationapi.service.interfaces.NotificationService;
import com.technologyzer.models.enums.APIResponseCode;
import com.technologyzer.models.interfaces.GenericAPIResponse;
import com.technologyzer.services.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ResponseHandler responseHandler;

    @PostMapping
    public GenericAPIResponse<String> sendNotification(@Valid @RequestBody SendNotificationRequest notificationRequest) {
        notificationService.sendNotification(notificationRequest);
        return responseHandler.ok("Notification Sent Successfully", APIResponseCode.SUCCESS);
    }

}
