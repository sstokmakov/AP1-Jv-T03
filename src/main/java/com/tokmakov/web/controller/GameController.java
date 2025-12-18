package com.tokmakov.web.controller;

import com.tokmakov.domain.model.Game;
import com.tokmakov.domain.service.GameService;
import com.tokmakov.web.mapper.GameDtoMapper;
import com.tokmakov.web.mapper.GameFieldMapper;
import com.tokmakov.web.model.GameDto;
import com.tokmakov.web.model.GameUpdateRequest;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService service;
    private final GameDtoMapper mapper;
    private final GameFieldMapper fieldMapper;

    @PostMapping("/createGame")
    public GameDto createGame() {
        Game game = service.createGame();
        return mapper.toDto(game);
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    public GameDto getGame(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid) {
        Game game = service.gameByUuid(uuid);
        return mapper.toDto(game);
    }

    @PostMapping("/{uuid}")
    public GameDto processTurn(@PathVariable("uuid") @Pattern(regexp = "[0-9a-fA-F-]{36}") String uuid,
                               @RequestBody GameUpdateRequest request) {
        int[][] domainField = fieldMapper.toDomainField(request);
        Game game = service.processTurn(uuid, domainField);
        return mapper.toDto(game);
    }
}
