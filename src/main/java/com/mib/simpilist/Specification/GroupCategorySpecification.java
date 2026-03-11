package com.mib.simpilist.Specification;

import com.mib.simpilist.model.GroupCategory;
import com.mib.simpilist.model.ListGroup;
import com.mib.simpilist.model.User;
import com.mib.simpilist.utililty.Security.UserContext;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class GroupCategorySpecification {
    public static Specification<GroupCategory> belongsToCurrentUser() {
        return (root,query,builder)->{
            Join<ListGroup, User> userJoin = root.join("user", JoinType.INNER);
            return builder.equal(userJoin.get("id"),
                    UserContext.getCurrentUser().id());
        };
    }
}
