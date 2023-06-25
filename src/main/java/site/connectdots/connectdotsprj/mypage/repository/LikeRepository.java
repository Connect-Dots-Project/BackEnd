package site.connectdots.connectdotsprj.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.connectdots.connectdotsprj.member.entity.Member;
import site.connectdots.connectdotsprj.mypage.entity.Like;
import site.connectdots.connectdotsprj.mypage.entity.LikeType;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {


    List<Like> findByMemberAndType(Member member, LikeType hotplace);
}
