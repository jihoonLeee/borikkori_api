package borikkori.community.api.adapter.out.persistence.post.mapper;

import borikkori.community.api.adapter.in.web.post.response.PostResponse;
import borikkori.community.api.adapter.out.persistence.file.entity.FileEntity;
import borikkori.community.api.adapter.out.persistence.mbti.entity.MbtiEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.CommentEntity;
import borikkori.community.api.adapter.out.persistence.post.entity.PostEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserEntity;
import borikkori.community.api.adapter.out.persistence.user.entity.UserRoleEntity;
import borikkori.community.api.common.enums.MbtiType;
import borikkori.community.api.common.enums.PostStatus;
import borikkori.community.api.common.enums.Role;
import borikkori.community.api.domain.post.entity.Post;
import borikkori.community.api.domain.user.entity.User;
import borikkori.community.api.domain.user.vo.UserId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T17:16:03+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toDomain(PostEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        User user = null;
        String title = null;
        String contents = null;
        int visitCount = 0;
        int likeCount = 0;
        PostStatus postStatus = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = entity.getId();
        user = userEntityToUser( entity.getUser() );
        title = entity.getTitle();
        contents = entity.getContents();
        visitCount = entity.getVisitCount();
        likeCount = entity.getLikeCount();
        postStatus = entity.getPostStatus();
        regDate = entity.getRegDate();
        updDate = entity.getUpdDate();

        Post post = new Post( id, user, title, contents, visitCount, likeCount, postStatus, regDate, updDate );

        return post;
    }

    @Override
    public PostEntity toEntity(Post domain) {
        if ( domain == null ) {
            return null;
        }

        Long id = null;
        UserEntity user = null;
        String title = null;
        String contents = null;
        int visitCount = 0;
        int likeCount = 0;
        PostStatus postStatus = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = domain.getId();
        user = userToUserEntity( domain.getUser() );
        title = domain.getTitle();
        contents = domain.getContents();
        visitCount = domain.getVisitCount();
        likeCount = domain.getLikeCount();
        postStatus = domain.getPostStatus();
        regDate = domain.getRegDate();
        updDate = domain.getUpdDate();

        List<FileEntity> fileEntities = null;
        List<CommentEntity> commentEntities = null;

        PostEntity postEntity = new PostEntity( id, user, title, contents, visitCount, likeCount, fileEntities, commentEntities, postStatus, regDate, updDate );

        return postEntity;
    }

    @Override
    public PostResponse toResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        Long postId = null;
        String nickName = null;
        int visitCnt = 0;
        int likeCnt = 0;
        String title = null;
        String contents = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        postId = post.getId();
        nickName = postUserName( post );
        visitCnt = post.getVisitCount();
        likeCnt = post.getLikeCount();
        title = post.getTitle();
        contents = post.getContents();
        regDate = post.getRegDate();
        updDate = post.getUpdDate();

        boolean isTemp = false;

        PostResponse postResponse = new PostResponse( postId, nickName, title, contents, visitCnt, likeCnt, isTemp, regDate, updDate );

        return postResponse;
    }

    @Override
    public List<PostResponse> toResponseList(List<Post> posts) {
        if ( posts == null ) {
            return null;
        }

        List<PostResponse> list = new ArrayList<PostResponse>( posts.size() );
        for ( Post post : posts ) {
            list.add( toResponse( post ) );
        }

        return list;
    }

    protected User userEntityToUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserId id = null;
        String name = null;
        String email = null;
        String password = null;
        boolean active = false;
        boolean emailVerified = false;
        boolean accountLocked = false;
        int failedLoginAttempts = 0;
        LocalDateTime lastLoginDate = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = map( userEntity.getId() );
        name = userEntity.getName();
        email = userEntity.getEmail();
        password = userEntity.getPassword();
        active = userEntity.isActive();
        emailVerified = userEntity.isEmailVerified();
        accountLocked = userEntity.isAccountLocked();
        failedLoginAttempts = userEntity.getFailedLoginAttempts();
        lastLoginDate = userEntity.getLastLoginDate();
        regDate = userEntity.getRegDate();
        updDate = userEntity.getUpdDate();

        List<Role> roles = null;
        MbtiType mbtiResult = null;

        User user = new User( id, name, email, password, active, emailVerified, accountLocked, failedLoginAttempts, lastLoginDate, regDate, updDate, roles, mbtiResult );

        return user;
    }

    protected UserEntity userToUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String email = null;
        String password = null;
        boolean active = false;
        boolean emailVerified = false;
        boolean accountLocked = false;
        int failedLoginAttempts = 0;
        LocalDateTime lastLoginDate = null;
        LocalDateTime regDate = null;
        LocalDateTime updDate = null;

        id = map( user.getId() );
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        active = user.isActive();
        emailVerified = user.isEmailVerified();
        accountLocked = user.isAccountLocked();
        failedLoginAttempts = user.getFailedLoginAttempts();
        lastLoginDate = user.getLastLoginDate();
        regDate = user.getRegDate();
        updDate = user.getUpdDate();

        MbtiEntity mbtiEntity = null;
        List<UserRoleEntity> userRoles = null;

        UserEntity userEntity = new UserEntity( id, name, email, password, active, emailVerified, accountLocked, failedLoginAttempts, lastLoginDate, regDate, updDate, mbtiEntity, userRoles );

        return userEntity;
    }

    private String postUserName(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
