# Mapxus UISDK Showcase App Startup

本文档说明如何在本地构建并运行该展示项目。

## 前置要求

- Android Studio（最新稳定版）
- 已获取有效的 Mapxus `appid` 和 `secret`

---

## 构建步骤

### 步骤一：克隆项目

```bash
git clone <仓库地址>
```

### 步骤二：在根目录创建 `secret.properties` 文件

在项目根目录（与 `settings.gradle.kts` 同级）新建一个名为 `secret.properties` 的文件：

### 步骤三：填入 appid 与 secret

打开 `secret.properties`，按以下格式填入你的 Mapxus 凭据：

```properties
appid-prod=YOUR_APP_ID
secret-prod=YOUR_SECRET
```

> ⚠️ **注意**：`secret.properties` 包含生产密钥，已加入 `.gitignore`，请勿提交到版本控制。

---

## 构建与运行

使用 Android Studio 打开项目，或通过命令行构建：

```bash
# 构建 Debug APK
./gradlew :app:assembleDebug
```

构建成功后，将 APK 安装至设备或模拟器即可运行。
