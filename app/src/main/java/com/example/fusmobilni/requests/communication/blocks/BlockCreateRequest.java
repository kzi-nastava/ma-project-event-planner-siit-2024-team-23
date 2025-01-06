package com.example.fusmobilni.requests.communication.blocks;

public class BlockCreateRequest {
    public Long blockedId;
    public Long blockerId;

    public BlockCreateRequest(Long blockedId, Long blockerId) {
        this.blockedId = blockedId;
        this.blockerId = blockerId;
    }

    public Long getBlockedId() {
        return blockedId;
    }

    public void setBlockedId(Long blockedId) {
        this.blockedId = blockedId;
    }

    public Long getBlockerId() {
        return blockerId;
    }

    public void setBlockerId(Long blockerId) {
        this.blockerId = blockerId;
    }
}
