package com.crud.tasks.domain;

import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class Task {
    private Long id;
    private String title;
    private String content;

}
