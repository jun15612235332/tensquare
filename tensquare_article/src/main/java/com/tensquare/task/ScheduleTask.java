package com.tensquare.task;

import com.tensquare.model.UserLikeArticle;
import com.tensquare.service.ArticleService;
import com.tensquare.service.UserLikeArticleService;
import com.tensquare.util.FastjsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

/**
 * 定时落库任务
 */
@Service
public class ScheduleTask {
    private Logger logger= LoggerFactory.getLogger(ScheduleTask.class);

    private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    private ArticleService articleService;
    @Autowired
    private UserLikeArticleService userLikeArticleService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 */1 * * * ? ")
    public void redisDataToMySQL(){
        logger.info("开始执行redis数据持久化到MySQL任务", LocalDateTime.now().format(dateTimeFormatter));
        //1.更新文章总的点赞数
        Map<String,String> articleMap = redisTemplate.opsForHash().entries(ARTICLE_LIKED_USER_KEY);
        //遍历map
        for (Map.Entry<String, String> entry:articleMap.entrySet()){
            String articleId = entry.getKey();
            Set<String> userIdSet = FastjsonUtil.deserializeToSet(entry.getValue(), String.class);
            //1.同步某篇文章总的点赞数到MySQL
            synchronizeTotalLikeCount(articleId,userIdSet);
            //2.同步用户喜欢的文章
            synchronizeUserLikeArticle(articleId,userIdSet);
        }
        logger.info("结束执行Redis数据持久化到MySQL任务", LocalDateTime.now().format(dateTimeFormatter));
    }

    /**
     * 同步用户喜欢的文章
     * @param articleId
     * @param userIdSet
     */
    private void synchronizeUserLikeArticle(String articleId, Set<String> userIdSet) {
        if (userIdSet.size()>0){
            for (String userId:userIdSet) {
                UserLikeArticle userLikeArticle = new UserLikeArticle();
                userLikeArticle.setUserId(userId);
                userLikeArticle.setArticleId(articleId);
                if (userLikeArticleService.findByUserIdArticleId(userId,articleId)==null){
                    userLikeArticleService.save(userLikeArticle);
                }
            }
        }else {
            userLikeArticleService.deleteByArticleId(articleId);
        }
    }

    /**
     * 同步某篇文章总的点赞数到MySQL
     * @param articleId
     * @param userIdSet
     */
    private void synchronizeTotalLikeCount(String articleId, Set<String> userIdSet) {
        articleService.updateThumbupById(articleId,userIdSet.size());
    }
}
