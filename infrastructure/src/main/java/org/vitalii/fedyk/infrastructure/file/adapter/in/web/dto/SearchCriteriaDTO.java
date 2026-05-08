package org.vitalii.fedyk.infrastructure.file.adapter.in.web.dto;

import java.time.LocalDate;

public record SearchCriteriaDTO(String customer,
                                String type,
                                LocalDate date) {
}
