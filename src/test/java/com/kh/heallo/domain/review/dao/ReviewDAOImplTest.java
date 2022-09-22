package com.kh.heallo.domain.review.dao;


import com.kh.heallo.domain.review.OrderBy;
import com.kh.heallo.domain.review.Review;
import com.kh.heallo.domain.review.ReviewCriteria;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewDAOImplTest {

    @Autowired
    private ReviewDAO reviewDAO;
    private static Review review;

    @Order(1)
    @Test
    @DisplayName("리뷰추가")
    void add() {
        review = new Review();
        review.setRvcontents("시설이 너무 좋습니다 적극 추천드립니다");
        review.setRvscore(4.5);
        review.setFcno(1L);
        review.setMemno(1L);
        Long rvno = reviewDAO.add(review);
        review.setRvno(rvno);

        assertThat(rvno).isNotNull();
    }

    @Order(2)
    @Test
    @DisplayName("리뷰조회")
    void findByRvno() {
        Review foundReview = reviewDAO.findByRvno(review.getRvno());

        assertThat(foundReview.getRvno()).isEqualTo(review.getRvno());
        assertThat(foundReview.getRvcontents()).isEqualTo(review.getRvcontents());
        assertThat(foundReview.getRvscore()).isEqualTo(review.getRvscore());
    }

    @Order(3)
    @Test
    @DisplayName("리뷰수정")
    void update() {
        Review newReview = new Review();
        newReview.setRvcontents("시설이 너무 좋습니다 적극 추천드립니다 수정!");
        newReview.setRvscore(2.5);
        Integer resultCount = reviewDAO.update(review);
        Review foundReview = reviewDAO.findByRvno(review.getRvno());

        assertThat(resultCount).isEqualTo(1);
        assertThat(foundReview.getRvcontents()).isEqualTo(review.getRvcontents());
        assertThat(foundReview.getRvscore()).isEqualTo(review.getRvscore());
    }

    @Order(4)
    @Test
    @DisplayName("운동시설의 리뷰 목록 리스트 조회")
    void findListByFcno() {
        ReviewCriteria criteria = new ReviewCriteria();
        criteria.setPageNo(1);
        criteria.setNumOfRow(5);
        criteria.setOrderBy(OrderBy.DATE_ASC.getOrderBy());
        List<Review> reviewList = reviewDAO.findListByFcno(1L, criteria);
        Review foundReview = reviewDAO.findByRvno(review.getRvno());
        reviewList.stream().forEach(ele -> ele.setMember(null));

        assertThat(reviewList).contains(foundReview);
    }

    @Order(5)
    @Test
    @DisplayName("운동시설의 리뷰 전체 결과 수")
    void getTotalCount() {
        Integer totalCount = reviewDAO.getTotalCount(1L);

        assertThat(totalCount).isEqualTo(1);
    }

    @Order(6)
    @Test
    @DisplayName("리뷰삭제")
    void delete() {
        Integer resultCount = reviewDAO.delete(review.getRvno());

        assertThat(resultCount).isEqualTo(1);
        assertThrows(DataAccessException.class, () -> {
            Review foundReview = reviewDAO.findByRvno(review.getRvno());
        });
    }
}