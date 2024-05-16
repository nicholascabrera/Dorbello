package com.dorbello.exceptions;

public class ParentNotFoundException extends RuntimeException {

  public ParentNotFoundException(String id) {
    super("Could not find parent: " + id);
  }
}