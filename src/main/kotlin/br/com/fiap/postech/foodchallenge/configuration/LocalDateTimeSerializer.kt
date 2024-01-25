import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>() {

    @Throws(IOException::class)
    override fun serialize(
        localDateTime: LocalDateTime?,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        if (localDateTime != null) {
            val formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            jsonGenerator.writeString(formattedDateTime)
        }
    }
}
