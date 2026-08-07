# shiro-dingtalk

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-21-orange)](https://github.com/easy-4-java/shiro-dingtalk) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](https://www.apache.org/licenses/LICENSE-2.0.txt)

Apache Shiro 的钉钉认证集成，拆分为与 Spring 解耦的核心模块和 Spring 集成模块，构建于 `shiro-extension`、`shiro-jwt-spring` 与 `dingtalk-sdk-extension` 之上。

## 目录

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

**是什么**

`shiro-dingtalk` 将钉钉登录集成到基于 Shiro 的应用中：

- **扫码登录**（`DingTalkScanCodeAuthenticatingFilter` / `DingTalkScanCodeAuthorizingRealm`）——企业扫码登录流程。
- **小程序免登**（`DingTalkMaAuthenticatingFilter` / `DingTalkMaAuthorizingRealm`）——钉钉个人小程序认证。
- **临时授权码登录**（`DingTalkTmpCodeAuthenticatingFilter` / `DingTalkTempCodeAuthorizingRealm`）——使用临时授权码登录。

认证成功后，`DingTalkAuthenticationSuccessHandler` 可通过 `JwtPayloadRepository`（来自 `shiro-jwt-spring`）下发 JWT 登录态；认证主体为携带钉钉用户资料字段的 `ShiroDingTalkPrincipal`。

**不是什么**

- 它不是钉钉 OpenAPI 客户端——API 调用委托给 `io.github.easy4j:dingtalk-sdk-extension`（`DingTalkTemplate`）。
- 它不是带自动配置的 Spring Boot Starter——过滤器与 Realm 需要你在自己的 Shiro 配置中装配。

**典型场景**

| 场景 | 说明 |
| :--- | :--- |
| 企业扫码登录 | `DingTalkScanCodeAuthenticatingFilter` + `DingTalkScanCodeAuthorizingRealm` 实现网页端扫码登录。 |
| 钉钉小程序免登 | `DingTalkMaAuthenticatingFilter` + `DingTalkMaAuthorizingRealm` 实现小程序静默登录。 |
| 临时授权码登录 | `DingTalkTmpCodeAuthenticatingFilter` + `DingTalkTempCodeAuthorizingRealm` 实现临时授权码登录。 |
| 登录态下发 | 钉钉认证成功后由 `DingTalkAuthenticationSuccessHandler` 通过 `JwtPayloadRepository` 下发 JWT 登录态。 |

## 2. Features & Status

| 能力 | 状态 | 说明 |
| :--- | :--- | :--- |
| 扫码登录流程 | 可用 | `DingTalkScanCodeAuthenticatingFilter`、`DingTalkScanCodeLoginRequest`（key/token/loginTmpCode）、`DingTalkScanCodeAuthenticationToken`（unionid、openid、userInfo）。 |
| 小程序登录流程 | 可用 | `DingTalkMaAuthenticatingFilter`、`DingTalkMaLoginRequest`（key/authCode/token/accessToken）、`DingTalkMaAuthenticationToken`。 |
| 临时授权码登录流程 | 可用 | `DingTalkTmpCodeAuthenticatingFilter`、`DingTalkTmpCodeLoginRequest`（key/code/token/accessToken）、`DingTalkTmpCodeAuthenticationToken`。 |
| 钉钉 Realm | 可用 | `DingTalkScanCodeAuthorizingRealm`、`DingTalkMaAuthorizingRealm`、`DingTalkTempCodeAuthorizingRealm`（均接收 `DingTalkTemplate`）。 |
| JWT 登录态 | 可用 | `DingTalkAuthenticationSuccessHandler`（ObjectMapper、`JwtPayloadRepository`、`checkExpiry`）。 |
| 钉钉主体模型 | 可用 | `ShiroDingTalkPrincipal` 继承 `ShiroPrincipal`（unionid、name、mobile、email、department、avatar、position、jobnumber 等）。 |
| 业务异常 | 可用 | `DingTalkCodeNotFoundException`、`DingTalkCodeExpiredException`、`DingTalkCodeIncorrectException`、`DingTalkAuthenticationServiceException`。 |
| 配置属性 Bean | 可用 | `ShiroDingTalkLoginProperties`（appId/appSecret）、`ShiroDingTalkCropAppProperties`、`ShiroDingTalkPersonalMiniAppProperties`、`ShiroDingTalkSuiteProperties`。 |

> 状态以 `feature/3.0.x` 分支上的 `3.0.x.20260630-SNAPSHOT` 为准。

## 3. Requirements & Compatibility

| 项目 | 版本 |
| :--- | :--- |
| JDK | 21+ |
| Maven | 3.0+（内置 Maven Wrapper 3.5.0） |
| Apache Shiro | 1.13.0（`shiro-core`、`shiro-web`） |
| Jackson | 2.17.2（`jackson-databind`、`jackson-annotations`） |
| easy4j 依赖 | `shiro-extension-core`、`shiro-extension-spring`、`shiro-jwt-spring`、`dingtalk-sdk-extension`（均为 `3.0.x.20260630-SNAPSHOT`） |

**版本线**

| 分支 | JDK 基线 | 版本模式 |
| :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
 钉钉客户端（扫码 / 小程序 / 临时授权码）
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
 DingTalk*AuthorizingRealm ----> DingTalkTemplate（dingtalk-sdk-extension）
        |
        +----> ShiroDingTalkPrincipal（用户资料）
        |
        v
 DingTalkAuthenticationSuccessHandler
        |  JwtPayloadRepository（shiro-jwt-spring）
        v
 JWT 登录态 -> Subject
```

本项目是包含两个模块的 Maven 聚合工程。为保持源码兼容，现有 Java 包名不变：

| 模块 | 职责 |
| :--- | :--- |
| `shiro-dingtalk-core` | 登录请求模型、认证 Token、异常和配置 Bean；不依赖 Spring API。 |
| `shiro-dingtalk-spring` | 钉钉主体、Spring Web 认证过滤器、JWT 成功处理器和钉钉 Realm；单向依赖 `shiro-dingtalk-core`。 |

## 5. Installation

构件发布到项目配置的阿里云快照仓库。普通 Spring 应用应依赖 Spring 模块；只使用模型和 Token 时可单独依赖核心模块。

**Maven**

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>shiro-dingtalk-spring</artifactId>
    <version>3.0.x.20260630-SNAPSHOT</version>
</dependency>
```

**Gradle**

```groovy
implementation 'io.github.easy4j:shiro-dingtalk-spring:3.0.x.20260630-SNAPSHOT'
```

## 6. Quick Start

装配扫码登录流程（三种模式中最简单的一种）：

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkScanCodeAuthenticatingFilter;
import org.apache.shiro.spring.boot.dingtalk.realm.DingTalkScanCodeAuthorizingRealm;

// 1. Realm：通过钉钉完成扫码登录凭证交换
DingTalkScanCodeAuthorizingRealm realm =
        new DingTalkScanCodeAuthorizingRealm(dingTalkTemplate);

// 2. 过滤器：解析扫码登录请求（key/token/loginTmpCode）
DingTalkScanCodeAuthenticatingFilter filter =
        new DingTalkScanCodeAuthenticatingFilter(new ObjectMapper());
filter.setLoginUrl("/login/dingtalk/scan");
// 将 filter 与 realm 注册到你的 SecurityManager / 过滤器链
```

**预期结果：** 访问配置的登录 URL 的请求被转换为 `DingTalkScanCodeAuthenticationToken`，由 Realm 通过 `DingTalkTemplate` 完成认证；成功后 `ShiroDingTalkPrincipal`（unionid、openid、用户资料）成为 Shiro 主体。

## 7. Configuration

本模块没有自动配置属性。以下属性 Bean 作为普通 Java Bean 供你绑定（例如在你的应用中通过 `@ConfigurationProperties` 使用）：

| Bean | 字段（示例） |
| :--- | :--- |
| `ShiroDingTalkLoginProperties` | `appId`、`appSecret` |
| `ShiroDingTalkCropAppProperties` | 企业应用设置 |
| `ShiroDingTalkPersonalMiniAppProperties` | 个人小程序设置 |
| `ShiroDingTalkSuiteProperties` | 套件设置 |

**假设：** 钉钉 API 凭证的具体键名遵循 `dingtalk-sdk-extension` 的约定；消费应用中请将这些 Bean 绑定到你自己的属性前缀。

## 8. Core Usage / API

| 类 | 职责 |
| :--- | :--- |
| `AbstractDingTalkAuthenticatingFilter` | 基础过滤器；解析 JSON 登录请求并创建钉钉 Token。 |
| `DingTalkScanCodeAuthenticatingFilter` / `DingTalkMaAuthenticatingFilter` / `DingTalkTmpCodeAuthenticatingFilter` | 三种登录模式的具体过滤器。 |
| `DingTalkScanCodeLoginRequest` / `DingTalkMaLoginRequest` / `DingTalkTmpCodeLoginRequest` | JSON 登录请求模型。 |
| `DingTalkScanCodeAuthenticationToken` / `DingTalkMaAuthenticationToken` / `DingTalkTmpCodeAuthenticationToken` | Shiro 认证 Token。 |
| `DingTalkScanCodeAuthorizingRealm` / `DingTalkMaAuthorizingRealm` / `DingTalkTempCodeAuthorizingRealm` | 执行钉钉凭证交换的 Realm。 |
| `DingTalkAuthenticationSuccessHandler` | 成功后下发 JWT 登录态（`JwtPayloadRepository`、`checkExpiry`）。 |
| `ShiroDingTalkPrincipal` | 携带钉钉用户资料的主体（继承 `ShiroPrincipal`）。 |

成功处理器示例：

```java
import org.apache.shiro.spring.boot.dingtalk.authc.DingTalkAuthenticationSuccessHandler;

DingTalkAuthenticationSuccessHandler successHandler =
        new DingTalkAuthenticationSuccessHandler(objectMapper, jwtPayloadRepository, true);
```

## 9. Testing & Build

```bash
# 完整构建（含 JaCoCo 覆盖率报告/检查）
./mvnw clean verify

# 安装到本地仓库
./mvnw install
```

测试与门禁事实（以 pom 配置为准）：

- 本模块暂无单元测试。
- JaCoCo 绑定 `prepare-agent` / `report` / `check`；`check` 规则要求**行覆盖率不低于 90%**（配置了 `haltOnFailure=false`）。

## 10. Versioning & Branches

| 分支 | JDK 基线 | 版本模式 | 状态 |
| :--- | :--- | :--- | :--- |
| `feature/1.0.x` | JDK 8 | `1.0.x.*` | 活跃；当前快照 `1.0.x.20260630-SNAPSHOT` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` | 维护中 |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` | 维护中 |

维护策略：1.0.x 版本线保持 JDK 8 兼容，服务于存量部署；2.0.x 与 3.0.x 版本线为现代 JDK 基线。发布制品发布到项目配置的制品仓库（阿里云制品仓库）与 GitHub Releases；项目尚未发布到 Maven Central。

## 11. Contributing & License

欢迎参与贡献——请在 [GitHub 仓库](https://github.com/easy-4-java/shiro-dingtalk) 提交 Issue 或 Pull Request。

本项目基于 **Apache License 2.0** 开源。详见 [LICENSE](LICENSE)。
