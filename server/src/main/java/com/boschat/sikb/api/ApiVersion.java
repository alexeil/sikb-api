package com.boschat.sikb.api;

public enum ApiVersion {
    V1("v1");

    private String name;

    ApiVersion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
/*  private static Profiles getProfiles(Object object) {
        if (isProfiles(object)) {
            return (Profiles) object;
        }
        return null;
    }

    private static Profile getProfile(Object object) {
        if (isProfile(object)) {
            return (Profile) object;
        }
        return null;
    }

    private static boolean isProfiles(Object object) {
        return object instanceof Profiles;
    }

    private static boolean isProfile(Object object) {
        return object instanceof Profile;
    }

    public String getName() {
        return name;
    }

    public Object comply(Object object, ProffamContext proffamContext) {
        Profiles profiles = getProfiles(object);
        if (profiles != null) {
            complyProfiles(profiles, proffamContext);
        }

        Profile profile = getProfile(object);
        if (profile != null) {
            complyProfile(profile, proffamContext);
        }
        return object;
    }

    private void complyProfile(Profile member, ProffamContext proffamContext) {
        computeFilterAgeLimit(member, proffamContext);
    }

    private void complyProfiles(Profiles profiles, ProffamContext proffamContext) {
        if (CollectionUtils.isNotEmpty(profiles.getProfiles())) {
            profiles.getProfiles().forEach(member -> complyProfile(member, proffamContext));
        }
    }
*/
}
