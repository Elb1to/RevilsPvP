package eu.revils.revilspvp.kt.uuid

import eu.revils.revilspvp.RevilsPvP
import java.util.*

class RedisUUIDCache : UUIDCache() {

    override fun fetchCache(): Map<UUID, String> {
        return RevilsPvP.getInstance().redis
            .runRedisCommand { redis -> redis.hgetAll(KEY) }
            .mapKeys { entry -> UUID.fromString(entry.key) }
    }

    override fun updateCache(uuid: UUID, name: String) {
        RevilsPvP.getInstance().redis.runRedisCommand { redis -> redis.hset(KEY, uuid.toString(), name) }
    }

    companion object {
        private const val KEY = "Cubed:UUIDCache"
    }

}