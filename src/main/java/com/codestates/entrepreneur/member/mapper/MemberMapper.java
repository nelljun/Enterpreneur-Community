package com.codestates.entrepreneur.member.mapper;

import com.codestates.entrepreneur.member.Member;
import com.codestates.entrepreneur.member.dto.MemberResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public List<MemberResponseDto> memberListToMemberResponseDtoList(List<Member> memberList) {
//        return memberList.stream()
//                    .map(member -> {
//                        return MemberResponseDto.builder()
//                                        .id(member.getId())
//                                        .name(member.getName())
//                                        .sex(member.getSex())
//                                        .companyName(member.getCompanyName())
//                                        .companyType(member.getCompanyType().getType())
//                                        .companyLocation(member.getCompanyLocation().getLocation())
//                                        .build();
//                    })
//                    .collect(Collectors.toList());
        return null;
    }

    
}
