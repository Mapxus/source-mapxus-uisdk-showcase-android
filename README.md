# Mapxus DropInUISDK 使用说明

## 1. 初始化配置

### 1.1 在 Application 中初始化

```kotlin
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 使用您的 AppId 和 Secret 初始化 SDK
        DIAuth.init(this, "your_app_id", "your_secret")
    }
}
```

### 1.2 在 AndroidManifest.xml 中声明 Application

```xml
<application
    android:name=".MainApplication"
    ...
>
```

## 2. 基本使用

### 2.1 创建 DISdk 实例

```kotlin
private val diSdk: DISdk by lazy { DISdk() }
```

### 2.2 配置 DIConfig

```kotlin
val config = DIConfig(
    // 基础配置
    showVenueBackButton = true,  // 是否显示场馆返回按钮
    language = DIConfig.Language.English,  // 设置语言
    
    // 导航模式配置
    navigationModes = listOf(
        DIConfig.NavigationMode.getFootInstance(),      // 步行导航
        DIConfig.NavigationMode.getLiftOnlyInstance(),  // 仅电梯
        DIConfig.NavigationMode.getEscalatorInstance()  // 包含扶梯
    ),
    
    // 主题配置
    colors = DropInColors(
        primaryColor = "#your_color",
        primaryContentColor = "#your_color",
        // ... 其他颜色配置
    ),
    
    // 多语言文本配置
    buildingTitle = StringsWithLanguage(
        default = "默认标题",
        en = "English Title",
        zhHans = "简体中文标题",
        zhHant = "繁體中文標題",
        ja = "日本語タイトル"
    ),
    
    // POI 配置
    poiSort = DIConfig.PoiSortOption.ByNameAlphabetically,  // POI 排序方式
    permanentlyDisplayedCategory = listOf("facility.gate.entry", "facility.attractions")  // 永久显示的 POI 类别
)
```

### 2.3 生成并添加地图视图

```kotlin
// 生成地图视图
val dropInView = diSdk.generateView(context, diConfig)

// 设置布局参数
dropInView.layoutParams = FrameLayout.LayoutParams(
    FrameLayout.LayoutParams.MATCH_PARENT,
    FrameLayout.LayoutParams.MATCH_PARENT
)

// 添加到容器中
container.addView(dropInView)
```

## 3. 事件监听

```kotlin
diSdk.apply {
    // 场馆页面事件
    setVenueEventListener(venueEventListener)
    
    // 建筑物事件
    setBuildingEventListener(buildingEventListener)
    
    // POI 事件
    setPoiEventListener(poiEventListener)
    
    // 导航事件
    setNavigationEventListener(navigationEventListener)
    
    // 地图事件
    setMapEventListener(mapEventListener)
    
    // 分享事件
    setShareEventListener(shareEventListener)
}
```

## 4. 用户模式设置

```kotlin
// 设置用户模式（普通/轮椅）
diSdk.setUserMode(UserMode.NORMAL)  // 普通模式
diSdk.setUserMode(UserMode.WHEELCHAIR)  // 轮椅模式
```

## 5. 资源释放

```kotlin
override fun onDestroy() {
    diSdk.destroy()
    super.onDestroy()
}
```

## 6. 注意事项

1. 确保在使用 SDK 前已正确初始化（DIAuth.init）
2. 使用完毕后需要调用 destroy() 方法释放资源
3. 配置 DIConfig 时，可以根据需求选择性配置，未配置的项目将使用默认值
4. 事件监听器的设置应在生成地图视图之前完成
5. 用户模式的设置会影响导航路线的计算结果
        
