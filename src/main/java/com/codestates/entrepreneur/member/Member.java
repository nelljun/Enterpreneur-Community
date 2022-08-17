package com.codestates.entrepreneur.member;

import com.codestates.entrepreneur.company.CompanyLocation;
import com.codestates.entrepreneur.company.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
    private Character sex;

    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_type_id")
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_location_id")
    @Column(name = "company_location", nullable = false)
    private CompanyLocation companyLocation;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
