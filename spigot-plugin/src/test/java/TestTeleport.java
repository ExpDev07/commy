import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Project created by ExpDev
 */
public class TestTeleport {

    public static void main(String[] args) {
        Player player = Bukkit.getPlayer("ExpDev");
        Location loc = player.getLocation().clone();

        // Here we're keeping the same yaw and pitch
        player.teleport(new Location(loc.getWorld(), 1d, 1d, 1d, loc.getYaw(), loc.getPitch()));
    }

}
