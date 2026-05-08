# Mapxus UISDK Showcase App Setup

This document explains how to build and run the showcase project locally.

## Prerequisites

- Android Studio (latest stable release)
- A valid Mapxus `appid` and `secret`

---

## Setup Steps

### Step 1: Clone the Repository

```bash
git clone <repository-url>
```

### Step 2: Create `secret.properties` in the Project Root

Create a file named `secret.properties` in the project root directory, alongside
`settings.gradle.kts`.

### Step 3: Add Your `appid` and `secret`

Open `secret.properties` and add your Mapxus credentials in the following format:

```properties
appid-prod=YOUR_APP_ID
secret-prod=YOUR_SECRET
```

> ⚠️ **Note**: `secret.properties` contains production credentials and is already included in
> `.gitignore`. Do not commit it to version control.

---

## Build and Run

Open the project in Android Studio, or build it from the command line:

```bash
# Build the debug APK
./gradlew :app:assembleDebug
```

After the build succeeds, install the APK on a device or emulator to launch the app.
