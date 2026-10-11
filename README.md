# Shopping Notepad for Android

A standalone Android shopping-list app written in Kotlin and Jetpack Compose. Lists are grouped by the date you plan to shop, and all data is stored locally on the device in a Room/SQLite database. The app has no account, server, network permission, or cloud dependency.

## Features

- Keep a separate shopping list for any day, with previous/next-day navigation and a date picker.
- Categories for Groceries, Vegetables, Fruits, Dairy, Prepared food, Meat & fish, Bakery, Household, Personal care, and Other.
- Optional quantity and note fields for each item.
- Check items off as you buy them, edit them, or delete them.
- Filter a day's list by category and see a progress bar for items bought.
- Optionally remove checked-off items from the selected day.
- Android Room database persists lists after the app closes.
- Light/dark appearance follows the Android system setting.

## Build locally

1. Open this folder in Android Studio (a recent version with Android SDK 36 support).
2. Allow Gradle sync to complete.
3. Run the `app` configuration on a device running Android 8.0 (API 26) or newer.

Or build a debug APK from a shell with Java 17, Android SDK platform 36, and Android build tools 36.0.0 installed:

```bash
./gradlew assembleDebug
```

The debug APK is created under `app/build/outputs/apk/debug/`. For a local release build, configure the signing environment variables described below before running `./gradlew assembleRelease`.

## Automatic GitHub Releases (APK only)

The workflow at `.github/workflows/release-apk.yml` runs on each push to `main`, and can also be started from **Actions → Build and publish APK → Run workflow**. It builds a signed release APK and uploads exactly one workflow asset: `Shopping-Notepad-x.y.z.apk`. It does not upload the source-project ZIP. GitHub itself also displays automatically generated **Source code (zip)** and **Source code (tar.gz)** links for release tags; this is GitHub platform behavior and cannot be disabled by this workflow. The release notes tell users to install the APK asset.

### 1. Create a persistent signing key

Use Java's `keytool` on your computer or Android/Termux. Keep the keystore and passwords private; do not commit the keystore to Git.

```bash
keytool -genkeypair -v \
  -keystore shopping-notepad-release.jks \
  -alias shopping-notepad \
  -keyalg RSA -keysize 2048 -validity 10000
```

Convert the keystore file to a single-line Base64 string. On Linux or Termux:

```bash
base64 -w 0 shopping-notepad-release.jks
```

On macOS, use `base64 shopping-notepad-release.jks` and remove any line breaks before saving the value as a GitHub secret.

### 2. Add these GitHub repository secrets

In your repository, open **Settings → Secrets and variables → Actions → New repository secret** and add:

| Secret | Value |
| --- | --- |
| `ANDROID_KEYSTORE_BASE64` | The Base64 output from the command above |
| `ANDROID_KEYSTORE_PASSWORD` | The keystore password you selected |
| `ANDROID_KEY_ALIAS` | `shopping-notepad` (or the alias you selected) |
| `ANDROID_KEY_PASSWORD` | The key password you selected |

The same keystore must be reused for future releases. Losing or replacing it means Android will not accept an APK as an in-place update of the previous signed app.

### 3. Push to `main`

Commit and push this project to a GitHub repository with the default branch named `main`. Make sure **Settings → Actions → General → Workflow permissions** allows workflows to read and write repository contents. The workflow uses `GITHUB_TOKEN` to create the tag and release; no personal access token is needed.

### Automatic versioning

- The first successful workflow run uses **1.0.0**.
- Each subsequent successful run increments the patch component: `1.0.1`, `1.0.2`, `1.0.3`, and so on.
- The workflow creates matching tags (`v1.0.0`, `v1.0.1`, …), sets the Android `versionName` and increases the numeric `versionCode` for each release.
- Versioning is based on the highest existing `vMAJOR.MINOR.PATCH` Git tag. Avoid manually creating conflicting version tags.

## Project structure

```text
ShoppingNotepadProject/
├── .github/workflows/release-apk.yml
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/shoppingnotepad/
│       │   ├── MainActivity.kt
│       │   ├── data/
│       │   └── ui/
│       └── res/
├── gradle/wrapper/gradle-wrapper.properties
├── build.gradle.kts
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

## Privacy and storage

Shopping data stays in the app's private local SQLite database (`shopping_notepad.db`). Uninstalling the app or clearing its storage removes that data. There is no sync or automatic backup/export feature in this initial version.
