# OpenChamberPlugins · Android-каталог плагинов

Приложение-каталог плагинов OpenCode, MCP-серверов и дополнений OpenChamber.
Web-интерфейс в `app/src/main/assets/index.html` (+ `catalog.json`) выполняется
в WebView; команды установки запускаются через `Android.run(cmd)`
(`sh -c` в `MainActivity.kt`).

Данные каталога генерируются из `../openchamber-plugins/catalog.json`:

```sh
node gen-assets.js
```

## GitHub

Проект опубликован на GitHub:
- Репозиторий: https://github.com/vladimirgorodok/OpenChamberPluginsAndroid
- Релиз с APK v1.0: https://github.com/vladimirgorodok/OpenChamberPluginsAndroid/releases/tag/v1.0

## Готовые APK

- Подписанный (v2-схема, рекомендуемый): `/root/workspace/OpenChamberPluginsAndroid.apk`
  - GitHub: https://github.com/vladimirgorodok/OpenChamberPluginsAndroid/releases/download/v1.0/OpenChamberPluginsAndroid.apk
  - sha256: `c5850e34f993ff10bf14528c171ff2762b6a86f85c7a2207a24d2b175b5e01b1`
- Безподписанный (из `app/build/outputs/apk/release/app-release.apk`):
  - GitHub: https://github.com/vladimirgorodok/OpenChamberPluginsAndroid/releases/download/v1.0/OpenChamberPluginsAndroid-v1.0.apk
  - sha256: `356b7a16db4144c4c686a74a3a42a04faf573551819449143a68ec8dded27812`

Проверить целостность: `sha256sum OpenChamberPluginsAndroid.apk` → `c5850e34f993ff10bf14528c171ff2762b6a86f85c7a2207a24d2b175b5e01b1`.

## Сборка

```sh
./gradlew assembleRelease
```

На aarch64 можно использовать обёртку с переопределением aapt2 (см. `build-local.sh`):

```sh
./build-local.sh
```

APK: `app/build/outputs/apk/release/app-release.apk`.

Каталог сервера: `../openchamber-plugins` (`node server.js`, по умолчанию `http://127.0.0.1:8787/`).
