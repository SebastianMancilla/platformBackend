package jsmg.com.platformbackend.controller;

import jsmg.com.platformbackend.domain.AuthorizedChannel;
import jsmg.com.platformbackend.dto.AuthorizedChannelDTO;
import jsmg.com.platformbackend.service.AuthorizedChannelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorized-channel")
@CrossOrigin( origins = "http://localhost:4200")
@AllArgsConstructor
public class AuthorizedChannelController {
    private final AuthorizedChannelService authorizedChannelService;

    @PostMapping(path = "/")
    public ResponseEntity<AuthorizedChannel> createAuthorizedChannel(@RequestBody AuthorizedChannelDTO authorizedChannelDTO){
        return new ResponseEntity<>(authorizedChannelService.save(authorizedChannelDTO), HttpStatus.CREATED);
    }
    @GetMapping(path = "/")
    public ResponseEntity<List<AuthorizedChannel>> getChannels(){
        return new ResponseEntity<>(authorizedChannelService.findAll(), HttpStatus.OK);
    }
    @GetMapping(path = "/{code}")
    public ResponseEntity<AuthorizedChannelDTO> getChannelByCode(@PathVariable String code){
        return new ResponseEntity<>(authorizedChannelService.findByCode(code), HttpStatus.OK);
    }
    @PatchMapping(path = "/{code}")
    public ResponseEntity<AuthorizedChannel> updateChannel(@PathVariable String code, @RequestBody AuthorizedChannelDTO authorizedChannelDTO){
        return new ResponseEntity<>(authorizedChannelService.update(authorizedChannelDTO, code), HttpStatus.OK);
    }
    @DeleteMapping(path = "/{code}")
    public ResponseEntity<AuthorizedChannel> deleteChannelByCode(@PathVariable String code){
        authorizedChannelService.deleteByCode(code);
        return  new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
}
