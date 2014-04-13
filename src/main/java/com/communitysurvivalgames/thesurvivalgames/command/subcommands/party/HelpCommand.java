/**
 * Name: HelpCommand.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.party;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

    /**
     * Displays the help for the party commands
     *
     * @param sender The player executing the command
     * @param args   The page of help to be shown
     */
    public void execute(String cmd, Player sender, String[] args) {
        if ((args.length == 0) || (cmd.equalsIgnoreCase("help")) || (cmd.equalsIgnoreCase("?"))) {
            staticExecute(sender, args);
        }
    }

    public static void staticExecute(Player sender, String[] args) {

        String help = ChatColor.YELLOW + "";
        if ((args.length == 0) || ((args.length == 1) && (args[0].equalsIgnoreCase("help")))) {
            help = I18N.getLocaleString("PARTY_HELP") + "\n";
            help = help + "------------------\n";
            help = help + I18N.getLocaleString("PARTY_HELP_1") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_2") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_3") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_4") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_5") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_6") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_7") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_8") + "\n";
            help = help + I18N.getLocaleString("PARTY_HELP_9");
        } else if ((args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?"))) {
            if (args[1].equalsIgnoreCase("invite")) {
                help = I18N.getLocaleString("PARTY_INVITE_HELP") + "\n";
                help = help + "-----------------\n";
                help = help + I18N.getLocaleString("PARTY_INVITE_HELP_1") + "\n";
                help = help + I18N.getLocaleString("PARTY_INVITE_HELP_2") + "\n";
                help = help + I18N.getLocaleString("PARTY_INVITE_HELP_3") + "\n";
            }
            if (args[1].equalsIgnoreCase("join")) {
                help = I18N.getLocaleString("PARTY_JOIN_HELP") + "\n";
                help = help + "---------------\n";
                help = help + I18N.getLocaleString("PARTY_JOIN_HELP_1") + "\n";
                help = help + I18N.getLocaleString("PARTY_JOIN_HELP_2") + "\n";
                help = help + I18N.getLocaleString("PARTY_JOIN_HELP_3") + "\n";
            }
            if (args[1].equalsIgnoreCase("leave")) {
                help = I18N.getLocaleString("PARTY_LEAVE_HELP") + "\n";
                help = help + "----------------\n";
                help = help + I18N.getLocaleString("PARTY_LEAVE_HELP_1") + "\n";
                help = help + I18N.getLocaleString("PARTY_LEAVE_HELP_2") + "\n";
                help = help + I18N.getLocaleString("PARTY_LEAVE_HELP_3") + "\n";
            }
            if (args[1].equalsIgnoreCase("decline")) {
                help = I18N.getLocaleString("PARTY_DECLINE_HELP") + "\n";
                help = help + "-----------------\n";
                help = help + I18N.getLocaleString("PARTY_DECLINE_HELP_1") + "\n";
                help = help + I18N.getLocaleString("PARTY_DECLINE_HELP_2") + "\n";
                help = help + I18N.getLocaleString("PARTY_DECLINE_HELP_3") + "\n";
            }
            if (args[1].equalsIgnoreCase("list")) {
                help = I18N.getLocaleString("PARTY_LIST_HELP") + "\n";
                help = help + "---------------\n";
                help = help + I18N.getLocaleString("PARTY_LIST_HELP_1") + "\n";
                if (sender.hasPermission("partymanager.admin.list")) {
                    help = help + I18N.getLocaleString("PARTY_LIST_HELP_2") + "\n";
                    help = help + I18N.getLocaleString("PARTY_LIST_HELP_3") + "\n";
                }

                help = help + I18N.getLocaleString("PARTY_LIST_HELP_4") + "\n";
                help = help + I18N.getLocaleString("PARTY_LIST_HELP_5") + "\n";
            }
            if (args[1].equalsIgnoreCase("kick")) {
                help = I18N.getLocaleString("PARTY_KICK_HELP") + "\n";
                help = help + "---------------\n";
                help = help + I18N.getLocaleString("PARTY_KICK_HELP_1");
                help = help + I18N.getLocaleString("PARTY_KICK_HELP_2");
                help = help + I18N.getLocaleString("PARTY_KICK_HELP_3");
            }
            if (args[1].equalsIgnoreCase("promote")) {
                help = I18N.getLocaleString("PARTY_PROMOTE_HELP") + "\n";
                help = help + "------------------\n";
                help = I18N.getLocaleString("PARTY_PROMOTE_HELP_1") + "\n";
                help = I18N.getLocaleString("PARTY_PROMOTE_HELP_2") + "\n";
                help = I18N.getLocaleString("PARTY_PROMOTE_HELP_3") + "\n";
            }
            if (args[1].equalsIgnoreCase("chat")) {
                help = I18N.getLocaleString("PARTY_CHAT_HELP") + "\n";
                help = help + "------------------\n";
                help = help + I18N.getLocaleString("PARTY_CHAT_HELP_1") + "\n";
                help = help + I18N.getLocaleString("PARTY_CHAT_HELP_2") + "\n";
                help = help + I18N.getLocaleString("PARTY_CHAT_HELP_3") + "\n";
            }
        }
        sender.sendMessage(help);
    }
}
