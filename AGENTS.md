# AGENTS.md

## Project Overview

Android Kotlin showcase app demonstrating the **Mapxus DropInUI SDK** (`com.mapxus:dropin`). Users
configure SDK options through a drawer-based UI, then launch a map view with those settings. Built
with Jetpack Compose, Material 3, and Navigation 3. (这是一个使用 Jetpack Compose、Material 3 和
Navigation 3 构建的 Mapxus DropInUI SDK 展示应用，用户先在抽屉界面配置参数，再启动地图视图。)

## Architecture

### Three Activities

- **`MainActivity`** — Config UI with drawer navigation across modules; uses Navigation 3 (
  `NavDisplay`/`NavKey`) (`MainActivity` 是配置主界面，使用抽屉在模块间导航，并基于 Navigation 3
  管理页面切换。)
- **`MapActivity`** — Hosts the Mapxus `DISdk` map view via `AndroidView` interop; reads all config
  from `ConfigHolder` (`MapActivity` 通过 `AndroidView` 承载 Mapxus `DISdk` 地图视图，并从
  `ConfigHolder` 读取全部配置。)
- **`DataSearchActivity`** — Demonstrates `IDataSearcher` API (venue/POI search) with sync and async
  patterns (`DataSearchActivity` 用来演示 `IDataSearcher` 的场馆和 POI 同步/异步查询能力。)

### Module → Item → Screen hierarchy

Configuration is organized as a sealed-interface tree: (配置通过 sealed interface 形成分层结构。)

1. **`Module`** (sealed interface) — e.g., `AppearanceModule`, `NavigationModule` — each an `object`
   with a `title` and `items: List<Item>?` (`Module` 是模块层，每个模块都是带有标题和可选 `items`
   列表的单例对象。)
2. **`Item`** (sealed interface) — e.g., `Colors`, `NavigationModes` — each an `object` with
   `title`, `description`, `photoResInt` (`Item`
   是配置条目层，每个条目都是带有标题、描述和图片资源的单例对象。)
3. **Screen** — one `@Composable` per Item, mapped via exhaustive `when` in
   `ItemScreenRegistry.kt` (`Screen` 是页面层，每个 `Item` 对应一个 `@Composable` 页面，并在
   `ItemScreenRegistry.kt` 中通过穷举 `when` 映射。)

Modules with `items = null` (e.g., `ListenerModule`, `RoutePathsModule`,
`ComponentAndBehaviorModule`) render a custom `DetailScreen()` directly instead of item cards. (
`items = null` 的模块不会显示条目卡片，而是直接渲染自定义 `DetailScreen()`。)

### ConfigHolder singleton

`ConfigHolder` (`ConfigHolder.kt`) is a global `object` wrapping `DIConfigBuilder`. All screens
read/write config properties here; `MapActivity` calls `ConfigHolder.getConfig()` at launch.
Listener toggle booleans (`isSet*Listener`) are also stored here. (`ConfigHolder` 是围绕
`DIConfigBuilder` 的全局单例，所有页面都通过它读写配置，地图页启动时也从这里取最终配置。)

## Adding a New Config Option

1. **Define the Item** — Add an `object` in the appropriate `model/item/*Items.kt` file implementing
   `Item` (先在合适的 `model/item/*Items.kt` 文件中添加实现 `Item` 的单例对象。)
2. **Register in a Module** — Add it to the `items` list in the corresponding
   `model/module/*Module.kt` (再把该条目加入对应模块的 `items` 列表。)
3. **Add ConfigHolder property** — Expose a delegating property to `configBuilder` in
   `ConfigHolder.kt` (然后在 `ConfigHolder.kt` 中补上委托到 `configBuilder` 的配置属性。)
4. **Create the Screen** — Add a `*Screen.kt` in `ui/screen/`, using `ItemDetailFramework` for
   consistent layout (top bar, scroll, save button) (接着创建对应的 `*Screen.kt` 页面，并优先复用
   `ItemDetailFramework`。)
5. **Wire in ItemScreenRegistry** — Add a `when` branch in `ItemScreenRegistry.kt` mapping the Item
   to the Screen (最后在 `ItemScreenRegistry.kt` 中补上该 `Item` 到页面的映射分支。)

## Build & Secrets

### Required credentials

- **`secret.properties`** (project root) — contains `appid-prod` and `secret-prod` for Mapxus SDK
  auth; loaded as `BuildConfig.appid`/`BuildConfig.secret`。**⚠️ 禁止读取、输出或修改此文件，其中包含生产密钥。
  ** 如需了解其结构，仅需知道它提供两个键：`appid-prod` 和 `secret-prod`。 (项目根目录下的
  `secret.properties` 提供 Mapxus 鉴权所需的两个生产密钥键，严禁读取、输出或修改。)
