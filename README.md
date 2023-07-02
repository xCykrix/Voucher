
# Voucher

In-game whitelist for Players to join Communities.


## Usage/Examples

Commands
- /voucher add <player> (voucher.add)
  - Grants a Voucher to the specified player to allow interaction with the world.
- /voucher remove <player> (voucher.remove)
  - Revokes a Voucher that has been assigned to a player and denies further interaction with the world.


## Documentation

config.yml

```yml
# Protections to Enable.
prevent-interact: true # PlayerInteractEvent
prevent-entity-interact: true # PlayerInteractEntityEvent
prevent-armor-stand-interact: true # PlayerArmorStandManipulateEvent
prevent-block-place: true # BlockPlaceEvent
prevent-block-break: true # BlockBreakEvent
prevent-item-pickup: true # EntityPickupItemEvent
prevent-item-drop: true # PlayerDropItemEvent
prevent-damage: true # EntityDamageEvent
prevent-hunger: true # FoodLevelChangeEvent
prevent-edit-book: true # PlayerEditBookEvent
prevent-hanging-place: true # HangingPlaceEvent
prevent-hanging-break: true # HangingBreakByEntityEvent

# DO NOT EDIT THE VERSION. Manual edits may corrupt or reset configuration files.
#
version: 1
```

## FAQ

#### How does this plugin work?

Voucher works by hooking into the Spigot Event System to block key player interaction events until they have been granted a "Voucher".


## Deployment

This plugin is based on the PluginCommon API. Updates to PluginCommon will provide verioned and shaded assets associated with this plugin.

1. Update POM XML Plugins and Dependencies.
2. Update POM XML Version.
3. Build Plugin
4. Verify in 1.18 to 1.20.1
5. Update GitHub Source Code
6. Tag Version to Last Commit
7. Add Release
8. Update Spigot
