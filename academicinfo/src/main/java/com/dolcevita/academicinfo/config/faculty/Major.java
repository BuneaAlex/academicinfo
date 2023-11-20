package com.dolcevita.academicinfo.config.faculty;
import com.dolcevita.academicinfo.model.User;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Major {
    private final String name;
    private final int years;
    private final Set<Branch> branches;
    private Set<String> groups;
    private Set<String> formations;

    public Major(final String name, final int years, final Set<Branch> branches) {
        this.name = name;
        this.years = years;
        this.branches = branches;

        this.formations = branches.stream()
                .flatMap(branch -> IntStream.rangeClosed(1, years)
                        .mapToObj(year -> branch.getIdentifier() + year))
                .collect(Collectors.toSet());

        this.groups = branches.stream()
                .flatMap(branch -> IntStream.rangeClosed(1, years)
                        .boxed()
                        .flatMap(year -> IntStream.rangeClosed(1, branch.getGroups())
                                .mapToObj(group -> String.valueOf(branch.getGroupPrefix()) + year + group)))
                .collect(Collectors.toSet());
    }

    public Optional<Branch> getBranchByLanguage(User.Language language) {
        return branches.stream()
                .filter(branch -> branch.getLanguage().equals(language))
                .findFirst();
    }
}
