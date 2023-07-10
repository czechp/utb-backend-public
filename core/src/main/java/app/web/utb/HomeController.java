package app.web.utb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
class HomeController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    String getMsg() {
        return "Application to manage utb carts - AUTHORIZED";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/open")
    String getMsgOpen() {
        return "Application to manage utb carts - OPEN";
    }
}
