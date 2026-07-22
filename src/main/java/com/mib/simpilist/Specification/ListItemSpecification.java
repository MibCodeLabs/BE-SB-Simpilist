package com.mib.simpilist.Specification;

import com.mib.simpilist.exception.ClientException;
import com.mib.simpilist.model.Group;
import com.mib.simpilist.model.ListItem;
import com.mib.simpilist.model.User;
import com.mib.simpilist.utililty.Security.UserContext;
import com.mib.simpilist.utililty.Utilities;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class ListItemSpecification {
    public static Specification<ListItem> searchQuery(String searchQuery) {
        return (root, query, criteriaBuilder) -> {
            if(Utilities.isNullOrEmpty(searchQuery)){
                return criteriaBuilder.conjunction();
            }

            String likePattern = "%" + searchQuery.trim().toLowerCase() + "%";

            Predicate titlePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    likePattern
            );

            Predicate descriptionPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    likePattern
            );

            return criteriaBuilder.or(titlePredicate, descriptionPredicate);
        };
    }

    public static Specification<ListItem> belongsToCurrentUser() {
        return (root,query,builder)->{
            Join<ListItem, Group> groupJoin=root.join("group", JoinType.INNER);
            Join<Group, User> userJoin = groupJoin.join("user", JoinType.INNER);
            return builder.equal(userJoin.get("id"),
                    UserContext.getCurrentUser().id());
        };
    }

    public static Specification<ListItem> groupIdEquals(Long groupId) {
        return (root,query,builder)->{
            if(Utilities.isNull(groupId)){
                throw new ClientException("Invalid Group");
            }
            Join<ListItem, Group> groupJoin=root.join("group", JoinType.INNER);
            return builder.equal(groupJoin.get("id"),groupId);
        };
    }
}
