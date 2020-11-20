package fr.patatedouce.utils.command;

import fr.patatedouce.Vision;

public abstract class BaseCommand {

    public Vision main = Vision.getInstance();

    public BaseCommand() {
        main.getFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
