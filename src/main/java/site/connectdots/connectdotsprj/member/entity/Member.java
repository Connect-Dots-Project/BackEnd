package site.connectdots.connectdotsprj.member.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@EqualsAndHashCode(of = "memberIdx")
@Table(name = "TB_MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;
    @Column(nullable = false, length = 50, unique = true)
    private String memberAccount;
    @Column(nullable = false, length = 2000)
    private String memberPassword;
    @Column(nullable = false, length = 30)
    private String memberNickname;
    @Column(nullable = false, length = 50, unique = true)
    private String memberName;
    @Column(nullable = false, length = 1)
    private Gender memberGender;
    private LocalDateTime memberBirth;
    @CreationTimestamp
    private LocalDateTime memberSignDate;
    @Column(nullable = false, length = 15, unique = true)
    private String memberPhone;
    @Column(nullable = false, length = 20)
    private String memberLocation;
    @Column(nullable = false, length = 50)
    private String memberComment;

}
