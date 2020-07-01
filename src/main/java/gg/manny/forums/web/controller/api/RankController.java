package gg.manny.forums.web.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gg.manny.forums.Application;
import gg.manny.forums.rank.Rank;
import gg.manny.forums.rank.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class RankController {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired private RankRepository rankRepository;

    @RequestMapping(value = "/api/rank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String fetchRank(@RequestParam(required = false, defaultValue = "") String key, @RequestParam(required = false, defaultValue = "") String id, @RequestParam(required = false, defaultValue = "") String name) throws JsonProcessingException {
        if (key.isEmpty() || !key.equals(Application.getApiKey())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No key provided"); // We it to NOT_FOUND to prevent people from finding this
        }

        Optional<Rank> rank = Optional.empty();
        if (!id.isEmpty()) {
            rank = rankRepository.findByIdIgnoreCase(id);
        } else if (!name.isEmpty()) {
            rank = rankRepository.findByNameIgnoreCase(name);
        }

        if (rank.isPresent()) {
            return MAPPER.writeValueAsString(rank.get());
        }

        return MAPPER.writeValueAsString(rankRepository.findAll());
    }

    @RequestMapping(value = "/api/rank/{id}/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRank(@RequestParam(required = false, defaultValue = "") String key, @PathVariable String id, @Valid Rank updateRank) {
        if (id.isEmpty() || key.isEmpty() || !key.equals(Application.getApiKey())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No key provided"); // We it to NOT_FOUND to prevent people from finding this
        }

        Optional<Rank> optionalRank = rankRepository.findById(id);
        optionalRank.ifPresent(rank -> {
            if (!updateRank.getId().equals(rank.getId())) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Mismatched rank update"); // Make sure we're not updating the wrong rank
            }

            rank.sync(updateRank);
        });
    }
}
