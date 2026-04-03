# Copilot Instructions

## Build, test, and lint commands

- This project depends on private Mapxus Maven repositories configured through `gradle.properties`,
  and `app/build.gradle.kts` also reads `secret.properties` to populate `BuildConfig.appid` and
  `BuildConfig.secret`. Do not read or print those secret values. (项目依赖私有 Maven
  仓库和本地密钥文件，禁止读取或输出这些敏感值。)
- Build the debug app with `./gradlew :app:assembleDebug`. (使用该命令构建 debug 应用。)
- Run unit tests with `./gradlew :app:testDebugUnitTest`. (使用该命令运行全部本地单元测试。)
- Run a single unit test with
  `./gradlew :app:testDebugUnitTest --tests "com.mapxus.uisdkshowcase.ExampleUnitTest"`. (
  使用该命令运行单个本地单元测试类。)
- Run instrumented tests on a connected device or emulator with
  `./gradlew :app:connectedDebugAndroidTest`. (在已连接设备或模拟器上运行仪器测试。)
- Run a single instrumented test with
  `./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.mapxus.uisdkshowcase.ExampleInstrumentedTest`. (
  使用该命令运行单个仪器测试类。)
- Run Android lint with `./gradlew :app:lint` or `./gradlew :app:lintDebug`. (使用该命令运行 Android
  lint 检查。)
- The current test suite only contains template example tests in `app/src/test/` and
  `app/src/androidTest/`. (当前测试目录里只有模板示例测试。)

## High-level architecture

- This is a Jetpack Compose showcase app for the Mapxus DropIn UI SDK. The app lets users configure
  SDK options in a drawer-driven UI, then launches the SDK map with those settings. (这是一个基于
  Jetpack Compose 的 Mapxus DropIn UI SDK 展示应用，用户先配置参数再启动地图。)
- `ShowcaseApplication` initializes Mapxus auth at startup via
  `CognitoContext.registerWithApiKey(...)` using `BuildConfig.appid` and `BuildConfig.secret`. (
  应用启动时会在 `ShowcaseApplication` 中完成 Mapxus 鉴权初始化。)
- `MainActivity` is the configuration shell. It defines the top-level module order, uses Navigation
  3 with `ModuleSelecting` and `ItemDetail` nav keys, and routes each `Item` to a screen through
  `ui/screen/ItemScreenRegistry.kt`. (主页面负责配置入口、模块顺序、Navigation 3 导航以及 Item
  到具体页面的分发。)
- Configuration state is centralized in `ConfigHolder`, a singleton wrapper around
  `DIConfigBuilder`. Item screens read initial values from `ConfigHolder`, update local Compose
  state, and save back into `ConfigHolder`. `MapActivity` later creates
  `DISdk(ConfigHolder.getConfig())` from that singleton state. (配置状态集中保存在 `ConfigHolder`
  中，各配置页读写它，地图页再基于它构建 SDK 配置。)
- `ModuleDrawerFramework` shows either item cards for a module or the module's custom
  `DetailScreen()` when `items == null`. (抽屉框架会根据模块是否有 items
  决定显示条目卡片还是模块自定义详情页。)
- `MapActivity` embeds the DropIn SDK `View` inside Compose with `AndroidView`, requests location
  permission, starts the SDK using `ConfigHolder.appRoute`, and conditionally wires SDK listeners
  based on boolean flags stored in `ConfigHolder`. (地图页通过 `AndroidView` 嵌入 SDK
  视图、申请定位权限，并按配置决定是否注册各类监听器。)
- `DataSearchActivity` is separate from the map flow and demonstrates `IDataSearcher` sync and async
  venue/POI queries using the current config. (数据搜索页独立于地图流程，用于演示 `IDataSearcher`
  的同步和异步查询。)
- `README.md` is Chinese end-user SDK usage documentation. `AGENTS.md` is the more accurate
  contributor-oriented summary of the repository structure and extension points. (`README.md` 偏向中文
  SDK 使用说明，`AGENTS.md` 更适合作为仓库开发指引。)

## Key conventions

- `Module` and `Item` are sealed interfaces implemented as singleton `object`s. Follow that pattern
  for new modules and settings instead of introducing data classes or class-based registries. (
  `Module` 和 `Item` 都采用 sealed interface + singleton object 模式，不要改成 data class
  或类注册表。)
- The main extension path for a new config option spans multiple files: add the `Item` object in
  `model/item/*Items.kt`, register it in the appropriate `model/module/*Module.kt`, expose the
  corresponding `ConfigHolder` property, create the `*Screen.kt`, and add the exhaustive branch in
  `ui/screen/ItemScreenRegistry.kt`. (新增配置项通常要同步修改 Item、Module、ConfigHolder、对应 Screen
  和 `ItemScreenRegistry`。)
- For standard item detail screens, reuse `ui/component/ItemDetailFramework.kt`. The common pattern
  is: initialize local state from `ConfigHolder`, edit locally in Compose, then persist only inside
  `onSaveClicked` and show snackbar feedback. (标准配置详情页应复用 `ItemDetailFramework`
  ，并遵循“本地编辑、点击保存后写回”的模式。)
- Modules with `items = null` are special-case top-level screens, not item grids. They override
  `DetailScreen()` directly, as in `ListenerModule`, `RoutePathsModule`, and
  `ComponentAndBehaviorModule`. (`items = null` 的模块表示顶层自定义页面，而不是普通条目列表。)
- If you add a new module, also add it to the hardcoded `modules` list in `MainActivity`; drawer
  order and visibility are manual. (新增模块后还要手动加入 `MainActivity`
  的模块列表，抽屉顺序不是自动发现的。)
- Do not assume every screen file is reachable from the UI. Some screens and items exist in the
  source tree but are intentionally not included in a module's `items` list. (不要默认所有 screen
  文件都已接入 UI，有些页面只是存在于源码中但未暴露。)
- The app does not use a ViewModel layer. State is held directly in composables with `remember` /
  `mutableStateOf` and mirrored into `ConfigHolder`. (项目没有 ViewModel 层，状态主要由 Compose 本地状态和
  `ConfigHolder` 共同维护。)
- Use dependency aliases from `gradle/libs.versions.toml` (`libs.*`) instead of hardcoding
  dependency versions in Gradle files. (依赖版本统一走 `libs.versions.toml`，不要在 Gradle
  文件中硬编码版本。)
