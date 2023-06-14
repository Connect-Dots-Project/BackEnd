package site.connectdots.connectdotsprj.mypage.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageWriteDTO {

    private String memberNickname;
    private String memberProfile;
    private String memberComment;
    private List<FreeBoard> freeBoardList = new ArrayList<>();
    private List<FreeBoardReply> freeBoardReplyList = new ArrayList<>();

    public MyPageWriteDTO(Member member) {
        this.memberNickname=member.getMemberNickname();
        this.memberProfile=member.getMemberProfile();
        this.memberComment=member.getMemberComment();
        this.freeBoardList=member.getFreeBoardList();
        this.freeBoardReplyList=member.getFreeBoardReplyList();
    }

    }

