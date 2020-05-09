package eu.revils.revilspvp;

import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.comphenix.protocol.ProtocolLibrary;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.qrakn.morpheus.Morpheus;
import eu.revils.revilspvp.follow.FollowHandler;
import eu.revils.revilspvp.kit.KitHandler;
import eu.revils.revilspvp.lobby.LobbyHandler;
import eu.revils.revilspvp.party.PartyHandler;
import eu.revils.revilspvp.kt.menu.ButtonListeners;
import eu.revils.revilspvp.kt.protocol.InventoryAdapter;
import eu.revils.revilspvp.kt.protocol.LagCheck;
import eu.revils.revilspvp.kt.protocol.PingAdapter;
import eu.revils.revilspvp.kt.uuid.RedisUUIDCache;
import eu.revils.revilspvp.kt.redis.RedisCredentials;
import eu.revils.revilspvp.kt.command.CommandHandler;
import eu.revils.revilspvp.kt.redis.Redis;

import eu.revils.revilspvp.kt.util.serialization.*;
import eu.revils.revilspvp.kt.uuid.UUIDCache;
import eu.revils.revilspvp.kt.visibility.VisibilityEngine;
import eu.revils.revilspvp.morpheus.EventListeners;
import eu.revils.revilspvp.pvpclasses.PvPClassHandler;
import eu.revils.revilspvp.tournament.TournamentHandler;
import eu.revils.revilspvp.util.event.HalfHourEvent;
import eu.revils.revilspvp.util.event.HourEvent;
import net.frozenorb.qlib.nametag.FrozenNametagHandler;
import net.frozenorb.qlib.scoreboard.FrozenScoreboardHandler;
import net.frozenorb.qlib.tab.FrozenTabHandler;
import net.frozenorb.qlib.tab.TabAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.frozenorb.chunksnapshot.ChunkSnapshot;
import eu.revils.revilspvp.arena.ArenaHandler;
import eu.revils.revilspvp.duel.DuelHandler;
import eu.revils.revilspvp.elo.EloHandler;
import eu.revils.revilspvp.kittype.KitType;
import eu.revils.revilspvp.kittype.KitTypeJsonAdapter;
import eu.revils.revilspvp.kittype.KitTypeParameterType;
import eu.revils.revilspvp.listener.BasicPreventionListener;
import eu.revils.revilspvp.listener.BowHealthListener;
import eu.revils.revilspvp.listener.NightModeListener;
import eu.revils.revilspvp.listener.PearlCooldownListener;
import eu.revils.revilspvp.listener.RankedMatchQualificationListener;
import eu.revils.revilspvp.listener.TabCompleteListener;
import eu.revils.revilspvp.match.Match;
import eu.revils.revilspvp.match.MatchHandler;
import eu.revils.revilspvp.nametag.RevilsPvPNametagProvider;
import eu.revils.revilspvp.postmatchinv.PostMatchInvHandler;
import eu.revils.revilspvp.queue.QueueHandler;
import eu.revils.revilspvp.rematch.RematchHandler;
import eu.revils.revilspvp.scoreboard.RevilsPvPScoreboardConfiguration;
import eu.revils.revilspvp.setting.SettingHandler;
import eu.revils.revilspvp.statistics.StatisticsHandler;
import eu.revils.revilspvp.tab.RevilsPvPLayoutProvider;
import org.spigotmc.SpigotConfig;

public final class RevilsPvP extends JavaPlugin {

    private static RevilsPvP instance;

    @Getter private static Gson gson = new GsonBuilder()
        .registerTypeHierarchyAdapter(PotionEffect.class, new PotionEffectAdapter())
        .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
        .registerTypeHierarchyAdapter(Location.class, new LocationAdapter())
        .registerTypeHierarchyAdapter(Vector.class, new VectorAdapter())
        .registerTypeAdapter(BlockVector.class, new BlockVectorAdapter())
        .registerTypeHierarchyAdapter(KitType.class, new KitTypeJsonAdapter()) // custom KitType serializer
        .registerTypeAdapter(ChunkSnapshot.class, new ChunkSnapshotAdapter())
        .serializeNulls()
        .create();

