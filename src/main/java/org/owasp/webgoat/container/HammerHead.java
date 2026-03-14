/*
 * SPDX-FileCopyrightText: Copyright © 2008 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container;

import lombok.AllArgsConstructor;
import org.owasp.webgoat.container.session.Course;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class HammerHead {

  private final Course course;

  /** Entry point for WebGoat, redirects to the first lesson found within the course. */
  @GetMapping(path = "/attack")
  @PostMapping(path = "/attack")
  public ModelAndView attack() {
    return new ModelAndView("redirect:" + "start.mvc" + course.getFirstLesson().getLink());
  }
}
