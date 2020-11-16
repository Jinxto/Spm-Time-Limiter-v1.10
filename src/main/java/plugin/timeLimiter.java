package plugin;



import java.io.File;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import org.bukkit.BanList;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

//only for setting up server stugg
public final class timeLimiter  extends JavaPlugin implements Listener, CommandExecutor{
	    int i;  //for event handler connected
	    int j; //for event handler disconnected
	    boolean k;
		String quit;
		boolean before;
		int temp;
		int temporarycomparin;
		String playerJoinTemp;
		Calendar calendar = Calendar.getInstance();
		double tempspm = ((737747.8334) -((calendar.get(Calendar.YEAR)*365)+((calendar.get(Calendar.MONTH)+1)*30.4167)+(calendar.get(Calendar.DATE))));
		int spm;
		ArrayList<String>playerJoin = new ArrayList<String>();
		ArrayList<Integer>playerTime= new ArrayList<Integer>();
		ArrayList<Integer>playerGameTime= new ArrayList<Integer>();
		ArrayList<Integer>playerQuitGameTime= new ArrayList<Integer>();
	    ArrayList<String>playerQuit = new ArrayList<String>();
	    ArrayList<Integer>playerGametempTime= new ArrayList<Integer>();
	    ArrayList<Integer>customTime= new ArrayList<Integer>();
	    ArrayList<Integer>playerQuitCustomTime= new ArrayList<Integer>();
	   
