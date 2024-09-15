package pl.activitymusic.backend.business.account.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import pl.activitymusic.backend.system.spring.jpa.StringCryptoConverter
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Account(

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val source: Source,

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter::class)
    val accessToken: String,

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter::class)
    val refreshToken: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @LastModifiedDate
    var lastModifiedDate: Instant? = null,

    @CreatedDate
    var createdDate: Instant? = null
)
