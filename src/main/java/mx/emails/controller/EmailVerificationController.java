package mx.emails.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EmailVerificationController {

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String email) {
        boolean isValid = checkEmailValidity(email);

        if (isValid) {
            return ResponseEntity.ok("Email is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is invalid.");
        }
    }

    private boolean checkEmailValidity(String email) {
        try {
            String url = "https://mxtoolbox.com/SuperTool.aspx?action=mx%3a" + email;
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("#ctl00_ContentPlaceHolder1_lblValid");

            if (elements.isEmpty()) {
                return false;
            }

            String validationStatus = elements.first().text();
            return validationStatus.equalsIgnoreCase("Valid");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
