package io.github.usalko.sy.controller;

import io.github.usalko.sy.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping("/api/Token")
public class TokenController {

    TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping(value = {""})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Request new token")
    public @NotNull String generate(@RequestParam("user-agent-hash") int userAgentHash,
                                    @RequestParam("seed") int randomSeed) {
        return this.tokenService.generate(userAgentHash, randomSeed).getId();
    }

    @GetMapping(value = {"/Validation"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Check token")
    public @NotNull boolean validation(String token) {
        return this.tokenService.isValid(token);
    }

}