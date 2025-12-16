package com.tokmakov.web.controller;

import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.service.GameServiceImpl;
import com.tokmakov.web.mapper.GameDtoMapper;
import com.tokmakov.web.model.GameDto;
import com.tokmakov.web.model.MoveRequest;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameServiceImpl service;
    private final GameDtoMapper mapper;

    @PostMapping("/createGame")
    public UUID createGame() {
        return service.createGame();
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    public GameDto getGame(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid) {
        Game game = service.gameByUuid(uuid);
        return mapper.toDto(game);
    }

    @PostMapping("/{uuid}")
    public GameDto makeMove(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid,
                            @RequestBody MoveRequest moveRequest) {
        Game game = service.makeMove(uuid, moveRequest.getX(), moveRequest.getY());
        return mapper.toDto(game);
    }

    @PostMapping("/computerMove/{uuid}")
    public GameDto makeComputerMove(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid) {
        Game game = service.makeComputerMove(uuid);
        return mapper.toDto(game);
    }
}
