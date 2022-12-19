package com.its.ex.dto;

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



}