    public static Gson plainGson = new GsonBuilder()
            .registerTypeHierarchyAdapter(PotionEffect.class, new PotionEffectAdapter())
            .registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter())
            .registerTypeHierarchyAdapter(Location.class, new LocationAdapter())
            .registerTypeHierarchyAdapter(Vector.class, new VectorAdapter())
            .registerTypeAdapter(BlockVector.class, new BlockVectorAdapter())
            .serializeNulls()
            .create();

    private MongoClient mongoClient;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private SettingHandler settingHandler;
    @Getter private DuelHandler duelHandler;
    @Getter private KitHandler kitHandler;
    @Getter private LobbyHandler lobbyHandler;
    private ArenaHandler arenaHandler;
    @Getter private MatchHandler matchHandler;
    @Getter private PartyHandler partyHandler;
    @Getter private QueueHandler queueHandler;
    @Getter private RematchHandler rematchHandler;
    @Getter private PostMatchInvHandler postMatchInvHandler;
    @Getter private FollowHandler followHandler;
    @Getter private EloHandler eloHandler;
    @Getter private PvPClassHandler pvpClassHandler;
    @Getter private TournamentHandler tournamentHandler;
    @Getter public Redis redis;
    @Getter public CommandHandler commandHandler;
    @Getter public VisibilityEngine visibilityEngine;
    @Getter public UUIDCache uuidCache;
    @Getter private ChatColor dominantColor = ChatColor.GOLD;
    @Getter private RevilsPvPCache cache = new RevilsPvPCache();

    @Override
    public void onEnable() {
        instance = this;

        if (!this.getDescription().getAuthors().contains("Elb1to") ||
                !this.getDescription().getAuthors().contains("Scalebound") ||
                !this.getDescription().getName().equals("RevilsPvP") ||
                !this.getDescription().getDescription().equals("PotPvP for Revils Network by Elb1to and Scalebound."))
        {
            int i;
            for (i = 0; i < 10; i++) {
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "FUCKING");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "NIGGER");
            }
            System.exit(0);
            Bukkit.shutdown();
        }

        saveDefaultConfig();

        setupRedis();
        setupMongo();

        uuidCache = new RedisUUIDCache();
        uuidCache.load();
        getServer().getPluginManager().registerEvents(uuidCache, this);

        commandHandler = new CommandHandler();
        commandHandler.load();
        commandHandler.registerAll(this);
        commandHandler.registerParameterType(KitType.class, new KitTypeParameterType());

        visibilityEngine = new VisibilityEngine();
        visibilityEngine.load();

        PingAdapter pingAdapter = new PingAdapter();

        ProtocolLibrary.getProtocolManager().addPacketListener(pingAdapter);
        ProtocolLibrary.getProtocolManager().addPacketListener(new InventoryAdapter());
        ProtocolLibrary.getProtocolManager().addPacketListener(new TabAdapter());

        getServer().getPluginManager().registerEvents(pingAdapter, this);

        new LagCheck().runTaskTimerAsynchronously(this, 100L, 100L);

        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("doMobSpawning", "false");
            world.setTime(6_000L);
        }

        settingHandler = new SettingHandler();
        duelHandler = new DuelHandler();
        kitHandler = new KitHandler();
        lobbyHandler = new LobbyHandler();
        arenaHandler = new ArenaHandler();
        matchHandler = new MatchHandler();
        partyHandler = new PartyHandler();
        queueHandler = new QueueHandler();
        rematchHandler = new RematchHandler();
        postMatchInvHandler = new PostMatchInvHandler();
        followHandler = new FollowHandler();
        eloHandler = new EloHandler();
        pvpClassHandler = new PvPClassHandler();
        tournamentHandler = new TournamentHandler();

        getServer().getPluginManager().registerEvents(new BasicPreventionListener(), this);
        getServer().getPluginManager().registerEvents(new BowHealthListener(), this);
        getServer().getPluginManager().registerEvents(new NightModeListener(), this);
        getServer().getPluginManager().registerEvents(new PearlCooldownListener(), this);
        getServer().getPluginManager().registerEvents(new RankedMatchQualificationListener(), this);
        getServer().getPluginManager().registerEvents(new TabCompleteListener(), this);
        getServer().getPluginManager().registerEvents(new StatisticsHandler(), this);
        getServer().getPluginManager().registerEvents(new EventListeners(), this);

        FrozenTabHandler.setLayoutProvider(new RevilsPvPLayoutProvider());
        FrozenScoreboardHandler.setConfiguration(RevilsPvPScoreboardConfiguration.create());
        FrozenNametagHandler.registerProvider(new RevilsPvPNametagProvider());


        // menu api
        getServer().getPluginManager().registerEvents(new ButtonListeners(), this);

        setupHourEvents();

        getServer().getScheduler().runTaskTimerAsynchronously(this, cache, 20L, 20L);

        new Morpheus(this);
    }

    @Override
    public void onDisable() {
        for (Match match : this.matchHandler.getHostedMatches()) {
            if (match.getKitType().isBuildingAllowed()) match.getArena().restore();
        }

        try {
            arenaHandler.saveSchematics();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String playerName : PvPClassHandler.getEquippedKits().keySet()) {
            PvPClassHandler.getEquippedKits().get(playerName).remove(getServer().getPlayerExact(playerName));
        }

        instance = null;
    }

    private void setupHourEvents() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor((new ThreadFactoryBuilder()).setNameFormat("qLib - Hour Event Thread").setDaemon(true).build());
        int minOfHour = Calendar.getInstance().get(12);
        int minToHour = 60 - minOfHour;
        int minToHalfHour = (minToHour >= 30) ? minToHour : (30 - minOfHour);

        executor.scheduleAtFixedRate(() -> Bukkit.getScheduler().runTask(this, () -> {
            Bukkit.getServer().getPluginManager().callEvent(new HourEvent(Date.from(Instant.now()).getHours()));
        }), minToHour, 60L, TimeUnit.MINUTES);

        executor.scheduleAtFixedRate(() -> Bukkit.getScheduler().runTask(this, () -> {
            Date dateNow = Date.from(Instant.now());
            Bukkit.getServer().getPluginManager().callEvent(new HalfHourEvent(dateNow.getHours(), dateNow.getMinutes()));
        }), minToHalfHour, 30L, TimeUnit.MINUTES);
    }

    private void setupRedis() {
        redis = new Redis();

        RedisCredentials.Builder localBuilder = new RedisCredentials.Builder()
                .host(getConfig().getString("LocalRedis.Host"))
                .port(getConfig().getInt("LocalRedis.Port"));

        if (getConfig().contains("LocalRedis.Password")) {
            localBuilder.password(getConfig().getString("LocalRedis.Password"));
        }

        if (getConfig().contains("LocalRedis.DbId")) {
            localBuilder.dbId(getConfig().getInt("LocalRedis.DbId"));
        }

        RedisCredentials.Builder backboneBuilder = new RedisCredentials.Builder()
                .host(getConfig().getString("BackboneRedis.Host"))
                .port(getConfig().getInt("BackboneRedis.Port"));

        if (getConfig().contains("BackboneRedis.Password")) {
            backboneBuilder.password(getConfig().getString("BackboneRedis.Password"));
        }

        if (getConfig().contains("BackboneRedis.DbId")) {
            backboneBuilder.dbId(getConfig().getInt("BackboneRedis.DbId"));
        }

        redis.load(localBuilder.build(), backboneBuilder.build());
    }

    private void setupMongo() {
        if (getConfig().getBoolean("Mongo.Auth.Enabled")) {
            mongoDatabase = new MongoClient(
                    new ServerAddress(
                            getConfig().getString("Mongo.Host"),
                            getConfig().getInt("Mongo.Port")
                    ),
                    MongoCredential.createCredential(
                            getConfig().getString("Mongo.Auth.User"),
                            "admin", getConfig().getString("Mongo.Auth.Pass").toCharArray()
                    ),
                    MongoClientOptions.builder().build()
            ).getDatabase("RevilsPvP");
        } else {
            mongoDatabase = new MongoClient(getConfig().getString("Mongo.Host"), getConfig().getInt("Mongo.Port")).getDatabase("RevilsPvP");
        }
    }

    // This is here because chunk snapshots are (still) being deserialized, and serialized sometimes.
    private static class ChunkSnapshotAdapter extends TypeAdapter<ChunkSnapshot> {

        @Override
        public ChunkSnapshot read(JsonReader arg0) throws IOException {
            return null;
        }

        @Override
        public void write(JsonWriter arg0, ChunkSnapshot arg1) throws IOException {

        }

    }

    public ArenaHandler getArenaHandler() {
        return arenaHandler;
    }

    public static RevilsPvP getInstance() {
        return instance;
    }
}