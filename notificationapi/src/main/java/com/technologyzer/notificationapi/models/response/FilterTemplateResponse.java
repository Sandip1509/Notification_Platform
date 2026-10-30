package com.technologyzer.notificationapi.models.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class FilterTemplateResponse  extends BaseTemplateResponse<TemplateResponseDTO, Long>{

    public FilterTemplateResponse(final List<TemplateResponseDTO> templates,
                                  final boolean hasMoreElement,
                                  final long totalCount) {
        setData(templates);
        setHasMoreElement(hasMoreElement);
        setTotalCount(totalCount);
    }
}
