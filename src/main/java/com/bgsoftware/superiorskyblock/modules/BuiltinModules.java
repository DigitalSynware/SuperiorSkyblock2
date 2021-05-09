package com.bgsoftware.superiorskyblock.modules;

import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import com.bgsoftware.superiorskyblock.modules.bank.BankModule;
import com.bgsoftware.superiorskyblock.modules.generators.GeneratorsModule;
import com.bgsoftware.superiorskyblock.modules.missions.MissionsModule;
import com.bgsoftware.superiorskyblock.modules.upgrades.UpgradesModule;

public final class BuiltinModules {

    private BuiltinModules(){

    }

    public static final GeneratorsModule GENERATORS = new GeneratorsModule();
    public static final MissionsModule MISSIONS = new MissionsModule();
    public static final BankModule BANK = new BankModule();
    public static final UpgradesModule UPGRADES = new UpgradesModule();

    public static PluginModule getBuiltinModule(String name){
        switch (name.toLowerCase()){
            case "generators":
                return GENERATORS;
            case "missions":
                return MISSIONS;
            case "bank":
                return BANK;
            case "upgrades":
                return UPGRADES;
            default:
                return null;
        }
    }

}
