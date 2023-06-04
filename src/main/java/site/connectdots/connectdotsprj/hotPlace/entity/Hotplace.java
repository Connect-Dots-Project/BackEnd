package site.connectdots.connectdotsprj.hotPlace.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@EqualsAndHashCode(of= "hotplaceIdx")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_hotplace")
public class Hotplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotplaceIdx ;

    @Column(nullable = false, length = 200)
    @Builder.Default
    private String hotplaceImg = "사진없음";

    @Column(nullable = false, length = 100)
    private String hotplaceContent;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private HotplaceLocation hotplaceLocation;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime hotplaceWriteDate;

    @Builder.Default
    private Long hotplaceLikeCount = 0L;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String hotplaceLatitude = "0.0000";

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String hotplaceLongitude = "0.0000";

    @Column(nullable = false, length = 10)
    private Long memberIdx;
}
