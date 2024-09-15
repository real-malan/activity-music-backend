package pl.activitymusic.backend.system.spring.jpa

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.springframework.beans.factory.annotation.Value
import pl.activitymusic.backend.system.crypto.CryptoOperations

@Converter
class StringCryptoConverter(
    @Value("\${jasypt.jpaPassword}") private val password: String
) : AttributeConverter<String, String> {

    private val cryptoOperations: CryptoOperations = CryptoOperations(password)

    override fun convertToDatabaseColumn(attribute: String?): String? {
        return attribute?.let { cryptoOperations.encrypt(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): String? {
        return dbData?.let { cryptoOperations.decrypt(it) }
    }
}
