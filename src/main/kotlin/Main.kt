import dev.inmo.tgbotapi.bot.ktor.telegramBot
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.api.bot.setMyCommands
import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import dev.inmo.tgbotapi.types.BotCommand
import dev.inmo.tgbotapi.types.commands.BotCommandScope
import java.text.SimpleDateFormat
import java.util.*

suspend fun main() {
    val bot = telegramBot(TokenDoNotShare.getToken())
    val sdf = SimpleDateFormat("dd MMMM yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    bot.buildBehaviourWithLongPolling {
        println(getMe())

        onCommand("start") {
            reply(it, "Hi :)")
        }

        onCommand("time") {
            reply(it, currentDate)
        }

        setMyCommands(
            BotCommand("time", "show current time"),
            scope = BotCommandScope.AllGroupChats
        )
    }.join()
}