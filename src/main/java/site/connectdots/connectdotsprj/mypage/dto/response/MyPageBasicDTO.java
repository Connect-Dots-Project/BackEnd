package site.connectdots.connectdotsprj.mypage.dto.response;

import lombok.*;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
import site.connectdots.connectdotsprj.hotplace.entity.Hotplace;
import site.connectdots.connectdotsprj.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageBasicDTO {

    private String memberNickname;
    private String memberProfile;
    private String memberComment;
    private List<Hotplace> hotPlaceList = new ArrayList<>();
    private List<FreeBoard> freeBoardList = new ArrayList<>();
    private List<FreeBoardReply> freeBoardReplyList = new ArrayList<>();

    public MyPageBasicDTO(Member member) {
        this.memberNickname=member.getMemberNickname();
        this.memberProfile=member.getMemberProfile();
        this.memberComment=member.getMemberComment();
        this.freeBoardList=member.getFreeBoardList();
        this.freeBoardReplyList=member.getFreeBoardReplyList();
    }

    }

