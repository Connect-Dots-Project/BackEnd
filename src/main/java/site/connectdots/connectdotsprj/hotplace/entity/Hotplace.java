package site.connectdots.connectdotsprj.hotplace.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import site.connectdots.connectdotsprj.global.enums.Location;
import site.connectdots.connectdotsprj.hotplace.dto.requestDTO.HotplaceModifyRequestDTO;
import site.connectdots.connectdotsprj.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString(exclude = {"member"})
@EqualsAndHashCode(of = "hotplaceIdx")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_hotplace")
public class Hotplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotplaceIdx;

//    @Column(nullable = false, length = 200)
    @Builder.Default
    private String hotplaceImg = "사진없음";

//    @Column(nullable = false, length = 100)
    private String hotplaceContent;

//    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Location location;

    @CreationTimestamp
//    @Column(nullable = false)
    private LocalDateTime hotplaceWriteDate;

    @Builder.Default
    private Long hotplaceLikeCount = 0L;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String hotplaceLatitude = "0.0000";

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String hotplaceLongitude = "0.0000";

    @Column(nullable = false)
    private String hotplaceName;

    @Column(nullable = false)
    private String hotplaceFullAddress;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_idx", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Member member;


}
