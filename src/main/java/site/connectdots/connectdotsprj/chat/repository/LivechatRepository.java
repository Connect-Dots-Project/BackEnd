package site.connectdots.connectdotsprj.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.connectdots.connectdotsprj.chat.entity.Livechat;

import java.util.List;
import java.util.Optional;

public interface LivechatRepository extends JpaRepository<Livechat, Long> {
    @Query("SELECT lc.livechatHashtag " +
            "FROM Livechat lc " +
            "GROUP BY lc.livechatHashtag " +
            "ORDER BY COUNT(lc) DESC")
    List<String> findHashtagsOrderByCount();

    List<Livechat> findAllByOrderByLivechatIdxDesc();

    List<Livechat> findAllByLivechatHashtag(String hashtag);

    List<Livechat> findAllByLivechatHashtagOrderByLivechatIdxDesc(String hashtag);

    Long countByMemberNickname(String memberNickname);

    void deleteByMemberNickname(String memberNickname);

    Optional<Livechat> findByMemberNickname(String memberNickname);


}
