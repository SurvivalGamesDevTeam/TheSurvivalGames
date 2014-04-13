package com.communitysurvivalgames.thesurvivalgames.command;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.HelpCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PartyCommandHandler implements CommandExecutor {

    private static final Map<String, SubCommand> commands = new HashMap<>();

    /**
     * Registers a command
     *
     * @param cmd   The command to register
     * @param clazz The class to register the command to. Must implement
     *              SubCommand.
     */
    public static void register(String cmd, SubCommand clazz) {
        try {
            Class.forName(clazz.getClass().getName());
        } catch (ClassNotFoundException e) {
            return;
        }

        if (!SubCommand.class.isAssignableFrom(clazz.getClass())) {
            throw new IllegalArgumentException("Class does not implement SubCommand");
        } else {
            commands.put(cmd, clazz);
        }
    }

    /**
     * Gets the SubCommand represnted by a specific Command
     *
     * @param cmd The name of the command to get
     * @return The SubCommand of the command
     */
    SubCommand getCommand(String cmd) throws CommandException {
        if (commands.containsKey(cmd)) {
            return commands.get(cmd);
        } else {
            throw new CommandException("This command was not found.");
        }
    }

    /**
     * The main executor for the SubCommands. DO NOT CALL.
     *
     * @param sender       The CommandSender that executed the command
     * @param command      The Command executed
     * @param commandLabel The command's label
     * @param args         The arguments after the command seperated by a space
     * @return Whether or not the command was executed successfully
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (command.getName().equalsIgnoreCase("party") && sender instanceof Player) {
            if (args.length == 2) {
                try {
                    getCommand(args[0]).execute(args[0], (Player) sender, new String[]{args[1]});
                } catch (CommandException e) {
                    sender.sendMessage(/* error prefix */I18N.getLocaleString("NO_COMMAND"));
                }
            } else if (args.length == 1) {
                try {
                    getCommand(args[0]).execute(args[0], (Player) sender, new String[]{});
                } catch (CommandException e) {
                    sender.sendMessage(/* error prefix */I18N.getLocaleString("NO_COMMAND"));
                }
            } else if (args.length == 0) {
                HelpCommand.staticExecute((Player) sender, new String[]{});
            } else {
                HelpCommand.staticExecute((Player) sender, new String[]{});
            }
            return true;

        } else if (!(sender instanceof Player)) {
            sender.sendMessage(I18N.getLocaleString("ONLY_PLAYERS"));
            return true;
        }

        return false;
    }
}
