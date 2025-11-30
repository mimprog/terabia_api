package com.terabia.terabia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.terabia.terabia.models.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    /**
     * Méthode Magique JPA (Derived Query)
     * Spring traduit ça en : SELECT * FROM conversation c JOIN participants p ON c.id = p.conv_id WHERE p.user_id = ?
     *
     * @param idUser L'ID de l'utilisateur dont on veut les conversations
     * @return La liste des conversations
     */
    List<Conversation> findByParticipantsId(Integer idUser);

    /**
     * Requete Personnalisée (JPQL)
     * l'utilisateur 1 ET l'utilisateur 2 dans sa liste de participants.
     *
     * @param userId1 ID de l'expéditeur
     * @param userId2 ID du destinataire
     * @return Un Optional (peut être vide si aucune conversation n'existe)
     */
    /*@Query("SELECT c FROM Conversation c " +
            "JOIN c.participants p1 " +
            "JOIN c.participants p2 " +
            "WHERE p1.idUser = :userId1 AND p2.idUser = :userId2")
    Optional<Conversation> findConversationEntreDeuxUsers(@Param("userId1") Integer userId1,
                                                          @Param("userId2") Integer userId2);
*/
    @Query("""
       SELECT c FROM Conversation c
       JOIN c.participants u
       WHERE u.id IN (:userId1, :userId2)
       GROUP BY c
       HAVING COUNT(c) = 2
       """)
    Optional<Conversation> findConversationEntreDeuxUsers(
            @Param("userId1") Integer userId1,
            @Param("userId2") Integer userId2
    );

}
