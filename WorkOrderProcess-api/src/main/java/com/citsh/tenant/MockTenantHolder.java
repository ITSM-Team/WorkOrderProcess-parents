package com.citsh.tenant;

public class MockTenantHolder
  implements TenantHolder
{
  private TenantDTO tenantDto = new TenantDTO();

  public MockTenantHolder() {
    this.tenantDto.setId("1");
    this.tenantDto.setCode("default");
    this.tenantDto.setUserRepoRef("1");
  }

  public String getTenantId() {
    return this.tenantDto.getId();
  }

  public String getTenantCode() {
    return this.tenantDto.getCode();
  }

  public String getUserRepoRef() {
    return this.tenantDto.getUserRepoRef();
  }

  public TenantDTO getTenantDto() {
    return this.tenantDto;
  }
}