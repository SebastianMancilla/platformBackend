package jsmg.com.platformbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExc extends RuntimeException{
    public BadRequestExc (String message){
        super(message);
    }
}
