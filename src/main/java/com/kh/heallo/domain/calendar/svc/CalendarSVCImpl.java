package com.kh.heallo.domain.calendar.svc;

import com.kh.heallo.domain.calendar.Calendar;
import com.kh.heallo.domain.calendar.dao.CalendarDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarSVCImpl implements CalendarSVC{
  private final CalendarDAO calendarDAO;

  /**
   * 운동기록 등록
   *
   * @param date     운동기록 날짜
   * @param calendar 등록 내용
   * @return 등록 건수
   */
  @Override
  public Long save(String date, Calendar calendar) {
    return calendarDAO.save(date, calendar);
  }


  @Override
  public Long save(String date, Calendar calendar, List<MultipartFile> files) {
    Long cdno = calendarDAO.save(date, calendar);
//    uploadFileSVC.fileUpload(AttachCode.CD_CODE, cdno, files);

    return cdno;
  }

  /**
   * 운동기록 조회(1회)
   *
   * @param date 조회 날짜
   * @return 조회 내용
   */
  @Override
  public Optional<Calendar> findByDate(String date) {
    return calendarDAO.findByDate(date);
  }

  /**
   * 운동기록 수정
   *
   * @param date     수정 날짜
   * @param calendar 수정 내용
   */
  @Override
  public void update(String date, Calendar calendar) {
    calendarDAO.update(date, calendar);
  }

  /**
   * 운동기록 삭제
   *
   * @param date 삭제 날짜
   */
  @Override
  public void del(String date) {
    calendarDAO.del(date);
  }

  /**
   * 달력 조회(1달)
   *
   * @param year 조회 달 첫째날
   * @param month 조회 달 마지막 날
   * @return
   */
  @Override
  public List<Calendar> monthly(String year, String month) {
    return calendarDAO.monthly(year, month);
  }
}
