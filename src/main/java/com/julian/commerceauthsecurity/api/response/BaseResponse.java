package com.julian.commerceauthsecurity.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class BaseResponse {
  private Object response;
  private int status = 200;
  private String message;
  private boolean success = true;
  private List<String> errorResponse;
}
