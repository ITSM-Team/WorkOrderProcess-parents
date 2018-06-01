package com.citsh.humantask;

public class CounterSignDTO
{
  private String type;
  private String participants;
  private String strategy;
  private int rate;

  public String getType()
  {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getParticipants() {
    return this.participants;
  }

  public void setParticipants(String participants) {
    this.participants = participants;
  }

  public String getStrategy() {
    return this.strategy;
  }

  public void setStrategy(String strategy) {
    this.strategy = strategy;
  }

  public int getRate() {
    return this.rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }
}