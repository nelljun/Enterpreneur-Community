package com.codestates.entrepreneur.restdocs.member;

import com.codestates.entrepreneur.company.CompanyLocation;
import com.codestates.entrepreneur.company.CompanyType;
import com.codestates.entrepreneur.member.Member;
import com.codestates.entrepreneur.member.MemberController;
import com.codestates.entrepreneur.member.MemberSearchCond;
import com.codestates.entrepreneur.member.MemberService;
import com.codestates.entrepreneur.member.dto.MemberResponseDto;
import com.codestates.entrepreneur.member.mapper.MemberMapper;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Test
    public void getMemberListTest() throws Exception {
        CompanyType companyType1 = CompanyType.builder()
                                        .id(1L)
                                        .type("요식업")
                                        .build();
        CompanyLocation companyLocation1 = CompanyLocation.builder()
                                                .id(1L)
                                                .location("서울특별시 강남구")
                                                .build();
        CompanyType companyType2 = CompanyType.builder()
                                        .id(2L)
                                        .type("임대업")
                                        .build();
        CompanyLocation companyLocation2 = CompanyLocation.builder()
                                                .id(2L)
                                                .location("경기도 성남시")
                                                .build();
        Member member1
            = Member.builder()
                    .id(1L)
                    .name("회원1")
                    .sex('m')
                    .password("1111")
                    .companyName("코드스테이츠1")
                    .companyType(companyType1)
                    .companyLocation(companyLocation1)
                    .build();

        Member member2
            = Member.builder()
                    .id(2L)
                    .name("회원2")
                    .sex('m')
                    .password("2")
                    .companyName("코드스테이츠2")
                    .companyType(companyType2)
                    .companyLocation(companyLocation2)
                    .build();


        MemberResponseDto memberResponseDto1
            = MemberResponseDto.builder()
                    .id(1L)
                    .name("회원1")
                    .sex('m')
                    .companyName("코드스테이츠1")
                    .companyType(companyType1.getType())
                    .companyLocation(companyLocation1.getLocation())
                    .build();

        MemberResponseDto memberResponseDto2
            = MemberResponseDto.builder()
                    .id(2L)
                    .name("회원2")
                    .sex('w')
                    .companyName("코드스테이츠2")
                    .companyType(companyType2.getType())
                    .companyLocation(companyLocation2.getLocation())
                    .build();

        List<Member> memberList
            = List.of(member1, member2);

        List<MemberResponseDto> memberResponseDtoList
            = List.of(memberResponseDto1, memberResponseDto2);

        //stubbing
        BDDMockito.given(memberService.findMembers(Mockito.any(MemberSearchCond.class)))
            .willReturn(memberList);

        BDDMockito.given(mapper.memberListToMemberResponseDtoList(Mockito.anyList()))
            .willReturn(memberResponseDtoList);

        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/v1/members")
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "get-memberList",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        PayloadDocumentation.responseFields(
                            Arrays.asList(
                                PayloadDocumentation.fieldWithPath("totalCnt").type(JsonFieldType.NUMBER).description("조회 결과 수"),
                                PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.ARRAY).description("조회 결과 데이터"),
                                PayloadDocumentation.fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("회원 번호"),
                                PayloadDocumentation.fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                PayloadDocumentation.fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("회원 성별"),
                                PayloadDocumentation.fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                PayloadDocumentation.fieldWithPath("data[].companyType").type(JsonFieldType.STRING).description("회사 업종"),
                                PayloadDocumentation.fieldWithPath("data[].companyLocation").type(JsonFieldType.STRING).description("회사 지역")
                            )//list
                        )//responseFields
                    )//document
                )
                .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void getMemberListTestWithSearchCond() throws Exception {
        CompanyType companyType = CompanyType.builder()
                                    .id(1L)
                                    .type("요식업")
                                    .build();
        CompanyLocation companyLocation = CompanyLocation.builder()
                                                .id(1L)
                                                .location("서울특별시 강남구")
                                                .build();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("companyTypeNum", String.valueOf(companyType.getId()));
        queryParams.add("companyLocationNum", String.valueOf(companyLocation.getId()));

        Member member1
            = Member.builder()
                    .id(1L)
                    .name("회원1")
                    .sex('m')
                    .password("1111")
                    .companyName("코드스테이츠1")
                    .companyType(companyType)
                    .companyLocation(companyLocation)
                    .build();

        Member member2
            = Member.builder()
                    .id(2L)
                    .name("회원2")
                    .sex('m')
                    .password("2")
                    .companyName("코드스테이츠2")
                    .companyType(companyType)
                    .companyLocation(companyLocation)
                    .build();


        MemberResponseDto memberResponseDto1
            = MemberResponseDto.builder()
                    .id(1L)
                    .name("회원1")
                    .sex('m')
                    .companyName("코드스테이츠1")
                    .companyType(companyType.getType())
                    .companyLocation(companyLocation.getLocation())
                    .build();

        MemberResponseDto memberResponseDto2
            = MemberResponseDto.builder()
                    .id(2L)
                    .name("회원2")
                    .sex('w')
                    .companyName("코드스테이츠2")
                    .companyType(companyType.getType())
                    .companyLocation(companyLocation.getLocation())
                    .build();

        List<Member> memberList
            = List.of(member1, member2);

        List<MemberResponseDto> memberResponseDtoList
            = List.of(memberResponseDto1, memberResponseDto2);

        //stubbing
        BDDMockito.given(memberService.findMembers(Mockito.any(MemberSearchCond.class)))
            .willReturn(memberList);

        BDDMockito.given(mapper.memberListToMemberResponseDtoList(Mockito.anyList()))
            .willReturn(memberResponseDtoList);

        ResultActions actions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/v1/members")
                .params(queryParams)
                .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "get-memberList-condition",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.requestParameters(
                            List.of(
                                RequestDocumentation.parameterWithName("companyTypeNum").description("회사 업종 번호"),
                                RequestDocumentation.parameterWithName("companyLocationNum").description("회사 지역 번호")
                            )//list
                        ),//requestParameters
                        PayloadDocumentation.responseFields(
                            Arrays.asList(
                                PayloadDocumentation.fieldWithPath("totalCnt").type(JsonFieldType.NUMBER).description("조회 결과 수"),
                                PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.ARRAY).description("조회 결과 데이터"),
                                PayloadDocumentation.fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("회원 번호"),
                                PayloadDocumentation.fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                PayloadDocumentation.fieldWithPath("data[].sex").type(JsonFieldType.STRING).description("회원 성별"),
                                PayloadDocumentation.fieldWithPath("data[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
                                PayloadDocumentation.fieldWithPath("data[].companyType").type(JsonFieldType.STRING).description("회사 업종"),
                                PayloadDocumentation.fieldWithPath("data[].companyLocation").type(JsonFieldType.STRING).description("회사 지역")
                            )//list
                        )//responseFields
                    )//document
                )
                .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

}
