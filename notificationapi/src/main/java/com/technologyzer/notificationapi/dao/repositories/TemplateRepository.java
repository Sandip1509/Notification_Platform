package com.technologyzer.notificationapi.dao.repositories;

import com.technologyzer.notificationapi.models.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends MongoRepository<Template, UUID> {

    Optional<Template> findByNameIgnoreCaseAndTenantId(String name, UUID tenantId);

    Optional<Template> findByIdAndTenantId(UUID id, UUID tenantId);
}
