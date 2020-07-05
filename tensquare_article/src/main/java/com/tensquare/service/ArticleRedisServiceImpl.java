package com.tensquare.service;

import com.tensquare.model.Article;
import com.tensquare.util.CustomException;
import com.tensquare.repository.ArticleRedisRepository;
import com.tensquare.util.FastjsonUtil;
import com.tensquare.util.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleRedisServiceImpl implements ArticleRedisService {
    Logger logger = LoggerFactory.getLogger(ArticleRedisServiceImpl.class);
    /**
     * 文章点赞总数key
     */
    @Value("${total.like.count.key}")
    private String TOTAL_LIKE_COUNT_KEY;
    /**
     * 用户点赞的文章key
     */
    @Value("${user.like.article.key}")
    private String USER_LIKE_ARTICLE_KEY;
    /**
     * 文章被点赞的key
     */
    @Value("${article.liked.user.key}")
    private String ARTICLE_LIKED_USER_KEY;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ArticleRedisRepository articleRedisRepository;

    /*
    指定序列化方式
     */
    @PostConstruct
    public void init() {
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
    }

    /**
     * 用户点赞某篇文章
     *
     * @param articleId
     * @param likedUserId
     * @param likePoseId
     */
    @Override
    public void like(String articleId, String likedUserId, String likePoseId) {
        //入参验证
        validateParam(articleId, likedUserId, likePoseId);
        logger.info("点赞信息存入redis开始:", articleId, likedUserId, likePoseId);
        //只有未点赞的用户可以点赞
        likeArticleLogicValidate(articleId, likedUserId, likePoseId);
        //被点赞用户总点赞量+1, 当redis中没有该key时,第一次点赞会创建该key
        redisTemplate.opsForHash().increment(TOTAL_LIKE_COUNT_KEY, likedUserId, 1);
        //当前用户喜欢的文章+1
        String userLikeResult = (String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, likePoseId);
        Set<String> articleSet = userLikeResult == null ? new HashSet<>() : FastjsonUtil.deserializeToSet(userLikeResult, String.class);
        articleSet.add(articleId);
        redisTemplate.opsForHash().put(USER_LIKE_ARTICLE_KEY, likePoseId, FastjsonUtil.serialize(articleSet));
        //文章点赞数+1
        String articleLikedResult = (String) redisTemplate.opsForHash().get(ARTICLE_LIKED_USER_KEY, articleId);
        Set<String> likePoseIdSet=articleLikedResult==null?new HashSet<>():FastjsonUtil.deserializeToSet(articleLikedResult,String.class);
        likePoseIdSet.add(likePoseId);
        redisTemplate.opsForHash().put(ARTICLE_LIKED_USER_KEY,articleId,FastjsonUtil.serialize(likePoseIdSet));
        logger.info("点赞成功",articleId,likedUserId,likePoseId);
    }


    /**
     * 取消点赞
     * @param articleId
     * @param likedUserId
     * @param likePoseId
     */
    @Override
    public void notLike(String articleId, String likedUserId, String likePoseId) {
        //入参验证
        validateParam(articleId,likedUserId,likePoseId);
        logger.info("取消点赞开始",articleId,likedUserId,likePoseId);
        //只有点过赞的用户才能取消点赞
        unlikeArticleLogicValidate(articleId,likedUserId,likePoseId);
        //用户总点赞数-1, 先取出总的点赞数, 然后减一再放进去
        long count = Long.parseLong((String) redisTemplate.opsForHash().get(TOTAL_LIKE_COUNT_KEY, likedUserId));
        redisTemplate.opsForHash().put(TOTAL_LIKE_COUNT_KEY,likedUserId,String.valueOf(--count));
        //文章点赞数-1
        String articleLikedResult = (String) redisTemplate.opsForHash().get(ARTICLE_LIKED_USER_KEY, articleId);
        Set<String> likePoseIdSet = FastjsonUtil.deserializeToSet(articleLikedResult, String.class);
        likePoseIdSet.remove(likePoseId);
        redisTemplate.opsForHash().put(ARTICLE_LIKED_USER_KEY,articleId,FastjsonUtil.serialize(likePoseIdSet));
        //用户点赞数-1
        String userLikeResult = (String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, likePoseId);
        Set<String> articleSet = FastjsonUtil.deserializeToSet(userLikeResult, String.class);
        articleSet.remove(articleId);
        redisTemplate.opsForHash().put(USER_LIKE_ARTICLE_KEY,likePoseId,FastjsonUtil.serialize(articleSet));
        logger.info("取消点赞完成",articleId,likedUserId,likePoseId);
    }

    /**
     * 获取用户点赞的文章
     * @param likedPoseId
     * @return
     */
    @Override
    public List<String> userLike(String likedPoseId) {
        validateParam(likedPoseId);
        String userLikeArticleSet = (String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, likedPoseId);
        Set<String> articles = FastjsonUtil.deserializeToSet(userLikeArticleSet, String.class);
        return articles.stream().collect(Collectors.toList());
    }

    /**
     * 获取用户总的文章点赞数
     * @param likedPoseId
     * @return
     */
    @Override
    public String userTotal(String likedPoseId) {
        validateParam(likedPoseId);
        return String.valueOf(redisTemplate.opsForHash().get(TOTAL_LIKE_COUNT_KEY, likedPoseId));
    }

    /**
     * 获取某篇文章总的点赞数
     * @param articleId
     * @return
     */
    @Override
    public String articleTotal(String articleId) {
        validateParam(articleId);
        String articleLiked = (String) redisTemplate.opsForHash().get(ARTICLE_LIKED_USER_KEY, articleId);
        Set<String> userSet = FastjsonUtil.deserializeToSet(articleLiked, String.class);
        if (userSet == null){
            return "0";
        }
        return String.valueOf(userSet.size());
    }



    /**
     * 取消点赞逻辑校验
     * @param articleId
     * @param likedUserId
     * @param likePoseId
     */
        private void unlikeArticleLogicValidate(String articleId, String likedUserId, String likePoseId) {
        //获取文章点赞用户
        Set<String> likePoseIdSet = FastjsonUtil.deserializeToSet((String) redisTemplate.opsForHash().get(ARTICLE_LIKED_USER_KEY, articleId), String.class);
        //获取用户点赞文章
        Set<String> articleIdSet = FastjsonUtil.deserializeToSet((String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, likePoseId), String.class);
        if (likePoseIdSet.contains(likePoseId)||articleIdSet.contains(articleId)){
            return;
        }else {
            logger.error("该用户没有点赞该文章,因此无法取消点赞");
            throw new CustomException(StatusCode.ERROR,"无法取消点赞");
        }
    }

    /**
     * 点赞文章逻辑校验
     * @param articleId
     * @param likedUserId
     * @param likePoseId
     */
    private void likeArticleLogicValidate(String articleId, String likedUserId, String likePoseId) {
        //获取用户点赞文章
        Set<String> articleIdSet = FastjsonUtil.deserializeToSet((String) redisTemplate.opsForHash().get(USER_LIKE_ARTICLE_KEY, likePoseId), String.class);
        //获取文章被点赞用户
        Set<String> likePostIdSet = FastjsonUtil.deserializeToSet((String) redisTemplate.opsForHash().get(ARTICLE_LIKED_USER_KEY, articleId), String.class);
        if (articleIdSet == null){
            return;
        }
        if (likePostIdSet == null){
            return;
        }else {
            if(articleIdSet.contains(likePostIdSet) || likePostIdSet.contains(articleIdSet)){
                logger.error("该文章已被该用户点赞,重复点赞");
                throw new CustomException(StatusCode.ERROR,"重复点赞");
            }
        }
    }

    /**
     * 入参验证
     * @param params
     */
    private void validateParam(String... params) {
        for (String param:params) {
            if (param == null){
                logger.error("入参存在空值");
                throw new CustomException(StatusCode.ERROR,"入参存在空值");
            }
        }
    }


    /**
     * 测试点击量
     * @param addr
     * @return
     */
    @Override
    public int getHits(String addr) {
        SetOperations opsForSet = redisTemplate.opsForSet();
        opsForSet.add("123","");
        Long size = opsForSet.size("123");
        System.out.println("第一次设置key后的个数:::"+size);
        opsForSet.add("123",addr);
        opsForSet.add("123",addr);
        opsForSet.add("123","789");
        Long size1 = opsForSet.size("123");
        System.out.println("添加完数据后的key中的个数::::"+size1);
        return 0;
    }
}
