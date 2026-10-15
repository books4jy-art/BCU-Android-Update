# BCU_Android
Battle Cats Ultimate for Androids

---

## This copy: BCU KR

Not made by me: Battle Cats Ultimate and BCU Android are by the BCU team
([battlecatsultimate](https://github.com/battlecatsultimate)); Battle Cats is © PONOS.

This is [BCU_Android](https://github.com/battlecatsultimate/BCU_Android) (master, 0.18.13, July 2024) with its full
history, brought up to date like BCU's PC version and set up for the Korean version of the game. BCU's own code (the
core, `app/src/main/java/common`, a submodule of
[BCU_java_util_common](https://github.com/battlecatsultimate/BCU_java_util_common)) is not edited.

What's different from the official app:

- **Core 0.7.20.0** (BCU_java_util_common `data_focus`, the same core as the PC version plus the support needed for
  BCU's newest asset packs, game version 15.5), instead of the 2024 core. The Android code is adapted where the core
  changed: orb slots now belong to the cat, not the form (`androidutil/lineup/OrbCompat.kt` gives the orb screens
  the old shape), traits no longer have a "base game" flag, treasure functions take combo bonuses as numbers,
  realistic levelling takes the stage's limits, and the battle view's layer and summoner fields were renamed.
- **Korean data**: the Korean game's versions of files that differ from BCU's (Japanese) ones are in
  `app/src/main/assets/kr` and replace BCU's files after the asset packs are read (`androidutil/io/KoreanData.kt`):
  101 stage files plus talents and orb slots (`SkillAcquisition.csv`, `equipmentslot.csv`) from the Korean 15.6.0
  `DataLocal` ([fieryhenry/BCData](https://git.battlecatsmodding.org/fieryhenry/BCData/src/commit/2a49665d0135283dc5c86e8d5825bb120b2849d7/game_data/kr/15.6.0/DataLocal)),
  and 78 Korean enemy bases and backgrounds from the Korean server packs. Cat stats, max levels, combos, orbs and
  castle parts are the same in both versions. These are the same files the Battle Cats Emulator website uses (made
  by its `tools/kr_overlay.py`).
- **Installs next to the official app**: app ID `com.mandarin.bcu.kr`, name "BCU KR", its own file-provider name,
  and it never offers the official APKs as updates.
- **Builds without the BCU team's private files**: no Firebase (crash notes go to Android's log, tag "BCU"), no
  developer password, and error logs are only uploaded if the team's Drive key (`res/raw/service_key`) is added.

## Building

Needs JDK 17+ and the Android SDK (platform 34). Put `sdk.dir=...` in `local.properties`, then:

```sh
git submodule update --init
./gradlew assembleRelease     # app/build/outputs/apk/release/app-release.apk
```

Signing: with a `keystore.properties` in the project folder (`storeFile`, `storePassword`, `keyAlias`,
`keyPassword`) the APK is signed with that key; otherwise with the standard debug key. Keep the key private (this
repository is public) and keep using the same one: Android only installs an update signed with the same key.

## Updating from BCU

```sh
git remote add upstream https://github.com/battlecatsultimate/BCU_Android   # once
git fetch upstream && git merge upstream/master
cd app/src/main/java/common && git fetch origin && git checkout <core commit> && cd -   # newer core, if wanted
```
