package com.codestates.entrepreneur.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultiResponseDto<T> {

    private Integer totalCnt;
    private List<T> data;

}
