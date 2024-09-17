package pl.activitymusic.backend.business.account.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import pl.activitymusic.backend.system.spring.jpa.StringCryptoConverter
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(uniqueConstraints = [
    UniqueConstraint(columnNames = ["external_id", "source"])
])
class Service(

    @Column(nullable = false, updatable = false)
    val externalId: String,

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    val source: Source,

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter::class)
    val accessToken: String,

    @Column(nullable = false)
    val accessTokenExpirationDate: Instant,

    @Column(nullable = false)
    @Convert(converter = StringCryptoConverter::class)
    val refreshToken: String
) {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    lateinit var account: Account

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @LastModifiedDate
    val lastModifiedDate: Instant? = null

    @CreatedDate
    val createdDate: Instant? = null
}
