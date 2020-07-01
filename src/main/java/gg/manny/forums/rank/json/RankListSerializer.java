package gg.manny.forums.rank.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import gg.manny.forums.rank.Rank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RankListSerializer extends StdSerializer<List<Rank>> {

    public RankListSerializer() {
        this(null);
    }

    public RankListSerializer(Class<List<Rank>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Rank> ranks, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        List<String> rankList = new ArrayList<>();
        ranks.forEach(rank -> rankList.add(rank.getId()));
        jgen.writeObject(rankList);
    }
}
