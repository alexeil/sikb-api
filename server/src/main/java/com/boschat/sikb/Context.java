package com.boschat.sikb;

import com.boschat.sikb.api.CallType;

public class Context {

    private CreateOrUpdateAffiliationContext createOrUpdateAffiliationContext;

    private CreateOrUpdateClubContext createOrUpdateClubContext;

    private CallType callType;

    private Integer clubId;

    public Context(CallType callType) {
        this.callType = callType;
    }

    public CreateOrUpdateAffiliationContext getCreateOrUpdateAffiliationContext() {
        return createOrUpdateAffiliationContext;
    }

    public void setCreateOrUpdateAffiliationContext(CreateOrUpdateAffiliationContext createOrUpdateAffiliationContext) {
        this.createOrUpdateAffiliationContext = createOrUpdateAffiliationContext;
    }

    public CreateOrUpdateClubContext getCreateOrUpdateClubContext() {
        return createOrUpdateClubContext;
    }

    public void setCreateOrUpdateClubContext(CreateOrUpdateClubContext createOrUpdateClubContext) {
        this.createOrUpdateClubContext = createOrUpdateClubContext;
    }

    public CallType getCallType() {
        return callType;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }
}
