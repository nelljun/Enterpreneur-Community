package com.codestates.entrepreneur.member;

import com.codestates.entrepreneur.member.dto.MemberResponseDto;
import com.codestates.entrepreneur.member.dto.MultiResponseDto;
import com.codestates.entrepreneur.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @GetMapping
    public ResponseEntity getMemberList(@ModelAttribute MemberSearchCond memberSearchCond) {
        List<Member> memberList = memberService.findMembers(memberSearchCond);
        List<MemberResponseDto> memberResponseDtoList = mapper.memberListToMemberResponseDtoList(memberList);

        return new ResponseEntity<>(
            new MultiResponseDto<>(
                memberResponseDtoList.size(),
                memberResponseDtoList
            ),
            HttpStatus.OK
        );
    }


}
