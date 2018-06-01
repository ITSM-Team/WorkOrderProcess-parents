package com.citsh.rule.config;

public class PrefixRuleMatcher
  implements RuleMatcher
{
  private String prefix;
  private int prefixLength;
  private String separator = ":";

  public PrefixRuleMatcher(String prefix) {
    if (prefix == null) {
      throw new IllegalArgumentException("prefix cannot be null");
    }

    if ("".equals(prefix.trim())) {
      throw new IllegalArgumentException("prefix cannot be blank");
    }

    if (!prefix.endsWith(this.separator))
      this.prefix = (prefix + this.separator);
    else {
      this.prefix = prefix;
    }

    this.prefixLength = this.prefix.length();
  }

  public boolean matches(String text) {
    if (text == null) {
      throw new IllegalArgumentException("text cannot be null");
    }

    return text.startsWith(this.prefix);
  }

  public String getValue(String text) {
    return text.substring(this.prefixLength);
  }

  public String getPrefix() {
    return this.prefix;
  }

  public String getSeparator() {
    return this.separator;
  }
}