	    protected File customConfigFile;
	    protected FileConfiguration customConfig;
		 
		
		Server s;
	
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		 DateTimeFormatter joined = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		 LocalDateTime now = LocalDateTime.now();  
		 String datae= dtf.format(now);
	     String UserJoinedTime = joined.format(now);
	    @Override
	    public void onEnable() {
		  tempspm = Math.round(tempspm);
		  spm = (int)tempspm;
		  
		 createCustomConfig();
		 int x= (int) customConfig.get("numbers of quitted");
		 String previousDate= (String) customConfig.get("date");
		 if(datae.equals(previousDate)){
			if(x>0) {
			 j=x;
			 System.out.println("moving data.....");
			 for(int confug =0; confug<x; confug++) {
			playerQuit.add((String) customConfig.get("playerQuit"+confug));
			int customo =(int) customConfig.get("playerQuitCustomTime"+confug);
			int tempe= (int) customConfig.get("playerQuitGameTime"+confug);
			//((Number) obj.get("ipInt")).longValue();
			playerQuitGameTime.add(tempe);
			playerQuitCustomTime.add(customo);
		
			 } 
				 
			 }
		  }
		  if(!datae.equals(previousDate)) {
			 System.out.println(datae+" is not equal to"+" "+previousDate+" clearing offline memory......");
			   customConfig.set("numbers of quitted", 0);
	    	   customConfig.set("date", null);
		  for(int reset=0; reset<x; reset++) {
				customConfig.set("playerQuit"+reset, null);
				customConfig.set("playerQuitGameTime"+reset, null);
				customConfig.set("playerQuitCustomTime"+reset, null);
				
				
			}
			 try {
					customConfig.save(customConfigFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
		 }
	//	getServer().getPluginManager().registerEvents(new events(), this);
		  getCommand("spm").setExecutor(new commando());
		  getCommand("counter").setExecutor(this);
		  getServer().getPluginManager().registerEvents(this, this);
		
		 new BukkitRunnable() {
		       @Override
		       public void run() {
		    	  
		    		  
		    		   timeComparingInitializer();
		    	  
		       
		       }
		   }.runTaskTimer(this, 1200L, 1200L); //Delays in ticks
	
	 
		
         
        
	             
	   
		
		  getLogger().info("PluginEnabled!");
	        // TODO Insert logic to be performed when the plugin is enabled
	    }
	  @EventHandler
		public void onPlayerjoin(PlayerJoinEvent e) {
		 before = true;
		Calendar calendar = Calendar.getInstance();
		int Timenow= ((calendar.get(Calendar.YEAR)*525600)+((calendar.get(Calendar.MONTH)+1)*43800)+((calendar.get(Calendar.DATE)*1440))+((calendar.get(Calendar.HOUR_OF_DAY)+8)*60)+(calendar.get(Calendar.MINUTE)));
	
		   playerJoinTemp = e.getPlayer().getDisplayName();
		 
			/*ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			String command = "BannEdEqar";
			Bukkit.dispatchCommand(console, command); */
		   
			for(int op=0; op<j; op++) {
				
				
				
				// for player name get index and insert into player game time
				String playerComparing =playerQuit.get(op);
				
				if(playerJoinTemp.equals(playerComparing)) {
					
				
					playerGametempTime.add(playerQuitGameTime.get(op));
					System.out.println(playerQuitGameTime.get(op) );
					customTime.add(playerQuitCustomTime.get(op));
					playerQuit.remove(op);
					playerQuitGameTime.remove(op);
					playerQuitCustomTime.remove(op);
					
					j--;
					System.out.println("someone joined before");
					
					before = false;
					
					
					
				} 
			}
			// default
			
			playerJoin.add(e.getPlayer().getDisplayName());
			playerTime.add(Timenow);
			playerGameTime.add(0);
			
			if(before== true) {
		    customTime.add(30);
			playerGametempTime.add(0);
			}
				
			
			
			//set so that i can get it in the index section
			//size
			i++;
			System.out.println("Number of users join"+" "+i+" "+"Number of users left"+" "+j+" "+"User "+ e.getPlayer().getDisplayName()+" join at  "+ UserJoinedTime);
		    e.getPlayer().sendMessage(ChatColor.GREEN+"Welcome to"+ChatColor.GOLD+" The_Noob's server!"+" "+"and"+ChatColor.UNDERLINE+" SPM "+"is"+" "+ChatColor.RED+spm+" "+ChatColor.ITALIC+"days left! "+ChatColor.BLUE+"Mr "+ChatColor.BOLD+ playerJoin.get(i-1)+" "+"type /counter help to view the commands");
	       
			
	}
		
	   @EventHandler
	    public void onPlayerLeave(PlayerQuitEvent e) {
		   
	    	quit = e.getPlayer().getDisplayName();
	    	System.out.println(quit);
	    	
	    	for(int pop=0; pop<i; pop++) {
	    		if(playerJoin.get(pop)== quit) {
	    			if(k!=true) {
	    				System.out.println("user "+playerJoin.get(pop)+ " disconnected early, saving data for rejoining purpose...");
		    			
		    			playerQuit.add(quit);
		    			playerQuitGameTime.add(playerGameTime.get(pop));
		    			playerQuitCustomTime.add(customTime.get(pop));
		    		
		    			j++;
		    			
		    			
		    		    playerJoin.remove(pop);
		    			playerGameTime.remove(pop);
		    			
		    		    playerTime.remove(pop);
		    		
		    		    playerGametempTime.remove(pop);
		    		    customTime.remove(pop);
		    		    
		    			
		    			
		    			i--;
	    			}
	    			
	    			}
	    		
	    			
	    		} 
	    		
	    	}
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    
			
	    
	   @EventHandler
	   public void onPlayerKicked(PlayerKickEvent e) {
		  
		   for(int pop=0; pop<i; pop++) {
	    		if(playerJoin.get(pop)== quit) {
	    			if(k=true) {
	    				playerJoin.remove(pop);
		    			playerGameTime.remove(pop);
		    			
		    		    playerTime.remove(pop);
		    		    
		    		    playerGametempTime.remove(pop);
		    		    customTime.remove(pop);
		    		    System.out.println(playerGameTime.get(pop) +" banned ");
		    		    i--;
		    		    
	    			}
	   }
  }
	   }
	    public void timeComparingInitializer() {
	    	Calendar tarkov = Calendar.getInstance();
	      
	    	k = false;
	    	for(Player p : Bukkit.getServer().getOnlinePlayers()) {
	    	for(int q=0; q<i; q++) {
	    		System.out.println("calculating....");
	    		if(playerJoin.get(q)==p.getPlayer().getDisplayName()) {
	    			int comparing = 0;
	    			int currenttime = ((tarkov.get(Calendar.YEAR)*525600)+((tarkov.get(Calendar.MONTH)+1)*43800)+((tarkov.get(Calendar.DATE)*1440))+((tarkov.get(Calendar.HOUR_OF_DAY)+8)*60)+(tarkov.get(Calendar.MINUTE)));
	    			int warning = 0;
	    			if(playerGametempTime.get(q)>0) {
	    				comparing+= playerGametempTime.get(q);
	    				
	    			}
	    			     
	    				comparing += currenttime-playerTime.get(q);
		    			System.out.println(playerJoin.get(q)+" has a game time of "+comparing);
		    			playerGameTime.set(q, comparing);
	    			
		    		 warning = customTime.get(q)- playerGameTime.get(q);
		    		 if(warning == 180) {
		    			 p.getPlayer().sendMessage(ChatColor.GREEN+"You have"+" "+ChatColor.GREEN+"3 hours left before ban");
		    		 }
		    		 if(warning == 120) {
		    			 p.getPlayer().sendMessage(ChatColor.GREEN+"You have"+" "+ChatColor.GREEN+"2 hours left before ban");
		    		 }
		    		 if(warning == 60){
		    			 p.getPlayer().sendMessage(ChatColor.GREEN+"You have"+" "+ChatColor.GREEN+"1 hours left before ban");
		    		 }
		    		 if(warning == 30){
		    			 p.getPlayer().sendMessage(ChatColor.GREEN+"You have"+" "+ChatColor.GREEN+"30 minutes left before ban");
		    		 }
		    		 if(warning == 10) {
		    			 p.getPlayer().sendMessage(ChatColor.GREEN+"You have"+" "+ChatColor.YELLOW+"10 minutes left before ban");
		    		 }
		    		 if(warning == 5) {
		    			 p.getPlayer().sendMessage(ChatColor.GREEN+"You have"+" "+ChatColor.RED+"5 minutes left before ban");
		    		 }
		    		 if(warning == 1){
		    			 p.getPlayer().sendMessage(ChatColor.GREEN+"You have"+" "+ChatColor.RED+ChatColor.BOLD+"1 minutes left before ban");
		    		 }
	    			
	    			if(playerGameTime.get(q)>=customTime.get(q)) {
	    				System.out.println("Game Time Reached for User"+" "+p.getPlayer().getDisplayName()+" "+", intializing ban");
	    				
	    				banName(p.getPlayer().getDisplayName(),ChatColor.MAGIC+"asdasdasdasd"+" "+ChatColor.GREEN+"Im sorry to say that you need to go back to study because"+" "+ChatColor.BOLD+"SPM"+" is "+" "+ChatColor.RED+spm+" "+ChatColor.AQUA+"days left!"+" "+ChatColor.GOLD+"come back tommorrow!",playerJoin.get(q)+""+ChatColor.MAGIC+" asdasdasdasdasdasdasda");
	    				p.getPlayer().kickPlayer(ChatColor.MAGIC+"asdasdasdasd "+ChatColor.GREEN+"Tik Tok "+ChatColor.DARK_GRAY+ "Tik Tok "+ChatColor.RED+" SPM"+ChatColor.DARK_PURPLE+" is around the door,"+ChatColor.YELLOW+" better study "+ChatColor.RED+spm+" "+"day before,"+" "+ChatColor.BOLD+"so you won't regret no more!"+ChatColor.MAGIC+" asdasdasdasdasdasdasda");
	    				k = true;
	    				
	    			}
	    		}
	    	}
	    	
	    }
	   } 
	   public void banName(String name, String reason, String source) {
	    	Date dt = new Date(System.currentTimeMillis()+24*60*60*1000);
	    	
	    	


	     Bukkit.getBanList(BanList.Type.NAME).addBan(name, reason, dt, source);
	    } 
	   public void createCustomConfig() {
		    customConfigFile = new File(getDataFolder(), "custom.yml");
		    if (!customConfigFile.exists()) {
		        customConfigFile.getParentFile().mkdirs();
		        saveResource("custom.yml", false);
		     }
		    customConfig= new YamlConfiguration();
		    try {
		        customConfig.load(customConfigFile);
		    } catch (IOException | InvalidConfigurationException e) {
		        e.printStackTrace();
		    }
			}
	   public int getSpm() {
		   return spm;
	   }
	   @Override
	   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	   	if (sender instanceof Player) {
	   		////////////////////////////////////////////////////////
	   			if(args.length >0) {
	   				if(args[0].equals("1-hour")) {
	   					for(int commande = 0; commande<i; commande++) {
	   						if(((Player) sender).getPlayer().getDisplayName()== playerJoin.get(commande)) {
	   							customTime.set(commande, 60); //very inefficient
	   							System.out.println("Limiting game time for 1-hour");
	   						   sender.sendMessage(ChatColor.GREEN+"Limiting game time for 1-hour");
	   						}
	   				
	   					}
	   					
	   				
	   			
	   				}
	   				if(args[0].equals("2-hour")) {
	   					for(int commande = 0; commande<i; commande++) {
	   						if(((Player) sender).getPlayer().getDisplayName()== playerJoin.get(commande)) {
	   							customTime.set(commande, 120);
	   							System.out.println("Limiting game time for 2-hour");
	   							sender.sendMessage(ChatColor.YELLOW+"Limiting game time for 2-hour."+" "+ChatColor.BLUE+"Doing this frequently isn't a wise choice!");
	   						}
	   						
	   		}
	   		}
	   				if(args[0].equals("3-hour")) {
	   					for(int commande = 0; commande<i; commande++) {
	   						if(((Player) sender).getPlayer().getDisplayName()== playerJoin.get(commande)) {
	   							customTime.set(commande, 180);
	   							System.out.println("setting time for 1hour");
	   							sender.sendMessage(ChatColor.RED+"Limiting game time for 3-hour."+" "+ChatColor.RED+"Do you know what you're doing? 3 hour is too much!");
	   				}
	   						
	   						
	   	   }
	   }
	   				if(args[0].equals("gameTime")) {
	   					for(int commande = 0; commande<i; commande++) {
	   						if(((Player) sender).getPlayer().getDisplayName()== playerJoin.get(commande)) {
	   							int temper =(playerGameTime.get(commande)%(60));
	   							int temp =(playerGameTime.get(commande)/(60));
	   							int minuto = 0;
	   							if(temper!=0) {
	   								minuto =  (playerGameTime.get(commande));
	   								if(minuto>=60) {
	   									minuto = 0;
	   								}
	   								
	   							}
	   							sender.sendMessage("Your game time is "+temp+" hours "+minuto+" minutes");
	   							
	   						}
	   							
	   		
	   					}
	   				}
	   				if(args[0].equals("help")) {
	   					for(int commande = 0; commande<i; commande++) {
	   						if(((Player) sender).getPlayer().getDisplayName()== playerJoin.get(commande)) {
	   							sender.sendMessage(ChatColor.BLUE+"----------------------------------------------------------");
	   							sender.sendMessage(ChatColor.YELLOW+"/counter default, n-hour, n = 1,2,3,"+" "+ChatColor.RED+"Max 3 hours"+" "+ChatColor.GREEN+"Default 30 minutes (autoEnable)");
	   							sender.sendMessage(ChatColor.YELLOW+"/spm counter"+" " +ChatColor.GREEN+"Returns number of days before spm");
	   							sender.sendMessage(ChatColor.YELLOW+"/counter getTime"+" " +ChatColor.GREEN+"Returns the custom time set");
	   							sender.sendMessage(ChatColor.YELLOW+"/counter gameTime"+" " +ChatColor.GREEN+"Returns current gameTime");
	   							sender.sendMessage(ChatColor.BLUE+"----------------------------------------------------------");
	   						}
	   							
	   		
	   					}
	   				}
	   				if(args[0].equals("default")) {
	   					for(int commande = 0; commande<i; commande++) {
	   						if(((Player) sender).getPlayer().getDisplayName()== playerJoin.get(commande)) {
	   							customTime.set(commande, 30);
	   							System.out.println("Setting default time-limit which is 30 minutes");
	   							sender.sendMessage(ChatColor.GREEN+"Limiting game time to 30 minutes very wise!");
	   						}
	   							
	   		
	   					}
	   				}
	   				if(args[0].equals("getTime")) {
	   					for(int commande = 0; commande<i; commande++) {
	   						if(((Player) sender).getPlayer().getDisplayName()== playerJoin.get(commande)) {
	   							
	   							int ol =(customTime.get(commande)%(60));
	   							int oll =(customTime.get(commande)/(60));
	   							int molll = 0;
	   							if(ol!=0) {
	   								molll=  (customTime.get(commande));
	   								if(molll>=60) {
	   									molll = 0;
	   								
	   							}
	   							
	   						}
	   							sender.sendMessage("Your custom game time is "+oll+" hours "+molll+" minutes ");
	   							
	   		
	   					}
	   				}
	 
	     }
	   	}
	   	////////////////////////////////////////////////////////////
	    
	 }
	   	return false;
	   }
	   
	    @Override   //need to save date too btw
	    public void onDisable() {
	    	System.out.println("saving data");
	    	   customConfig.set("numbers of quitted", j);
	    	   customConfig.set("date", datae);
	    	for(int cfg=0; cfg<j; cfg++) {
				customConfig.set("playerQuit"+cfg, playerQuit.get(cfg));
				customConfig.set("playerQuitGameTime"+cfg, playerQuitGameTime.get(cfg));
				customConfig.set("playerQuitCustomTime"+cfg, playerQuitCustomTime.get(cfg));
				
			}
	    	 try {
					customConfig.save(customConfigFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
	    	
	    	getLogger().info("PluginDisabled");
	        // TODO Insert logic to be performed when the plugin is disabled
	    }
	}