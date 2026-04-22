package com.supervision.safety.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateStatusReq {
    @NotNull private IssueStatus status;
}
