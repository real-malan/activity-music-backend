package pl.activitymusic.backend.system.crypto

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.salt.RandomSaltGenerator
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CryptoOperations(
    @Value("\${jasypt.password}") private val password: String
) {

    private val encryptor: StringEncryptor = StandardPBEStringEncryptor()
        .also {
            it.setPassword(password)
            it.setSaltGenerator(RandomSaltGenerator())
        }

    fun encrypt(value: String): String {
        return encryptor.encrypt(value)
    }

    fun decrypt(value: String): String {
        return encryptor.decrypt(value)
    }
}
