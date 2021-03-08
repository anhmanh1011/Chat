import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ChatHistoryDB {

    Session session = HibernateUtils.getSessionFactory().openSession();

    public void save(ChatHistoryEntity chatHistoryEntity) {
        session.beginTransaction();
        session.save(chatHistoryEntity);
        session.getTransaction().commit();
    }

    public List<ChatHistoryEntity> findAllByFromUserAndToUser(String fromUser, String toUser) {

//        String sql = "select * from chathistory where (FROM_USER = :FROM_USER and TO_USER = :TO_USER ) or (TO_USER = :FROM_USER and FROM_USER = :TO_USER ) order by CREATION_TIME";
//        Query query = session.createNativeQuery(sql);
        Query query = session.createQuery("FROM ChatHistoryEntity C WHERE (C.fromUser = :FROM_USER and C.toUser = :TO_USER) or (C.toUser = :FROM_USER and C.fromUser = :TO_USER  ) order by date(C.createTime) ");

        query.setParameter("FROM_USER", fromUser);
        query.setParameter("TO_USER", toUser);
        return query.list();

    }

}
