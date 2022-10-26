package com.sushi.flower.model;

import lombok.Data;

@Data
public class TaskLinkRequest {
    private Long taskIdFrom;
    private Long taskIdTo;
}
