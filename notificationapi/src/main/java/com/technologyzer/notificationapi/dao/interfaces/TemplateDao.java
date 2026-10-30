package com.technologyzer.notificationapi.dao.interfaces;

import com.technologyzer.notificationapi.models.entity.Template;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.function.Supplier;

public interface TemplateDao {

    Optional<Template> findByTenantIdAndName(String tenantId, String templateName);

    Optional<Template> findByTenantIdAndId(String tenantId, String id);

    Template save(Template template);

    Page<Template> filterTemplate(Example<Template> templateExample, PageRequest pageRequest);

    void deleteTemplateById(String id, Supplier<? extends Throwable> exceptionHandler);
}
