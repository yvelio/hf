package com.holdemfactory.history.client;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class HistoryExceptionMapper implements ResponseExceptionMapper<HistoryNotFoundException> {
  @Override
  public HistoryNotFoundException toThrowable(Response response) {
    return new HistoryNotFoundException("Failed to retrieve account");
  }

  @Override
  public boolean handles(int status, MultivaluedMap<String, Object> headers) {
    return status == 404;
  }
}