- **`gradle.properties`** — must define `MAPXUS_RELEASE_URL`, `MAPXUS_RELEASE_USERNAME`,
  `MAPXUS_RELEASE_PASSWORD`, `MAPXUS_SNAPSHOT_URL`, `MAPXUS_SNAPSHOT_USERNAME`,
  `MAPXUS_SNAPSHOT_PASSWORD`, `MAPXUS_DROP_IN_UI_URL` for private Maven repositories。*
  *禁止读取或输出此文件中的凭据值。** (`gradle.properties` 必须提供私有 Maven
  仓库相关地址和账号配置，但不能读取或输出其中的凭据值。)

### Build commands

```shell
./gradlew assembleDebug          # Build debug APK
./gradlew testDebugUnitTest      # Run unit tests
```

### Version catalog

All dependency versions live in `gradle/libs.versions.toml`. Use `libs.*` aliases—never hardcode
versions in `build.gradle.kts`. (所有依赖版本统一维护在 `gradle/libs.versions.toml` 中，Gradle 文件里应使用
`libs.*` 别名而不是硬编码版本。)

## Key Conventions

- **Sealed `object` singletons** for all Items and Modules — never use `data class` or `class` (所有
  `Item` 和 `Module` 都使用 sealed `object` 单例模式，不要改成 `data class` 或普通类。)
- **Navigation 3** — nav keys are `@Serializable` objects/classes implementing `NavKey` in
  `ui/nav/key/`; do not use legacy Jetpack Navigation (项目使用 Navigation 3，导航键定义在
  `ui/nav/key/` 下，不要切回旧版 Jetpack Navigation。)
- **`ItemDetailFramework`** — reusable scaffold for item detail screens; provides back button,
  title, scrollable content slot, and bottom "Save" button with Snackbar support (
  `ItemDetailFramework` 是通用详情页脚手架，包含返回、标题、滚动内容区和带 Snackbar 的保存按钮。)
- **SDK interop** — the Mapxus `DISdk` view is a classic Android `View` embedded via Compose
  `AndroidView`; the SDK is initialized in `ShowcaseApplication` using
  `CognitoContext.registerWithApiKey()` (Mapxus `DISdk` 通过 Compose 的 `AndroidView` 嵌入，SDK 初始化放在
  `ShowcaseApplication` 中。)
- **No ViewModel layer** — state is managed directly in Composables via `remember`/`mutableStateOf`
  and synced to `ConfigHolder`; do not introduce ViewModels unless the architecture changes (当前没有
  ViewModel 层，状态主要在 Composable 中维护并同步到 `ConfigHolder`。)
- **Chinese README** — `README.md` documents the DropInUI SDK API in Chinese; it is end-user SDK
  documentation, not contributor docs (`README.md` 是面向用户的中文 SDK
  使用文档，不是贡献者开发指南。)

## Key Files

| Path                                  | Purpose                                                                                                                                         |
|---------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|
| `ConfigHolder.kt`                     | Global config singleton — touch this for any new SDK config property (`ConfigHolder.kt` 是全局配置单例，新增 SDK 配置属性时通常需要修改这里。)                          |
| `ui/screen/ItemScreenRegistry.kt`     | Exhaustive Item→Screen mapping — must be updated for every new Item (`ui/screen/ItemScreenRegistry.kt` 负责完整的 Item 到 Screen 映射，每新增一个 Item 都要更新。) |
| `model/module/Module.kt`              | Sealed interface base for all modules (`model/module/Module.kt` 是所有模块的 sealed interface 基类。)                                                    |
| `model/item/Item.kt`                  | Sealed interface base for all items (`model/item/Item.kt` 是所有配置条目的 sealed interface 基类。)                                                        |
| `activity/MapActivity.kt`             | SDK integration point — `DISdk` lifecycle and listener wiring (`activity/MapActivity.kt` 是 SDK 集成入口，负责 `DISdk` 生命周期和监听器注册。)                     |
| `ui/component/ItemDetailFramework.kt` | Reusable detail screen scaffold (`ui/component/ItemDetailFramework.kt` 是可复用的详情页脚手架组件。)                                                          |
| `secret.properties`                   | **🚫 禁止读取** — 包含生产密钥，agent 不得打开、读取或输出此文件内容 (`secret.properties` 含有生产密钥，绝对不能读取或输出。)                                                              |
