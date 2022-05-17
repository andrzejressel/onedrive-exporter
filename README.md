# OneDrive prometheus exporter
![GitHub release (latest by date)](https://img.shields.io/github/v/release/andrzejressel/onedrive-exporter)

## Quick start

```yaml
# docker-compose.yml
version: "3.9"
services:
  onedrive_exporter:
    image: ghcr.io/andrzejressel/onedrive-exporter:VERSION
    environment:
      - ONEDRIVE_CLIENT_ID=CLIENT_ID
      - ONEDRIVE_REDIRECT=http://localhost:8080/
      - ONEDRIVE_CLIENT_SECRET=CLIENT_SECRET
    volumes:
      - onedrivedata:/data
    ports:
      - "8080:8080"
```

```yaml
# prometheus.yaml
- job_name: 'onedrive'
  metrics_path: /q/metrics
  static_configs:
    - targets: ['localhost:8080']
```

### Getting your own Client ID and Secret

1. Open https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationsListBlade and then click `New registration`.
2. Enter a name for your app, choose account type `Accounts in any organizational directory (Any Azure AD directory - Multitenant) and personal Microsoft accounts (e.g. Skype, Xbox)`, select `Web` in `Redirect URI`, then type (be careful when copying and pasting) `http://localhost:8080/onedrive` (or your custom url where collector will be available + `/onedrive` suffix) and click Register. Copy and keep the `Application (client) ID`  - this is your `CLIENT_ID`.
3. Under `manage` select `Certificates & secrets`, click `New client secret`. Enter a description (can be anything) and set `Expires` to 24 months. Copy and keep that secret _Value_ - this is your `CLIENT_SECRET`.
4. Under `manage` select `API permissions`, click `Add a permission` and select `Microsoft Graph` then select `delegated permissions`.
5. Search and select the following permissions: `Files.Read`, `offline_access` and `User.Read`. Once selected click `Add permissions` at the bottom.

## Why not use Microsoft library

- It does not work with GraalVM. It may in the future - https://github.com/Azure/azure-sdk-for-java/issues/21735