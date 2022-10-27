package com.sushi.flower.model;

import lombok.Data;

@Data
public class TaskLinkRequest {
    private Long parentTask;
    private Long childTask;
}
