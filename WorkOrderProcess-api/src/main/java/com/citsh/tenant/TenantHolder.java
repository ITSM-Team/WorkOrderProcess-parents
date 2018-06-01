package com.citsh.tenant;
public interface TenantHolder {
    String getTenantId();

    String getTenantCode();

    String getUserRepoRef();
    
    TenantDTO getTenantDto();
}
