/*
 * SPDX-FileCopyrightText: Copyright © 2019 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.dummy.insecure.framework;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// TODO move back to lesson
public class VulnerableTaskHolder implements Serializable {

  private static final long serialVersionUID = 2;

  private String taskName;
  private String taskAction;
  private LocalDateTime requestedExecutionTime;

  public VulnerableTaskHolder(String taskName, String taskAction) {
    super();
    this.taskName = taskName;
    this.taskAction = taskAction;
    this.requestedExecutionTime = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "VulnerableTaskHolder [taskName="
        + taskName
        + ", taskAction="
        + taskAction
        + ", requestedExecutionTime="
        + requestedExecutionTime
        + "]";
  }

  /**
   * Execute a task when de-serializing a saved or received object.
   */
  private void readObject(ObjectInputStream stream) throws Exception {
    // unserialize data so taskName and taskAction are available
    stream.defaultReadObject();

    // do something with the data
    log.info("restoring task: {}", taskName);
    log.info("restoring time: {}", requestedExecutionTime);

    if (requestedExecutionTime != null
        && (requestedExecutionTime.isBefore(LocalDateTime.now().minusMinutes(10))
            || requestedExecutionTime.isAfter(LocalDateTime.now()))) {
      // do nothing is the time is not within 10 minutes after the object has been created
      log.debug(this.toString());
      throw new IllegalArgumentException("outdated");
    }

    // Safe implementation: simulate delay for "sleep N" instead of executing arbitrary commands
    if (taskAction != null && taskAction.startsWith("sleep") && taskAction.length() < 22) {
      try {
        String[] parts = taskAction.trim().split("\\s+");
        if (parts.length == 2 && parts[0].equals("sleep")) {
          int seconds = Integer.parseInt(parts[1]);
          if (seconds >= 1 && seconds <= 10) {
            log.info("simulating sleep: {} seconds", seconds);
            Thread.sleep(seconds * 1000L);
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        log.error("Sleep interrupted", e);
      } catch (NumberFormatException e) {
        log.debug("Invalid sleep format: {}", taskAction);
      }
    }
  }
}
