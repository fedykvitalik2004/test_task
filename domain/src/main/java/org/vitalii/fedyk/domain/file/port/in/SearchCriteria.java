package org.vitalii.fedyk.domain.file.port.in;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SearchCriteria (String customer,
                              String type,
                              LocalDate date){
}
