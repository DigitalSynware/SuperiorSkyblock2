package com.bgsoftware.superiorskyblock.island.permissions;

import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import com.bgsoftware.superiorskyblock.api.island.PermissionNode;
import com.bgsoftware.superiorskyblock.structure.EnumerateMap;
import com.bgsoftware.superiorskyblock.utils.debug.PluginDebugger;
import com.google.common.base.Preconditions;

import java.util.Map;

@SuppressWarnings("WeakerAccess")
public abstract class PermissionNodeAbstract implements PermissionNode {

    protected final EnumerateMap<IslandPrivilege, PrivilegeStatus> privileges;

    protected PermissionNodeAbstract() {
        this.privileges = new EnumerateMap<>(IslandPrivilege.values());
    }

    protected PermissionNodeAbstract(EnumerateMap<IslandPrivilege, PrivilegeStatus> privileges) {
        this.privileges = new EnumerateMap<>(privileges);
    }

    protected void setPermissions(String permissions, boolean checkDefaults) {
        if (!permissions.isEmpty()) {
            String[] permission = permissions.split(";");
            for (String perm : permission) {
                String[] permissionSections = perm.split(":");
                try {
                    IslandPrivilege islandPrivilege = IslandPrivilege.getByName(permissionSections[0]);
                    if (permissionSections.length == 2) {
                        privileges.put(islandPrivilege, PrivilegeStatus.of(permissionSections[1]));
                    } else {
                        if (!checkDefaults || !isDefault(islandPrivilege))
                            privileges.put(islandPrivilege, PrivilegeStatus.ENABLED);
                    }
                } catch (NullPointerException ignored) {
                    // Ignored - invalid privilege.
                } catch (Exception error) {
                    PluginDebugger.debug(error);
                }
            }
        }
    }

    @Override
    public abstract boolean hasPermission(IslandPrivilege permission);

    @Override
    public void setPermission(IslandPrivilege islandPrivilege, boolean value) {
        Preconditions.checkNotNull(islandPrivilege, "islandPrivilege parameter cannot be null.");
        this.privileges.put(islandPrivilege, value ? PrivilegeStatus.ENABLED : PrivilegeStatus.DISABLED);
    }

    @Override
    public Map<IslandPrivilege, Boolean> getCustomPermissions() {
        return this.privileges.collect(IslandPrivilege.values(),
                privilegeStatus -> privilegeStatus == PrivilegeStatus.ENABLED);
    }

    @Override
    public abstract PermissionNodeAbstract clone();

    protected boolean isDefault(IslandPrivilege islandPrivilege) {
        return false;
    }

    protected enum PrivilegeStatus {

        ENABLED,
        DISABLED,
        DEFAULT;

        static PrivilegeStatus of(String value) throws IllegalArgumentException {
            switch (value) {
                case "0":
                    return DISABLED;
                case "1":
                    return ENABLED;
                default:
                    return valueOf(value);
            }
        }

        static PrivilegeStatus of(byte value) throws IllegalArgumentException {
            switch (value) {
                case 0:
                    return DISABLED;
                case 1:
                    return ENABLED;
                default:
                    throw new IllegalArgumentException("Invalid privilege status: " + value);
            }
        }

        @Override
        public String toString() {
            switch (this) {
                case ENABLED:
                    return "1";
                case DISABLED:
                    return "0";
                default:
                    return name();
            }
        }

    }


}
