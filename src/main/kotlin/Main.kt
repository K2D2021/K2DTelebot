import dev.inmo.tgbotapi.bot.ktor.telegramBot
import dev.inmo.tgbotapi.extensions.api.bot.getMe
import dev.inmo.tgbotapi.extensions.api.bot.setMyCommands
import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.expectations.waitText
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import dev.inmo.tgbotapi.requests.send.SendTextMessage
import dev.inmo.tgbotapi.types.BotCommand
import dev.inmo.tgbotapi.types.MenuButton
import dev.inmo.tgbotapi.types.buttons.ReplyKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.SimpleKeyboardButton
import dev.inmo.tgbotapi.types.commands.BotCommandScope
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*

suspend fun main() {
    val bot = telegramBot(TokenDoNotShare.getToken())
    val sdf = SimpleDateFormat("dd MMMM yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    bot.buildBehaviourWithLongPolling {
        println(getMe())
        val nameReplyMarkup = ReplyKeyboardMarkup(
            matrix {
                row {
                    +SimpleKeyboardButton("nope")
                    +SimpleKeyboardButton("hope")
                }
            }
        )

//        val menu = MenuButton
        onCommand("start") {
            reply(it, "Hi22 :)")
            val name = waitText(
                SendTextMessage(
                    it.chat.id,
                    "Send me your name or choose \"nope\"",
                    replyMarkup = nameReplyMarkup
                )
            ).first().text.takeIf { it != "nope" }
        }

        onCommand("what is time") {
            reply(it, currentDate)
        }

        setMyCommands(
            BotCommand("time", "show current time"),
            scope = BotCommandScope.AllGroupChats
        )
    }.join()
}