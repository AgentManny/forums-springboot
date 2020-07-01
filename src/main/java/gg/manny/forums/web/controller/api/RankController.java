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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class RankController {

    public static final String AUTH_HEADER = "Web-Authorization";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired private RankRepository rankRepository;

    @RequestMapping(value = "/api/rank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String fetchRank(
            @RequestParam(required = false, defaultValue = "") String id,
            @RequestParam(required = false, defaultValue = "") String name,
            HttpServletRequest request) throws JsonProcessingException {
        String key = request.getHeader(AUTH_HEADER);
        if (key == null || !key.equals(Application.getApiKey())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No key provided");
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
    public void updateRank(@PathVariable String id, @Valid Rank updateRank, HttpServletRequest request) {
        String key = request.getHeader(AUTH_HEADER);
        if (key == null || !key.equals(Application.getApiKey()) || id.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No key provided"); // We it to NOT_FOUND to prevent people from finding this
        }

        Optional<Rank> optionalRank = rankRepository.findById(id);
        optionalRank.ifPresent(rank -> {
            if (!updateRank.getId().equals(rank.getId())) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Mismatched rank update"); // Make sure we're not updating the wrong rank
            }

            rank.sync(updateRank);
            rankRepository.save(rank);
        });
    }

    @PostMapping(value = "/api/rank/create")
    public void createRank(@Valid @RequestBody Rank rank, HttpServletRequest request) {
        String key = request.getHeader(AUTH_HEADER);
        if (key == null || !key.equals(Application.getApiKey())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No key provided"); // We it to NOT_FOUND to prevent people from finding this
        }

        if (rank == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rank not found.");
        }

        if (rankRepository.findById(rank.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rank already exists");
        }

        rankRepository.save(rank);
    }

    @RequestMapping(value = "/api/rank/{id}/delete", method = RequestMethod.DELETE)
    public void deleteRank(@PathVariable String id, HttpServletRequest request) {
        String key = request.getHeader(AUTH_HEADER);
        if (key == null || !key.equals(Application.getApiKey()) || id.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No key provided"); // We it to NOT_FOUND to prevent people from finding this
        }

        Optional<Rank> rank = rankRepository.findById(id);
        if (!rank.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rank not found");
        }

        rankRepository.delete(rank.get());
    }
}
