package com.dolcevita.academicinfo.config.faculty;
import com.dolcevita.academicinfo.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public class Specialization {
    private final String name;
    private final int years;
    private final Set<Branch> branches;

    public Optional<Branch> getBranchByLanguage(User.Language language) {
        return branches.stream()
                .filter(branch -> branch.getLanguage().equals(language))
                .findFirst();
    }

    void generateGroups(Integer groupSplitFactor) {
        branches.forEach(branch -> branch.generateFormations(years, groupSplitFactor));
    }
}
