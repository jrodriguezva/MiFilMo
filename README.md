# MiFilMo

This is an example Android Application that follow up Clean Architecture principles using MVVM pattern with Google architecture components and kotlin

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/jrodriguezva/MiFilMo.git
```
### Configuration
This project use [Secrets Gradle Plugin](https://github.com/google/secrets-gradle-plugin) for providing the secrets securely to Android project.

You must add the next configuration to `local.properties` file:
```
API_KEY=xxxxxxxxx
GOOGLE_API_KEY=xxxxxxxxxx.apps.googleusercontent.com
SERVER_KEY=xxxxxx:xxxl_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx-xxxxxxxxx_xxxxxxxxxxxxxxxxxxxxxxxxx

store_file=../keystore/private.jks
store_password=123456
key_alias=alias
key_password=7891011
``` 

To get the API_KEY go to https://www.themoviedb.org/settings/api, create your API and paste it replacing xxxxxxx 

Next step is create your firebase project. 
1. Go to https://console.firebase.google.com and create your project
2. To activate authentication go to Authentification tab, press get started, and into the sign-in method tab enable email/password and google providers
3. Activate Realtime Database and storage
4. Download your google-services.json and move it into root project

To get GOOGLE_API_KEY go to Authentification tab, and into the sign-in method click on Google provider and copy `web client ID`

To get SERVER_KEY go to firebase project setting and into cloud messaging tab, copy the `Server key`.

The last of file is the key to sign release build. You can create a jks file and set data or remove all properties and the `build.config` release sign configuration

## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request 
