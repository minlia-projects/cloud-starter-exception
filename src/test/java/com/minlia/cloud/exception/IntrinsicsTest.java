package com.minlia.cloud.exception;

import static org.junit.Assert.*;

import com.minlia.cloud.stateful.body.impl.SuccessResponseBody;
import com.minlia.cloud.stateful.code.ApiCode;
import java.util.Date;
import java.util.Objects;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class IntrinsicsTest {

  @Test
  public void isNull_Then_Success() throws Exception {

//    SuccessResponseBody successResponseBody=new SuccessResponseBody();
//    successResponseBody.setCode(100);

    Date date=null;
    Intrinsics.isNull(date, ApiCode.NOT_NULL, HttpStatus.OK);


  }

  @Test
  public void isNull1() throws Exception {
  }

  @Test
  public void isNotNull() throws Exception {
  }

  @Test
  public void isNotNull1() throws Exception {
  }

  @Test
  public void isNull2() throws Exception {
  }

  @Test
  public void isNull3() throws Exception {
  }

  @Test
  public void isNotNull2() throws Exception {
  }

  @Test
  public void isNotNull3() throws Exception {
  }

  @Test
  public void is() throws Exception {
  }

  @Test
  public void is1() throws Exception {
  }

  @Test
  public void not() throws Exception {
  }

  @Test
  public void not1() throws Exception {
  }

  @Test
  public void throwException() throws Exception {
  }

  @Test
  public void throwException1() throws Exception {
  }

  @Test
  public void throwException2() throws Exception {
  }

}