// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
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