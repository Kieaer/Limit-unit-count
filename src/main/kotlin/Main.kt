import arc.Core
import arc.Events
import arc.util.CommandHandler
import arc.util.Log
import mindustry.game.EventType
import mindustry.gen.Groups
import mindustry.mod.Plugin

class Main : Plugin() {
    private var limit = 3000

    override fun init() {
        if (Core.settings.has("unit-limit")){
            limit = Core.settings.getInt("unit-limit")
        }

        Events.on(EventType.UnitCreateEvent::class.java) {
            if (Groups.unit.size() > limit) it.unit.kill()
        }

        Events.on(EventType.UnitSpawnEvent::class.java) {
            if (Groups.unit.size() > limit) it.unit.kill()
        }
    }

    override fun registerServerCommands(handler: CommandHandler) {
        handler.register("limit", "<count>", "Set unit limit per world.") {
            if (it[0].toIntOrNull() != null){
                limit = it[0].toInt()
                Core.settings.put("unit-limit", it[0].toInt())
            }
        }
    }
}