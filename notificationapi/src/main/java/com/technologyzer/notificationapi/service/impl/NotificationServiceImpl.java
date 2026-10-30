package com.technologyzer.notificationapi.service.impl;

import com.technologyzer.notificationapi.dao.interfaces.TemplateDao;
import com.technologyzer.notificationapi.exception.ValidationException;
import com.technologyzer.notificationapi.models.entity.Template;
import com.technologyzer.notificationapi.models.request.IngestTopicDTO;
import com.technologyzer.notificationapi.models.request.SendNotificationRequest;
import com.technologyzer.notificationapi.pubsub.publisher.GenericPublisher;

import com.technologyzer.notificationapi.service.interfaces.NotificationService;
import com.technologyzer.notificationapi.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.technologyzer.notificationapi.constants.ErrorConstants.TEMPLATE_NOT_EXISTS_WITH_ID_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final TemplateDao templateDao;
    private final GenericPublisher genericPublisher;

    @Override
    public void sendNotification(final SendNotificationRequest request) {
        Optional<Template> byTenantIdAndId = templateDao.findByTenantIdAndId(CommonUtils.getCurrentTenantId(), request.getTemplateId());
        if (byTenantIdAndId.isEmpty()) {
            // TODO send to audit topic;
//            genericPublisher.sendDataToAudit();
            throw new ValidationException(TEMPLATE_NOT_EXISTS_WITH_ID_ERROR, HttpStatus.BAD_REQUEST.value());
        }

        byTenantIdAndId.ifPresent(template -> {
            Map<String, Object> requestDynamicVariables = request.getDynamicVariables();
            if (template.getTemplateVariables().size() != requestDynamicVariables.size()
                    || template.getTemplateVariables().values().stream()
                    .anyMatch(variable -> !requestDynamicVariables.containsKey(variable))) {
                throw new ValidationException("Invalid Dynamic Variables");
            }
        });

        IngestTopicDTO ingestTopicDTO = new IngestTopicDTO();

        ingestTopicDTO.setRequestId(CommonUtils.getCurrentTraceId());
        ingestTopicDTO.setTenantId(CommonUtils.getCurrentTenantId());
        ingestTopicDTO.setReceivedAt(CommonUtils.getCurrentTimeStamp());
        ingestTopicDTO.setDynamicVariables(request.getDynamicVariables());
        ingestTopicDTO.setTemplateId(request.getTemplateId());
        ingestTopicDTO.setNotificationType(request.getNotificationType());


        genericPublisher.sendDataToIngest(ingestTopicDTO);

    }

}
