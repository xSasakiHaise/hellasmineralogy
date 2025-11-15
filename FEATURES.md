# HellasMineralogy

HellasMineralogy is the entitlement and bootstrapping module for the broader Hellas suite's mineralogy-related content. It does not currently ship in-game assets; instead, it ensures that the HellasControl core mod is present and correctly licensed so that future mineralogic features can hook into a validated environment. The mod runs on top of Minecraft 1.16.5 with Forge 36.2.x and is distributed as a lightweight sidemod that other Hellas projects can depend on.

## Feature Overview
- **Core/Entitlement validation** – Verifies that HellasControl is loaded before attempting any initialization and performs a dedicated-server entitlement check using the `CoreCheck` API.
- **Lifecycle hooks ready for expansion** – Registers common and client setup listeners so that mineralogy-specific registries, renderers, and data can be wired in as soon as they are implemented.
- **Consistent logging** – Provides deterministic startup logs so operators can confirm that the mineralogy component is active.

## Technical Overview
- The mod entry point is `com.xsasakihaise.hellasmineralogy.HellasMineralogy`, annotated with `@Mod` and registered through Forge's mod loading context.
- The common setup handler validates that HellasControl (and thereby HellasCore) is loaded. On dedicated servers it also calls `CoreCheck.verifyEntitled("mineralogy")` to enforce licensing.
- The client setup handler currently only logs activation but is the location where client-only registrations (render layers, GUIs) will be added later.
- No custom registries, commands, or assets are included yet; the module is intentionally skeletal so that future mineralogy mechanics can safely build on it.

## Extension Points
- **Adding mineralogy features** – Place new content under the `com.xsasakihaise.hellasmineralogy` package and register it from within the `onCommonSetup` or `onClientSetup` methods. Follow Forge best practices by using the mod event bus obtained via `FMLJavaModLoadingContext`.
- **Server-only logic** – Guard dedicated-server logic with `FMLEnvironment.dist == Dist.DEDICATED_SERVER` in the same way the entitlement check is implemented in `onCommonSetup`.
- **Dependency checks** – If additional Hellas modules become prerequisites, mirror the `ModList` inspection pattern demonstrated in `HellasMineralogy` so the mod gracefully skips initialization when requirements are missing.

## Dependencies & Environment
- Minecraft **1.16.5**
- Forge **36.2.42** (loader range `[36,)`)
- HellasControl **2.0.0+** (required for CoreCheck and entitlement verification)
- Built and packaged via Gradle using the configuration provided in `gradle.properties` and `mods.toml`.

## Notes for Future Migration
- The `CoreCheck` API calls are tightly coupled to the HellasControl mod. Any major update to HellasControl's entitlement system will require auditing `HellasMineralogy` to ensure compatibility.
- When upgrading to a newer Minecraft/Forge version, revalidate the lifecycle hook registrations in `HellasMineralogy` as Forge event signatures and the `Dist` enum often change between versions.
- Future mineralogy gameplay features should continue to isolate client/server logic within their respective setup handlers to minimize breaking changes when Pixelmon or Forge adjust rendering or server APIs.
