package com.kh.heallo.domain.reply.dao;


import com.kh.heallo.domain.reply.Reply;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReplyDAOImpl implements ReplyDAO {

  private final JdbcTemplate jdbcTemplate;

  /**
   * 게시글에 해당하는 댓글 목록 조회
   *
   * @return 댓글 목록
   */
  @Override
  public List<Reply> all(Long bdno) {
    StringBuffer sql = new StringBuffer();

    sql.append("SELECT * ");
    sql.append("  FROM REPLY ");
    sql.append("WHERE BDNO = ? ");

    jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Reply.class), bdno);
    return null;
  }

  /**
   * 댓글 등록
   *
   * @param reply 등록할 댓글
   * @return 댓글 번호
   */
  // 게시글 번호 받기
  // 멤버 번호 받기
  @Override
  public Long save(Reply reply) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO REPLY(RPNO, BDNO,MEMNO,RPCOMMENT) ");
    sql.append("     VALUES (REPLY_RPNO_SEQ.nextval, BOARD_BDNO_SEQ.currval, 1, ?) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(), new String[]{"rpno"});
        pstmt.setString(1, reply.getRpComment());
        return pstmt;
      }
    }, keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("rpno").toString());
  }

  /**
   * 대댓글 등록
   *
   * @param reply 등록할 대댓글
   * @return 댓글 번호
   */
  // 부모댓글, 들여쓰기 번호 받기
  @Override
  public Long savePlusReply(Reply reply) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO REPLY ");
    sql.append("     VALUES (REPLY_RPNO_SEQ.NEXTVAL, 1,1(부모댓글),1(들여쓰기), 1, '대댓글', SYSDATE, SYSDATE) ");



    return null;
  }

  /**
   * 댓글 수정
   *
   * @param rpno  수정할 댓글 번호
   * @param reply 수정할 댓글 내용
   */
  @Override
  public void update(Long rpno, Reply reply) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE REPLY ");
    sql.append("   SET RPCOMMENT = ?, ");
    sql.append("       RPUDATE = SYSDATE ");
    sql.append(" WHERE RPNO = ? ");

    jdbcTemplate.update(sql.toString(), reply.getRpComment(), rpno);
  }

  /**
   * 댓글 삭제
   *
   * @param rpno 삭제할 댓글 번호
   */
  @Override
  public void delete(Long rpno) {
    String sql = "DELETE FROM REPLY WHERE RPNO = ? ";

    jdbcTemplate.update(sql, rpno);
  }
}