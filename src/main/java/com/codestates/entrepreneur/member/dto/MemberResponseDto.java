package com.codestates.entrepreneur.member.dto;

import com.codestates.entrepreneur.company.CompanyLocation;
import com.codestates.entrepreneur.company.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private Character sex;
    private String companyName;
    private String companyType;
    private String companyLocation;
}
