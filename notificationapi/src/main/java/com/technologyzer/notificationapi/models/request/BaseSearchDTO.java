package com.technologyzer.notificationapi.models.request;

import com.technologyzer.notificationapi.exception.ValidationException;
import com.technologyzer.notificationapi.models.context.NotificationContextHolder;
import com.technologyzer.notificationapi.utils.CommonUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.util.UUID;

import static com.technologyzer.notificationapi.utils.CommonUtils.getCurrentTenantId;
import static com.technologyzer.notificationapi.utils.CommonUtils.isNotEmpty;
import static java.util.Optional.ofNullable;

public abstract class BaseSearchDTO<T> {
    private Integer page;
    private Integer pageSize;
    private SortRequest sortRequest;

    public PageRequest buildPageRequest() {
        Sort sort = ofNullable(sortRequest)
                .filter(CommonUtils::isNotEmpty)
                .filter(req -> isNotEmpty(req.getSortKey()) && isNotEmpty(req.getSortType()))
                .filter(req ->{
                    try {
                        this.getClass().getDeclaredField(req.getSortKey());
                        return true;
                    } catch (NoSuchFieldException e) {
                        throw new ValidationException("Invalid sort key", HttpStatus.BAD_REQUEST.value());
                    }
                }).map(req -> Sort.by(
                        Sort.Direction.fromString(req.getSortType().getValue()), req.getSortKey()
                        ))
                .orElse(Sort.by(Sort.Direction.DESC, "createdAt"));
        return PageRequest.of(
                ofNullable(page).orElse(0),
                ofNullable(pageSize).orElse(10),
                sort);
    }

    public Example<T> buildSearch() {
        try {
            Class<T> clazz = getEntity();
            T instance = clazz.getDeclaredConstructor().newInstance();

            injectTenantId(instance);

            for (Field dtoField : this.getClass().getDeclaredFields()) {
                dtoField.setAccessible(true);
                Object fieldValue = dtoField.get(this);
                if (isNotEmpty(fieldValue)) {
                    Field entityField = getField(clazz, dtoField.getName());
                    entityField.setAccessible(true);
                    entityField.set(instance, fieldValue);
                }
            }

            ExampleMatcher matcher = ExampleMatcher
                    .matchingAll()
                    .withIgnoreCase()
                    .withIgnoreNullValues();

            return Example.of(instance, matcher);
        } catch (Exception e) {
            throw new ValidationException("Error While Building Search",
                    HttpStatus.BAD_REQUEST.value());
        }

    }

    private void injectTenantId(final Object instance) {
        if (NotificationContextHolder.getContext().ignoreTenantIdInjection()) {
            System.out.println("Ignoring Tenant Id Injection");
            return;
        }
        try {
            Field tenantIdField = getField(instance.getClass(), "tenantId");
            tenantIdField.setAccessible(true);
            tenantIdField.set(instance, getCurrentTenantId());
        } catch (IllegalAccessException e) {
            throw new ValidationException("Error While Setting Tenant Id In Search Builder", HttpStatus.BAD_REQUEST.value());
        }

    }

    public Field getField(final Class<?> clazz, final String name) {
        Class<?> current = clazz;

        while (current != null) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }

        return null;
    }

    public abstract Class<T> getEntity();

}
