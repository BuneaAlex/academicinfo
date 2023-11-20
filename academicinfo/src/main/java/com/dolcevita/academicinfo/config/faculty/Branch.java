package com.dolcevita.academicinfo.config.faculty;

import com.dolcevita.academicinfo.model.User;
import lombok.Getter;

import java.util.Set;

@Getter
public class Branch {
    private final User.Language language;
    private final int groupPrefix;
    private final int groups;
    private final String identifier;

    public Branch(final String lang, final int groupPrefix, final int groups, final String identifier) {
        this.groupPrefix = groupPrefix;
        this.identifier = identifier;
        this.groups = groups;
        this.language = User.Language.fromString(lang).orElse(null);
    }
}
