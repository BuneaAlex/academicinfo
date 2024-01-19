package com.dolcevita.academicinfo.config.faculty;

import com.dolcevita.academicinfo.model.User;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Branch {
    private final User.Language language;
    private final int groupPrefix;
    private final int groups;
    private final String identifier;
    private Map<String, Set<String>> groupPartitions;
    private Map<Integer, String> formations;

    public Branch(final String lang, final int groupPrefix, final int groups, final String identifier) {
        this.groupPrefix = groupPrefix;
        this.identifier = identifier;
        this.groups = groups;
        this.language = User.Language.fromShortened(lang).orElse(null);
    }

    void generateFormations(Integer years, Integer splitFactor) {
        groupPartitions = IntStream.rangeClosed(1, years)
                .boxed()
                .flatMap(year -> IntStream.rangeClosed(1, groups)
                        .mapToObj(group -> String.format("%d%d%d", groupPrefix, year, group)))
                .collect(Collectors.toMap(
                        generatedGroup -> generatedGroup,
                        (generatedGroup) -> IntStream.rangeClosed(1, splitFactor)
                        .mapToObj(semigroup -> String.format("%s/%d", generatedGroup, semigroup))
                                .collect(Collectors.toSet())));
        formations = IntStream.rangeClosed(1, years)
                .boxed()
                .collect(Collectors.toMap(
                        year -> year,
                        (year) -> String.format("%s%d", identifier, year)
                ));
    }

    public Optional<String> getSemigroup(Integer group, Integer semigroup) {
        return groupPartitions.get(group.toString())
                .stream()
                .filter(sg -> Objects.equals(sg, String.format("%d/%d", group, semigroup)))
                .findFirst();
    }

    public Optional<String> getFormationForYear(Integer year) {
        return Optional.ofNullable(formations.get(year));
    }
}
