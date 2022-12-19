package com.its.ex.service;

import com.its.ex.dto.MemberDTO;
import com.its.ex.entity.MemberEntity;
import com.its.ex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) throws IOException {
        if (memberDTO.getMemberFile().isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            memberRepository.save(memberEntity);
        } else {
            MultipartFile memberFile = memberDTO.getMemberFile();
            String originalFileName = memberFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "D:\\boot_final_profile\\" + storedFileName;
            memberFile.transferTo(new File(savePath));
            memberDTO.setOriginalFileName(originalFileName);
            memberDTO.setStoredFileName(storedFileName);
            MemberEntity memberEntity = MemberEntity.toSaveFileEntity(memberDTO);
            memberRepository.save(memberEntity);
        }

    }

    public String duplicateCheck(String inputEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(inputEmail);
        if (optionalMemberEntity.isEmpty()) {
            return "Y";
        } else {
            return null;
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        String memberEmail = memberDTO.getMemberEmail();
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).get();
        if (memberEntity != null) {
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toDTO(memberEntity);
            } else {
                return null;
            }

        } else {
            return null;
        }
    }
}
































