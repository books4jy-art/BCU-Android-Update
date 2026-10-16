# BCU_Android
Battle Cats Ultimate for Androids

---

## This copy: BCU KR

### ⬇️ [Download the APK](https://github.com/books4jy-art/BCU-Android-Update/releases/latest)

Open the latest release, download **BCU-KR-….apk** and open it on your Android phone (allow installing apps from
that source when asked). It installs next to the official BCU app, and newer releases install over it.

Not made by me: Battle Cats Ultimate and BCU Android are by the BCU team
([battlecatsultimate](https://github.com/battlecatsultimate)); Battle Cats is © PONOS.

This is [BCU_Android](https://github.com/battlecatsultimate/BCU_Android) (master, 0.18.13, July 2024) with its full
history, brought up to date like BCU's PC version and set up for the game's country versions (Korea by default). BCU's own code (the
core, `app/src/main/java/common`, a submodule of
[BCU_java_util_common](https://github.com/battlecatsultimate/BCU_java_util_common)) is not edited.

What's different from the official app:

- **Core 0.7.20.0** (BCU_java_util_common `data_focus`, the same core as the PC version plus the support needed for
  BCU's newest asset packs, game version 15.5), instead of the 2024 core. The Android code is adapted where the core
  changed: orb slots now belong to the cat, not the form (`androidutil/lineup/OrbCompat.kt` gives the orb screens
  the old shape), traits no longer have a "base game" flag, treasure functions take combo bonuses as numbers,
  realistic levelling takes the stage's limits, and the battle view's layer and summoner fields were renamed.
- **Country versions** (Settings > Game version; Korea by default): the game's versions of files that differ from
  BCU's (Japanese 15.5) ones, from version 15.6.0 of each country in
  [fieryhenry/BCData](https://git.battlecatsmodding.org/fieryhenry/BCData/src/commit/2a49665d0135283dc5c86e8d5825bb120b2849d7/game_data),
  in `app/src/main/assets/<kr|jp|en|tw>` and put in place of BCU's files after the asset packs are read
  (`androidutil/io/CountryData.kt`; changing it restarts the app):
  - Korea 15.6: 95 stages, talents, orb slots, cat prices, 1 cat's stats, plus 78 Korean enemy bases and
    backgrounds from the Korean server packs;
  - Japan 15.6 (newer than BCU's 15.5): 14 stages, talents, orb slots, cat prices, 2 cats' stats;
  - English 15.6 and Taiwan 15.6: 68 stages each, talents, orb slots, cat prices, 1 cat's stats;
  - or BCU's own data unchanged.

  `tools/country_data.py` makes these and only takes what BCU can use safely: no stage that needs enemies,
  backgrounds or enemy bases BCU doesn't have, no cat form or spirit BCU has no pictures for, no placeholder data
  for unreleased or removed cats, no table missing rows for cats or enemies BCU has, no talent types the core
  doesn't know, no combos with cats BCU doesn't have. Cat stats, max levels, combos, orbs and castle parts are
  otherwise the same in all versions.
- **Fixes for the newer core** (the official app's screens were written for the 2024 core): no crash in the effect
  viewers, on talent names (Blast, Counter-Surge, Imu. Blast) or on enemies with the Base trait; the Supervillain
  trait, Supervillain Slayer, the new abilities (Blast, Lethargy, Delay, HP regeneration, speed-up and their
  immunities) and combos (Supervillain Slayer, Wave/Surge Immune, Deploy Cost Down) are shown; the SR and G stage
  collections are listed; the ability texts are the core's current ones.
- **Battles as in BCU PC**: orbs a cat can't have yet (form/level) or the stage bans are left out of the battle (before,
  only with "realistic levels" on), stage level limits use the stage's unit-group limits and also cap talents, Rich
  Cat and Sniper the Cat are off on stages that ban them, and a battle no longer changes the saved lineup's orbs or
  talents. Also: a cat's talents are no longer cleared when its settings are opened in a form without talents,
  respawn times count only combos that include that cat, and spirit slots show correctly when a summoner died.
- **Installs next to the official app**: app ID `com.mandarin.bcu.kr`, name "BCU KR", its own file-provider name,
  and it never offers the official APKs as updates.
- **Builds without the BCU team's private files**: no Firebase (crash notes go to Android's log, tag "BCU"), no
  developer password, and error logs are only uploaded if the team's Drive key (`res/raw/service_key`) is added.

## Updating the country data

```sh
python3 tools/country_data.py kr --keep-images   # --keep-images keeps the Korean pictures (not in DataLocal)
python3 tools/country_data.py jp                 # also en, tw; --version for another game version
```

## Building

Needs JDK 17+ and the Android SDK (platform 34). Put `sdk.dir=...` in `local.properties`, then:

```sh
git submodule update --init
./gradlew assembleRelease     # app/build/outputs/apk/release/app-release.apk
```

Signing: with a `keystore.properties` in the project folder (`storeFile`, `storePassword`, `keyAlias`,
`keyPassword`) the APK is signed with that key; otherwise with the standard debug key. Keep the key private (this
repository is public) and keep using the same one: Android only installs an update signed with the same key.

## Publishing a release

The "Release APK" workflow (`.github/workflows/release.yml`) builds the APK, signs it with the app's key and
publishes it as a GitHub Release. Start it under Actions > "Release APK" > Run workflow (or push a tag such as
`v0.18.13-kr2`). It needs the repository secrets `BCU_KR_KEYSTORE` (the key file, base64) and `BCU_KR_KEY_PASSWORD`.
For a new version, raise `versionName` (and `versionCode`) in `app/build.gradle` first.

## Updating from BCU

```sh
git remote add upstream https://github.com/battlecatsultimate/BCU_Android   # once
git fetch upstream && git merge upstream/master
cd app/src/main/java/common && git fetch origin && git checkout <core commit> && cd -   # newer core, if wanted
```
