package com.citsh.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class MessageHelper
{
  private MessageSourceAccessor messages;

  public void addFlashMessage(RedirectAttributes redirectAttributes, String code, String text)
  {
    addFlashMessage(redirectAttributes, this.messages.getMessage(code, text));
  }

  public void addFlashMessage(RedirectAttributes redirectAttributes, String text)
  {
    List flashMessages = (List)redirectAttributes
      .getFlashAttributes().get("flashMessages");

    if (flashMessages == null) {
      flashMessages = new ArrayList();
      redirectAttributes
        .addFlashAttribute("flashMessages", flashMessages);
    }

    flashMessages.add(text);
  }

  public void addMessage(Model model, String text) {
    List flashMessages = (List)model.asMap().get("flashMessages");

    if (flashMessages == null) {
      flashMessages = new ArrayList();
      model.addAttribute("flashMessages", flashMessages);
    }

    flashMessages.add(text);
  }
  @Resource
  public void setMessageSource(MessageSource messageSource) {
    this.messages = new MessageSourceAccessor(messageSource);
  }
}