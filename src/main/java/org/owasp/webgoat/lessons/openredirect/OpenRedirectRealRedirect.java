/*
 * SPDX-FileCopyrightText: Copyright © 2025 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.openredirect;

import java.net.URI;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Provides a real 302 redirect for experimentation separate from assignment scoring.
 */
@Controller
public class OpenRedirectRealRedirect {

  private static final Set<String> ALLOWED_REDIRECT_SCHEMES = Set.of("http", "https");
  private static final Set<String> ALLOWED_REDIRECT_HOSTS =
      Set.of("localhost", "127.0.0.1", "webgoat.org", "owasp.org");

  @GetMapping("/OpenRedirect/realRedirect")
  public ModelAndView real(@RequestParam("url") String url) {
    try {
      URI uri = URI.create(url);
      String scheme = uri.getScheme();
      String host = uri.getHost();
      if (scheme == null || !ALLOWED_REDIRECT_SCHEMES.contains(scheme.toLowerCase())) {
        return new ModelAndView("redirect:/WebGoat/start.mvc");
      }
      if (host == null || !ALLOWED_REDIRECT_HOSTS.contains(host.toLowerCase())) {
        return new ModelAndView("redirect:/WebGoat/start.mvc");
      }
      return new ModelAndView("redirect:" + url);
    } catch (Exception e) {
      return new ModelAndView("redirect:/WebGoat/start.mvc");
    }
  }
}
