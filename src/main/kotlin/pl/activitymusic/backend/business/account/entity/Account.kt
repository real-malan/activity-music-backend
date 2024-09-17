package pl.activitymusic.backend.business.account.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @LastModifiedDate
    val lastModifiedDate: Instant? = null

    @CreatedDate
    val createdDate: Instant? = null

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    private val _services: List<Service> = mutableListOf()

    val services get() = _services.toList()
}
