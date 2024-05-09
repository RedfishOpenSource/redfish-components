package com.redfish.components.demo.components;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class UserQueryResolver implements GraphQLQueryResolver {

    /**
     * 根据查询用户
     */
    public UserVO getUser(String userId) {
        UserVO user = new UserVO(userId,userId+"123",20);
        return user;
    }

}
