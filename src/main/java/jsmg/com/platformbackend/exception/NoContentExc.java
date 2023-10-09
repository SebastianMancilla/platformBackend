package jsmg.com.platformbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentExc extends  RuntimeException{
    public NoContentExc( String message){
        super(message);
    }
}
