//package site.connectdots.connectdotsprj.mypage.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.*;
//import site.connectdots.connectdotsprj.freeboard.entity.FreeBoard;
//import site.connectdots.connectdotsprj.freeboard.entity.FreeBoardReply;
//import site.connectdots.connectdotsprj.member.entity.Gender;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@Builder
//@Entity
//@EqualsAndHashCode(of = "memberIdx")
//@Table(name = "TB_MEMBER")
//public class MyPage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name = "member_idx")
//    private Long memberIdx;
//    @Column(nullable = false, length = 50, unique = true)
//    private String memberAccount;
//    @Column(nullable = false, length = 2000)
//    private String memberPassword;
//    @Column(nullable = false, length = 50)
//    private String memberName;
//    @Column(nullable = false, length = 30, unique = true)
//    private String memberNickname;
//    @Column(nullable = false, length = 1)
//    private Gender memberGender;
//    @Column(length = 50)
//    private String memberProfile;
//    @JsonFormat(pattern = "yyyy-HH-dd")
//    private LocalDateTime memberBirth;
//    //    @CreationTimestamp
////    @JsonFormat(pattern = "yyyy-HH-dd")
////    private LocalDateTime memberSignDate;
//    @Column(nullable = false, length = 15, unique = true)
//    private String memberPhone;
//    @Column(nullable = false, length = 20)
//    private String memberLocation;
//    @Column(nullable = false, length = 50)
//    private String memberComment;
//
//
//    @OneToMany(mappedBy = "member")
//    @Builder.Default
//    private List<FreeBoard> freeBoardList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member")
//    @Builder.Default
//    private List<FreeBoardReply> freeBoardReplyList = new ArrayList<>();
//
//
//
//
//
//}
