package com.guava.demo01;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

public class GuavaListeningExecutors {

  public ListeningExecutorService createListeningExecutorService(){
      return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));
  }
}
