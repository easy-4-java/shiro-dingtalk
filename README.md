# shiro-dingtalk-extension

![Java](https://img.shields.io/badge/Java-8-orange) ![License](https://img.shields.io/badge/License-Apache%202.0-blue)

DingTalk (钉钉) authentication extension for Apache Shiro. It brings scan-code (扫码登录), mini-app (小程序) and temporary-code (临时授权码) login flows into Shiro, with dedicated authentication filters, tokens, realms and a JWT-backed success handler — built on `shiro-biz`, `shiro-jwt-extension` and `dingtalk-sdk-extension`.

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

**What it is**

`shiro-dingtalk-extension` integrates DingTalk login into Shiro-based applications:

- **Scan-code login** (`DingTalkScanCodeAuthenticatingFilter` / `DingTalkScanCodeAuthorizingRealm`) — enterprise scan-to-login flows.
- **Mini-app login** (`DingTalkMaAuthenticatingFilter` / `DingTalkMaAuthorizingRealm`) — DingTalk personal mini-app authentication.
- **Temporary-code login** (`DingTalkTmpCodeAuthenticatingFilter` / `DingTalkTempCodeAuthorizingRealm`) — login with a temporary authorization code.

After successful authentication, `DingTalkAuthenticationSuccessHandler` can produce a JWT login state through `JwtPayloadRepository` (from `shiro-jwt-extension`), and the authenticated principal is a `ShiroDingTalkPrincipal` carrying DingTalk user profile fields.

**What it is not**

- It is not the DingTalk OpenAPI client — API calls are delegated to `io.github.easy4j:dingtalk-sdk-extension` (`DingTalkTemplate`).
- It is not a Spring Boot starter with auto-configuration — wire the filters/realms into your own Shiro configuration.

**Typical scenarios**

| Scenario | Description |
| :--- | :--- |
| 企业扫码登录 | `DingTalkScanCodeAuthenticatingFilter` + `DingTalkScanCodeAuthorizingRealm` for QR-code login on web pages. |
| 钉钉小程序免登 | `DingTalkMaAuthenticatingFilter` + `DingTalkMaAuthorizingRealm` for mini-app silent login. |
| 临时授权码登录 | `DingTalkTmpCodeAuthenticatingFilter` + `DingTalkTempCodeAuthorizingRealm` for temp-code login. |
| 登录态下发 | `DingTalkAuthenticationSuccessHandler` issues JWT state via `JwtPayloadRepository` after DingTalk authentication. |

## 2. Features & Status

| Capability | Status | Notes |
| :--- | :--- | :--- |
| Scan-code login flow | Available | `DingTalkScanCodeAuthenticatingFilter`, `DingTalkScanCodeLoginRequest` (key/token/loginTmpCode), `DingTalkScanCodeAuthenticationToken` (unionid, openid, userInfo). |
| Mini-app login flow | Available | `DingTalkMaAuthenticatingFilter`, `DingTalkMaLoginRequest` (key/authCode/token/accessToken), `DingTalkMaAuthenticationToken`. |
| Temporary-code login flow | Available | `DingTalkTmpCodeAuthenticatingFilter`, `DingTalkTmpCodeLoginRequest` (key/code/token/accessToken), `DingTalkTmpCodeAuthenticationToken`. |
| DingTalk realms | Available | `DingTalkScanCodeAuthorizingRealm`, `DingTalkMaAuthorizingRealm`, `DingTalkTempCodeAuthorizingRealm` (all take a `DingTalkTemplate`). |
| JWT login state | Available | `DingTalkAuthenticationSuccessHandler` (ObjectMapper, `JwtPayloadRepository`, `checkExpiry`). |
| DingTalk principal model | Available | `ShiroDingTalkPrincipal` extends `ShiroPrincipal` (unionid, name, mobile, email, department, avatar, position, jobnumber, ...). |
| Business exceptions | Available | `DingTalkCodeNotFoundException`, `DingTalkCodeExpiredException`, `DingTalkCodeIncorrectException`, `DingTalkAuthenticationServiceException`. |
| Configuration property beans | Available | `ShiroDingTalkLoginProperties` (appId/appSecret), `ShiroDingTalkCropAppProperties`, `ShiroDingTalkPersonalMiniAppProperties`, `ShiroDingTalkSuiteProperties`. |

> Status is reported as of `1.0.x.20260630-SNAPSHOT` on the `feature/1.0.x` branch.

## 3. Requirements & Compatibility

| Item | Version |
| :--- | :--- |
| JDK | 8+ |
| Maven | 3.0+ (Maven Wrapper 3.5.0 bundled) |
| Apache Shiro | 1.13.0 (`shiro-core`, `shiro-web`) |
| Jackson | 2.17.2 (`jackson-databind`, `jackson-annotations`) |
| easy4j dependencies | `shiro-biz`, `shiro-jwt-extension`, `dingtalk-sdk-extension` (all `1.0.x.20260630-SNAPSHOT`) |

**Version lines**

| Branch | JDK baseline | Version pattern |
| :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
 DingTalk client (scan-code / mini-app / temp-code)
        |
        v
 AbstractDingTalkAuthenticatingFilter
   |-- DingTalkScanCodeAuthenticatingFilter
   |-- DingTalkMaAuthenticatingFilter
   |-- DingTalkTmpCodeAuthenticatingFilter
        |
        v
 DingTalk*AuthenticationToken
        |
        v
 DingTalk*AuthorizingRealm ----> DingTalkTemplate (dingtalk-sdk-extension)
        |
        +----> ShiroDingTalkPrincipal (user profile)
        |
        v
 DingTalkAuthenticationSuccessHandler
        |  JwtPayloadRepository (shiro-jwt-extension)
        v
 JWT login state -> Subject
```

This is a **single-module** project (packaging `jar`), all classes under `org.apache.shiro.spring.boot.dingtalk`:

| Package | Role |
| :--- | :--- |
| `authc` | Authentication filters and login request models for the three login modes |
| `realm` | Realms that exchange the DingTalk credentials via `DingTalkTemplate` |
| `token` | `DingTalk*AuthenticationToken` classes |
| `exception` | DingTalk-specific authentication exceptions |
| `property` | Configuration property beans (appId/appSecret/suite/mini-app) |
| root | `ShiroDingTalkPrincipal`, `DingTalkAuthenticationSuccessHandler` |

## 5. Installation

The artifact is not yet published to Maven Central. Resolve it from the project's configured artifact repository (Aliyun Packages) or install it locally from source; the snapshot version currently used on the `feature/1.0.x` branch is `1.0.x.20260630-SNAPSHOT`.

**Maven**

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>shiro-dingtalk-extension</artifactId>
    <version>1.0.x.20260630-SNAPSHOT</version>
</dependency>
```

**Gradle**

```groovy
implementation 'io.github.easy4j:shiro-dingtalk-extension:1.0.x.20260630-SNAPSHOT'
```

## 6. Quick Start

Wire the scan-code flow (the simplest example of the three):

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkScanCodeAuthenticatingFilter;
import org.apache.shiro.spring.boot.dingtalk.realm.DingTalkScanCodeAuthorizingRealm;

// 1. Realm: exchange the scan-code login credentials through DingTalk
DingTalkScanCodeAuthorizingRealm realm =
        new DingTalkScanCodeAuthorizingRealm(dingTalkTemplate);

// 2. Filter: parses the scan-code login request (key/token/loginTmpCode)
DingTalkScanCodeAuthenticatingFilter filter =
        new DingTalkScanCodeAuthenticatingFilter(new ObjectMapper());
filter.setLoginUrl("/login/dingtalk/scan");
// register filter + realm with your SecurityManager / filter chain
```

**Expected result:** requests to the configured login URL are turned into `DingTalkScanCodeAuthenticationToken`s, authenticated by the realm via `DingTalkTemplate`, and on success a `ShiroDingTalkPrincipal` (unionid, openid, user profile) becomes the Shiro principal.

## 7. Configuration

There are no automatic configuration properties in this module. The following property beans exist as plain Java beans for you to bind (e.g. via `@ConfigurationProperties` in your own application):

| Bean | Fields (examples) |
| :--- | :--- |
| `ShiroDingTalkLoginProperties` | `appId`, `appSecret` |
| `ShiroDingTalkCropAppProperties` | enterprise app settings |
| `ShiroDingTalkPersonalMiniAppProperties` | personal mini-app settings |
| `ShiroDingTalkSuiteProperties` | suite settings |

**Assumption:** the exact key names of the DingTalk API credentials follow the conventions of `dingtalk-sdk-extension`; bind these beans to your own property prefix in the consuming application.

## 8. Core Usage / API

| Class | Role |
| :--- | :--- |
| `AbstractDingTalkAuthenticatingFilter` | Base filter; parses JSON login requests and creates DingTalk tokens. |
| `DingTalkScanCodeAuthenticatingFilter` / `DingTalkMaAuthenticatingFilter` / `DingTalkTmpCodeAuthenticatingFilter` | Concrete filters for the three login modes. |
| `DingTalkScanCodeLoginRequest` / `DingTalkMaLoginRequest` / `DingTalkTmpCodeLoginRequest` | JSON login request models. |
| `DingTalkScanCodeAuthenticationToken` / `DingTalkMaAuthenticationToken` / `DingTalkTmpCodeAuthenticationToken` | Shiro authentication tokens. |
| `DingTalkScanCodeAuthorizingRealm` / `DingTalkMaAuthorizingRealm` / `DingTalkTempCodeAuthorizingRealm` | Realms performing the DingTalk exchange. |
| `DingTalkAuthenticationSuccessHandler` | Issues JWT login state after success (`JwtPayloadRepository`, `checkExpiry`). |
| `ShiroDingTalkPrincipal` | Principal carrying the DingTalk user profile (extends `ShiroPrincipal`). |

Success-handler example:

```java
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkAuthenticationSuccessHandler;

DingTalkAuthenticationSuccessHandler successHandler =
        new DingTalkAuthenticationSuccessHandler(objectMapper, jwtPayloadRepository, true);
```

## 9. Testing & Build

```bash
# Full build with JaCoCo coverage report/check
./mvnw clean verify

# Install into the local repository
./mvnw install
```

Test & gate facts (as configured in the pom):

- No unit tests exist in this module yet.
- JaCoCo is bound to `prepare-agent` / `report` / `check`; the `check` rule requires a **90% line coverage ratio** (configured with `haltOnFailure=false`).

## 10. Versioning & Branches

| Branch | JDK baseline | Version pattern | Status |
| :--- | :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` | Active; current snapshot `1.0.x.20260630-SNAPSHOT` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` | Maintained |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` | Maintained |

Maintenance strategy: the 1.0.x line keeps JDK 8 compatibility for legacy deployments; the 2.0.x and 3.0.x lines are the modern JDK baselines. Release artifacts are published to the project's configured artifact repository (Aliyun Packages) and GitHub Releases; the project has not yet published to Maven Central.

## 11. Contributing & License

Contributions are welcome — please open an issue or a pull request on the [GitHub repository](https://github.com/easy-4-java/shiro-dingtalk-extension).

This project is licensed under the **Apache License 2.0**. See [LICENSE](LICENSE) for details.
