package com.technologyzer.config;

import com.technologyzer.filter.CorrelationIdFilter;
import com.technologyzer.services.ResponseHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({ResponseHandler.class, CorrelationIdFilter.class})
public class CommonAutoConfiguration {
}
