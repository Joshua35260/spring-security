package com.wcs.controller;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
  
  @GetMapping("/")
  public String hello() {
      return "redirect:/index.html";
  }

  @GetMapping("/login")
  public String login() {
      return "login.html";
  }
  
  @GetMapping("/avengers/assemble")
  public String onlyChampion(Authentication authentication) {
      if (authentication != null && authentication.isAuthenticated()
              && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("CHAMPION"))) {
          return "redirect:/assemble.html";
      }
      return "redirect:/login";
  }

  @GetMapping("/avengers/secret-bases")
  public String onlyDirector(Authentication authentication) {
      if (authentication != null && authentication.isAuthenticated()
              && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("DIRECTOR"))) {
          return "redirect:/secret-bases.html";
      }
     else { return "redirect:/login";}
  }

}
