# Instalación

En la carpeta `apk` hay un APK no firmado. Para instalarlo:

    $ adb install apk/neonstore.apk

También se puede compilar via:

    $ ./gradlew assembleDebug

Esto deposita el archivo apk en `./app/build/outputs/apk/app-debug.apk`.
