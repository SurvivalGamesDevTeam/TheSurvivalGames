/**
 * Name: HelpCommand.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

    /**
     * An example of implementing SubCommand. DO NOT CALL DIRECTLY. Only use in
     * CommandHandler
     * 
     * @param cmd The command that was executed
     * @param p The player that executed the command
     * @param args The arguments after the command
     */
    @Override
    public void execute(String cmd, Player p, String[] args) {
        if (cmd.equalsIgnoreCase("help")) {
            if (args.length == 0) {
                p.chat("/sg help 1");
            } else if (args.length >= 1) {
                int page = 0;
                try {
                    page = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NO_HELP"));
                }

                switch (page) {
                    case 0:
                        p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NO_PAGE"));
                        break;

                    case 1:
                        sendHelpMessages(p,
                                1,
                                I18N.getLocaleString("COMMAND_HELP_1"),
                                I18N.getLocaleString("COMMAND_HELP_2"),
                                I18N.getLocaleString("COMMAND_HELP_69"),
                                I18N.getLocaleString("COMMAND_HELP_3"),
                                I18N.getLocaleString("COMMAND_HELP_4"),
                                I18N.getLocaleString("COMMAND_HELP_5"));
                        break;

                    case 2:
                        sendHelpMessages(p,
                                2,
                                I18N.getLocaleString("COMMAND_HELP_6"),
                                I18N.getLocaleString("COMMAND_HELP_96"),
                                I18N.getLocaleString("COMMAND_HELP_7"),
                                I18N.getLocaleString("COMMAND_HELP_8"),
                                I18N.getLocaleString("COMMAND_HELP_9"),
                                I18N.getLocaleString("COMMAND_HELP_10"));
                        break;

                    case 3:
                        sendHelpMessages(p,
                                3,
                                I18N.getLocaleString("COMMAND_HELP_11"),
                                I18N.getLocaleString("COMMAND_HELP_12"),
                                I18N.getLocaleString("COMMAND_HELP_13"),
                                I18N.getLocaleString("COMMAND_HELP_14"),
                                I18N.getLocaleString("COMMAND_HELP_15"));
                        p.sendMessage(ChatColor.GOLD + "--------------" + ChatColor.DARK_AQUA + I18N.getLocaleString("COMMAND_HELP_16") + ChatColor.DARK_AQUA + "--------------");
                        break;

                    default:
                        p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NO_PAGE"));
                }

            }

        }
    }

    /**
     * Send specific messages to the player
     * 
     * @param p The player to send the help message to
     * @param page The page number
     * @param args The help messages separated by a : to display command and
     *        help. Limit 5
     */
    private void sendHelpMessages(Player p, int page, String... args) {
        p.sendMessage(ChatColor.GOLD + "--------------" + ChatColor.DARK_AQUA + "[The Survival Games]" + ChatColor.DARK_AQUA + "--------------");
        p.sendMessage(ChatColor.GOLD + "Page: " + ChatColor.GREEN + page + ChatColor.GOLD + " of 3");
        for (String s : args) {
            String[] split = s.split(": ");

            p.sendMessage(ChatColor.GREEN + split[0] + ChatColor.DARK_AQUA + ": " + ChatColor.GOLD + split[1]);
        }
    }
}
