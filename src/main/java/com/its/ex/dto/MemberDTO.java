package com.its.ex.dto;

import com.its.ex.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private LocalDateTime memberCreatedDate;
    private LocalDateTime memberUpdateDate;
    private MultipartFile memberFile;
    private String memberProfile;
    private String originalFileName;
    private String storedFileName;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberCreatedDate(memberEntity.getCreatedTime());
        memberDTO.setMemberUpdateDate(memberEntity.getUpdatedTime());
        if (memberEntity.getMemberProfile() == "Y") {
            memberDTO.setMemberProfile(memberEntity.getMemberProfile());
            memberDTO.setOriginalFileName(memberEntity.getOriginalFileName());
            memberDTO.setStoredFileName(memberEntity.getStoredFileName());
        } else {
            memberDTO.setMemberProfile(memberEntity.getMemberProfile());

        }
        return memberDTO;
    }


}
