/*
 * SPDX-FileCopyrightText: Copyright © 2017 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.webwolf.requests;

import jakarta.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/landing/**")
public class LandingPage {

  @GetMapping
  @PostMapping
  @DeleteMapping
  @PatchMapping
  @PutMapping
  public Callable<ResponseEntity<?>> ok(HttpServletRequest request) {
    return () -> {
      log.trace("Incoming request for: {}", request.getRequestURL());
      return ResponseEntity.ok().build();
    };
  }
}
