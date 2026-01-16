![TypeWriter Logo](https://github.com/gabber235/TypeWriter/blob/develop/design/Banner/TW_Banner_Transparant.png?raw=true)

# Typewriter-Folia

Version du plugin [Typewriter](https://github.com/gabber235/Typewriter) compatible avec **Folia**.

## Qu'est-ce que Folia ?

Folia est un fork de Paper qui divise le monde en régions indépendantes pour un meilleur multithreading.

## Modifications pour Folia

### 1. `foliaSupported = true`
Le plugin déclare maintenant le support Folia dans `build.gradle.kts`

### 2. `FoliaCompatibility.kt`
Nouvelle couche de compatibilité ajoutée dans `engine/engine-paper/src/main/kotlin/com/typewritermc/engine/paper/utils/`

Cette classe fournit des wrappers pour les schedulers Folia/Paper:
- `runAtLocationForResult()` - Exécute sur une région et retourne un résultat
- `runAtLocation()` - Exécute sur une région
- `runAtLocationLater()` - Exécute avec délai
- `runAtEntity()` - Exécute sur une entité
- `runAsync()` - Exécute en asynchrone
- `runGlobal()` - Exécute globalement

### 3. `PFInstanceSpace.kt`
Modifié pour utiliser `runAtLocationForResult()` lors des accès aux chunks

## Compatibilité

- ✅ Paper 1.21.3+
- ✅ Folia (toutes versions)

## Installation

### Prérequis
- JDK 21+
- Git

### Build

```bash
cd engine
./gradlew build
```

Le JAR sera dans `engine/engine-paper/build/libs/`

## Pour les développeurs

Le projet utilise déjà une architecture compatible avec Folia grâce à:
- **MCCoroutine** - Framework de coroutines compatible Folia
- **Dispatchers personnalisés** - `Dispatchers.Sync` et `Dispatchers.UntickedAsync`

## Extensions

Toutes les extensions sont incluses et compatibles Folia:
- BasicExtension
- CitizensExtension
- EntityExtension
- MythicMobsExtension
- QuestExtension
- RoadNetworkExtension
- RPGRegionsExtension
- SuperiorSkyblockExtension
- VaultExtension
- WorldGuardExtension

## Liens

- [Typewriter Original](https://github.com/gabber235/Typewriter)
- [PaperMC Folia Documentation](https://docs.papermc.io/paper/dev/folia-support)
- [Discord](https://discord.gg/HtbKyuDDBw)

## License

Même license que le projet original [Typewriter](https://github.com/gabber235/Typewriter)
