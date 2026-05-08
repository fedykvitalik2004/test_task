package org.vitalii.fedyk.infrastructure.adapter.in.web.file.dto;

import java.time.LocalDate;

public record SearchCriteriaDTO(String customer,
                                String type,
                                LocalDate date) {
}
