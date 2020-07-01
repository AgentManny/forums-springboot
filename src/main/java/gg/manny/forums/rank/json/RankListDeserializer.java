package gg.manny.forums.rank.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import gg.manny.forums.rank.Rank;
import gg.manny.forums.rank.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RankListDeserializer extends JsonDeserializer<List<Rank>> {

    @Autowired private RankRepository rankRepository;

    @Override
    public List<Rank> deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.getCurrentToken() == JsonToken.START_ARRAY) {
            List<Rank> ranks = new ArrayList<>();
            while(parser.nextToken() != JsonToken.END_ARRAY) {
                rankRepository.findById(parser.getValueAsString()).ifPresent(ranks::add);
            }
            return ranks;
        }
        throw context.mappingException("Expected Permissions list");
    }
}
