/*
 * pragmatickm-password-view - SemanticCMS view of all passwords in the current page and all children.
 * Copyright (C) 2016, 2017, 2020, 2021, 2022, 2023, 2025  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of pragmatickm-password-view.
 *
 * pragmatickm-password-view is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pragmatickm-password-view is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with pragmatickm-password-view.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.pragmatickm.password.view;

import com.aoapps.html.servlet.FlowContent;
import com.aoapps.servlet.http.Dispatcher;
import com.pragmatickm.password.model.Password;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.servlet.PageUtils;
import com.semanticcms.core.servlet.SemanticCMS;
import com.semanticcms.core.servlet.View;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.SkipPageException;

/**
 * View of all passwords in the current page and all children.
 */
public final class PasswordView extends View {

  public static final String NAME = "passwords";

  private static final String JSPX_TARGET = "/pragmatickm-password-view/view.inc.jspx";

  /**
   * Registers the "{@link #NAME}" view in {@link SemanticCMS}.
   */
  @WebListener("Registers the \"" + NAME + "\" view in SemanticCMS.")
  public static class Initializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
      SemanticCMS.getInstance(event.getServletContext()).addView(new PasswordView());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
      // Do nothing
    }
  }

  private PasswordView() {
    // Do nothing
  }

  @Override
  public Group getGroup() {
    return Group.VARIABLE;
  }

  @Override
  public String getDisplay() {
    return "Passwords";
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public boolean isApplicable(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, Page page) throws ServletException, IOException {
    return PageUtils.hasElement(servletContext, request, response, page, Password.class, true);
  }

  @Override
  public String getDescription(Page page) {
    return null;
  }

  @Override
  public String getKeywords(Page page) {
    return null;
  }

  /**
   * We'll assume nobody wants passwords indexed by search engines, even if they are
   * somehow actually published online.
   */
  @Override
  public boolean getAllowRobots(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response, Page page) {
    // return !page.filterElements(Password.class).isEmpty();
    return false;
  }

  @Override
  public <__ extends FlowContent<__>> void doView(
      ServletContext servletContext,
      HttpServletRequest request,
      HttpServletResponse response,
      __ flow,
      Page page
  ) throws ServletException, IOException, SkipPageException {
    Dispatcher.include(
        servletContext,
        JSPX_TARGET,
        request,
        response,
        Collections.singletonMap("page", page)
    );
  }
}
