package site.connectdots.connectdotsprj.cvs.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import site.connectdots.connectdotsprj.cvs.entity.Cvs;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CvsRepositoryTest {
    @Autowired
    CvsRepository cvsRepository;

    @Test
    @DisplayName("1+1 행사상품의 조회에 성공할 것이다")
    void findOnePlusOneItem() {
        //given
        String sale = "1+1";
        //when
        List<Cvs> itemList = cvsRepository.findAllByCvsSale(sale);
        //then
        assertNotNull(itemList);
        assertEquals(163, itemList.size());
    }

    @Test
    @DisplayName("CU 상품의 조회에 성공할 것이다")
    void findCUItem() {
        //given
        String cvs = "CU";
        //when
        List<Cvs> itemList = cvsRepository.findAllByCvsType(cvs);
        //then
        assertNotNull(itemList);
        assertEquals(80, itemList.size());
    }
}