package com.technologyzer.notificationapi.dao.impl;


import com.technologyzer.notificationapi.dao.interfaces.TemplateDao;
import com.technologyzer.notificationapi.dao.repositories.TemplateRepository;
import com.technologyzer.notificationapi.models.entity.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static com.technologyzer.notificationapi.utils.CommonUtils.getCurrentTenantId;
import static com.technologyzer.notificationapi.utils.CommonUtils.isNotEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

    private final TemplateRepository templateRepository;
    private final CacheManager cacheManager;

    @Override
    @Cacheable(cacheNames = "templatesByName", key="#tenantId + ':' + #templateName", unless = "#result == null")
    public Optional<Template> findByTenantIdAndName(final UUID tenantId, final String templateName) {
        return templateRepository.findByNameIgnoreCaseAndTenantId(templateName, tenantId);
    }

    @Override
    @Cacheable(cacheNames = "templatesById", key = "#tenantId + ':' + #id", unless = "#result == null")
    public Optional<Template> findByTenantIdAndId(final UUID tenantId, final String id) {
        return templateRepository.findByIdAndTenantId(UUID.fromString(id), tenantId);
    }

    @Override
    @Caching(put = {
            @CachePut(cacheNames = "templatesById", key = "#template.tenantId + ':' + #template.id"),
            @CachePut(cacheNames = "templatesByName", key = "#template.tenantId + ':' + #template.name")
    })
    public Template save(final Template template) {
        return templateRepository.save(template);
    }

    @Override
    public Page<Template> filterTemplate(final Example<Template> example,
                                         final PageRequest pageRequest) {
        return templateRepository.findAll(example, pageRequest);
    }

    @Override
    public void deleteTemplateById(final String id, final Supplier<? extends Throwable> exceptionHandler) {
        templateRepository.findByIdAndTenantId(UUID.fromString(id), getCurrentTenantId()).ifPresentOrElse(template -> {
            cacheManager.getCache("templatesById").evict(template.getTenantId() + ":" + template.getId());
            cacheManager.getCache("templatesByName").evict(template.getTenantId() + ":" + template.getName());
            templateRepository.deleteById(UUID.fromString(id));
        }, () -> {
            if (isNotEmpty(exceptionHandler)) {
                exceptionHandler.get();
            }
        });
    }
}