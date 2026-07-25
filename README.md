# 2FAS Auth + React Native

Experimental fork of [twofas/2fas-android](https://github.com/twofas/2fas-android)
testing brownfield support for existing Android codebases. Reference for
integrating React Native (via Expo) into an existing native app without
refactoring the project structure.

Uses Expo's brownfield **isolated** approach: the RN + Expo code is built into a
prebuilt **AAR** and consumed like any other Maven dependency. A floating "Expo"
button on the onboarding screen launches an `ExpoActivity` that renders the React
Native screen (JS bundle embedded in the release AAR, so no Metro).

## Integration steps

1. `npx create-expo-app@latest expo-app --template default@canary` (in `./expo-app`)
2. Build the AAR:
   ```sh
   cd expo-app
   npx expo install expo-brownfield expo-build-properties
   npx expo prebuild -p android --clean
   npx expo-brownfield build:android --release --fused --verbose
   ```
   Published to local Maven as `com.twofasapp.brownfield:twofasbrownfield-fused-release`.
3. Consume from `app/build.gradle.kts`, launch from `ExpoActivity`. Build:
   `assembleDebug`.

### Notes for reproducing the build

2FAS uses a convention-plugin build (`buildlogic`) that expects encrypted signing
config; for the fork these are replaced with placeholders:

- **`minSdk` 23 -> 24** (`buildlogic/.../AppConfig.kt`) — React Native floor.
- **`mavenLocal()`** (scoped) in `settings.gradle.kts` (`FAIL_ON_PROJECT_REPOS`).
- **`config/config.properties` + `config/debug_signing.jks`** — the
  `twofas.androidApplication` plugin reads signing config from these (the repo
  ships git-crypt-encrypted `.enc` versions). A standard debug keystore +
  matching properties are committed (no secrets).
- **`app/google-services.json`** — placeholder (the google-services plugin is
  applied unconditionally; no real Firebase project).
- `reactNativeArchitectures=arm64-v8a`. No compileSdk/Kotlin bump (compileSdk 36,
  Kotlin 2.2). No androidsvg/`maxSdkVersion` conflict (2FAS uses Coil without the
  SVG decoder and declares no legacy storage permissions).

---

<details>
<summary>2FAS Auth (original README)</summary>

# Open Source 2FAS for Android

This is the official Android app for the Open Source 2FAS project.

## What is 2FAS?

2FAS (Two-Factor Authentication Service) is a user authentication method that provides an additional layer of security for online accounts. In addition to a username and password, 2FAS uses a second factor, such as a one-time password (OTP) shown on a user's phone, to verify a user's identity. This helps prevent unauthorized access to accounts, even if a password is compromised.

## Features

- Support for time-based one-time passwords (TOTP) and HMAC-based on-time passwords (HOTP)
- Compatible with any service that supports the TOTP and HOTP standard, including Google, Microsoft, and Dropbox
- Easy to set up and use

## Graphics

Please note that the graphics used in this app are not part of the open source project and are subject to their own separate licensing terms.

## Bug Reporting

We use GitHub for bug reports. Please visit the [2FAS for Android issues page](https://github.com/twofas/2fas-android/issues) to search for and report any bugs you may have found. Before adding a new issue, please search for existing issues to avoid duplicates.

For reporting security issues only, please send a detailed description of the vulnerability to security@2fas.com. Do not use this address for general inquiries or bug reports unrelated to security concerns.

## Getting Started

1. Download the app from the [releases page](https://2fas.com).
2. Install the app on your Android device.
3. Follow the on-screen instructions to set up 2FAS for your online accounts.

## Contributing

We welcome contributions to the Open Source 2FAS project. If you would like to contribute, please see the [contribution guide](./CONTRIBUTING.md).

## Donations

If you would like to support the development of the Open Source 2FAS project, you can [make a donation](https://2fas.com/donate). All donations will be used to support the ongoing development and maintenance of the project.

We appreciate your support!

## License

Copyright (c) Two Factor Authentication Service, Inc. All rights reserved.

Licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).


</details>
