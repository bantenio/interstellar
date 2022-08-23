package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.ReadPreference;
import com.mongodb.Tag;
import com.mongodb.TagSet;
import org.tenio.interstellar.mongo.config.MongoClientProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadPreferenceParser {
    private final ReadPreference readPreference;

    public ReadPreferenceParser(ConnectionString connectionString, MongoClientProperties config) {
        ReadPreference connStringReadPreference = connectionString != null ? connectionString.getReadPreference() : null;
        if (connStringReadPreference != null) {
            // Prefer connection string's read preference
            readPreference = connStringReadPreference;
        } else {
            ReadPreference rp;
            MongoClientProperties.ReadPreferenceProperties readPreference = config.getReadPreference();
            String rps = readPreference.getReadPreference();
            if (rps != null) {
                List<String> readPreferenceTags = readPreference.getReadPreferenceTags();
                if (readPreferenceTags == null) {
                    rp = ReadPreference.valueOf(rps);
                    if (rp == null) throw new IllegalArgumentException("Invalid ReadPreference " + rps);
                } else {
                    // Support advanced ReadPreference Tags
                    List<TagSet> tagSet = new ArrayList<>();
                    readPreferenceTags.forEach(o -> {
                        String tagString = (String) o;
                        List<Tag> tags = Stream.of(tagString.trim().split(","))
                                .map(s -> s.split(":"))
                                .filter(array -> {
                                    if (array.length != 2) {
                                        throw new IllegalArgumentException("Invalid readPreferenceTags value '" + tagString + "'");
                                    }
                                    return true;
                                }).map(array -> new Tag(array[0], array[1])).collect(Collectors.toList());

                        tagSet.add(new TagSet(tags));
                    });
                    rp = ReadPreference.valueOf(rps, tagSet);
                }
            } else {
                rp = null;
            }

            this.readPreference = rp;
        }
    }

    public ReadPreference readPreference() {
        return readPreference;
    }
}
