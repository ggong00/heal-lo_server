package com.kh.heallo.web;

import com.kh.heallo.domain.facility.Facility;
import com.kh.heallo.domain.facility.FacilityCriteria;
import com.kh.heallo.domain.review.Review;
import com.kh.heallo.domain.review.ReviewCriteria;
import com.kh.heallo.web.facility.dto.FacilityDto;
import com.kh.heallo.web.review.dto.AddReviewForm;
import com.kh.heallo.web.review.dto.ReviewDto;
import com.kh.heallo.web.review.dto.ReviewFileData;
import com.kh.heallo.web.review.dto.SearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoModifier {

    //SearchCriteria => FacilityCriteria
    public FacilityCriteria getFacilityCriteria(com.kh.heallo.web.facility.dto.SearchCriteria searchCriteria) {
        FacilityCriteria criteria = new FacilityCriteria();
        criteria.setFctype(searchCriteria.getFctype());
        criteria.setFcaddr(searchCriteria.getFcaddr());
        criteria.setFcname(searchCriteria.getFcname());
        criteria.setPageNo(searchCriteria.getPageNo());
        criteria.setNumOfRow(searchCriteria.getNumOfRow());
        return criteria;
    }

    //List<Review> => List<ReviewDto>
    public List<ReviewDto> getReviewDtos(List<Review> FoundReviewList, Long memno) {
        List<ReviewDto> reviewList = FoundReviewList.stream().map(review -> {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setRvno(review.getRvno());
            reviewDto.setMemno(review.getMemno());
            reviewDto.setRvscore(review.getRvscore());
            reviewDto.setRvcontents(review.getRvcontents());
            reviewDto.setRvcdate(review.getRvcdate());
            reviewDto.setMemninkname(review.getMemninkname());
            if (review.getMemno() == memno) reviewDto.setLogin(true);

            if(review.getAttachedImage() != null) {
                List<ReviewFileData> reviewFileDataList = review.getAttachedImage().stream().map(fileData -> {
                    ReviewFileData reviewFileData = new ReviewFileData();
                    reviewFileData.setOriginFileName(fileData.getUffname());
                    reviewFileData.setLocalFileName(fileData.getUfsname());
                    return reviewFileData;
                }).collect(Collectors.toList());
                reviewDto.setImageFiles(reviewFileDataList);
            }

            return reviewDto;
        }).collect(Collectors.toList());

        return reviewList;
    }

    //SearchCriteria => ReviewCriteria
    public ReviewCriteria getReviewCriteria(SearchCriteria searchCriteria) {
        ReviewCriteria reviewCriteria = new ReviewCriteria();
        reviewCriteria.setPageNo(searchCriteria.getPageNo());
        reviewCriteria.setNumOfRow(searchCriteria.getNumOfRow());
        return reviewCriteria;
    }

    //Facility => facilityDto
    public FacilityDto getFacilityDto(Facility foundFacility) {
        FacilityDto facilityDto = new FacilityDto();
        facilityDto.setFcname(foundFacility.getFcname());
        facilityDto.setFcaddr(foundFacility.getFcaddr());
        facilityDto.setFcimg(foundFacility.getFcimg());
        facilityDto.setFcno(foundFacility.getFcno());
        facilityDto.setFctel(foundFacility.getFctel());
        return facilityDto;
    }

    //AddReviewForm => Review
    public Review getReview(AddReviewForm addReviewForm) {
        Review review = new Review();
        review.setRvcontents(addReviewForm.getRvcontents());
        review.setRvscore(addReviewForm.getRvscore());
        return review;
    }
}
