package com.codestates.entrepreneur.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findMembers(MemberSearchCond memberSearchCond) {
        return null;
    }
}
