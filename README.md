
# Voucher

In-game whitelist for Players to join Communities.


## Usage/Examples

Commands
- /voucher add <player> (voucher.add)
  - Grants a Voucher to the specified player to allow interaction with the world.
- /voucher remove <player> (voucher.remove)
  - Revokes a Voucher that has been assigned to a player and denies further interaction with the world.

Users with voucher.bypass will be excluded from interaction checks.


## FAQ

#### How does this plugin work?

Voucher works by hooking into the Event System and blocking key events that will prevent unauthorized access to the server.


## Deployment

This plugin is based on the PluginCommon API. Updates to PluginCommon will provide versioned and shaded assets associated with this plugin.

1. Update POM XML Plugins and Dependencies.
2. Update POM XML Version.
3. Build Plugin
4. Verify in 1.18 to 1.20.1
5. Update GitHub Source Code
6. Tag Version to Last Commit
7. Add Release
8. Update Spigot
