package plugin;

import java.util.Calendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;


public class commando implements CommandExecutor {
	
@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	if (sender instanceof Player) {
		
			if(args.length >0) {
				if(args[0].equals("counter")) {
					
				
			
			Calendar calendar = Calendar.getInstance();
			double tempspm = ((737747.8334) -((calendar.get(Calendar.YEAR)*365)+((calendar.get(Calendar.MONTH)+1)*30.4167)+(calendar.get(Calendar.DATE))));
			tempspm= Math.round(tempspm);
			int spem = (int)tempspm;
			String spm = String.valueOf("Spm is "+spem+" days left don't worry");
			sender.sendMessage(spm);
				}
		}
		}
		
	
	return false;
}
}
